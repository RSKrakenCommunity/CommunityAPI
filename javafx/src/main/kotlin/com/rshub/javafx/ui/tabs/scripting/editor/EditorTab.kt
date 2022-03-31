package com.rshub.javafx.ui.tabs.scripting.editor

import com.rshub.javafx.ui.tabs.scripting.editor.util.Comments
import com.rshub.javafx.ui.tabs.scripting.editor.util.FileEndings
import com.rshub.javafx.ui.tabs.scripting.editor.util.ProjectChooser
import com.rshub.javafx.ui.tabs.scripting.editor.util.registerShortKeys
import com.rshub.javafx.ui.tabs.scripting.editor.util.replaceLast
import com.rshub.javafx.ui.tabs.scripting.editor.highlightings.StyleSheets
import com.rshub.javafx.ui.tabs.scripting.editor.highlightings.syntax
import com.rshub.scripting.DeveloperScript
import javafx.geometry.Pos
import javafx.scene.control.Tab
import javafx.scene.control.Tooltip
import javafx.scene.layout.StackPane
import kraken.plugin.api.Debug
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import org.reactfx.EventStreams
import tornadofx.*
import java.nio.file.Files
import java.nio.file.Path
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

/**
 * @author Artur Bosch
 */
class EditorTab(
	val name: String = "New Tab..",
	val content: String = "",
	var path: Path? = null,
	val editable: Boolean = true,
	val newBlankScript: Boolean = false
) : Tab(name) {

	private var codeArea: CodeArea = CodeArea()

	init {
		val thisPath = path
		if(newBlankScript) {
			initBlankCodeArea()
		} else if (thisPath != null && content.isEmpty()) {
			loadCodeArea(thisPath)
			determineTabName(thisPath)
		} else if (thisPath != null) {
			initCodeArea(content)
			determineTabName(thisPath)
		} else {
			initCodeArea(content)
		}
	}

	private fun initBlankCodeArea() {
		codeArea.apply {
			paragraphGraphicFactory = LineNumberFactory.get(this)
			enableHighlightingNewScript()
			registerShortKeys(this@EditorTab)
			isEditable = editable
			replaceText("")
			moveTo(0)
			undoManager.forgetHistory()
			undoManager.mark()
		}
		this.setContent(anchorpane {
			hbox {
				anchorpaneConstraints {
					topAnchor = 0.0
					leftAnchor = 0.0
					rightAnchor = 0.0
				}
				minHeight = 40.0
				alignment = Pos.CENTER_LEFT
				button("Execute") {
					disableWhen(codeArea.textProperty().isBlank())
					setOnAction {
						val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<DeveloperScript>()
						val result = BasicJvmScriptingHost().eval(codeArea.text.toScriptSource(), compilationConfiguration, null)
						for (report in result.reports) {
							Debug.log(report.message)
							val exception = report.exception
							if (exception != null) {
								Debug.log(exception.message)
							}
						}
					}
				}
			}
			add(StackPane(VirtualizedScrollPane(codeArea)).apply {
				anchorpaneConstraints {
					topAnchor = 40.0
					bottomAnchor = 0.0
					leftAnchor = 0.0
					rightAnchor = 0.0
				}
			})
		})
	}

	private fun initCodeArea(content: String) {
		codeArea.apply {
			paragraphGraphicFactory = LineNumberFactory.get(this)
			enableHighlighting(path)
			registerShortKeys(this@EditorTab)
			isEditable = editable
			replaceText(content)
			moveTo(0)
			undoManager.forgetHistory()
			undoManager.mark()
		}
		this.setContent(vbox {
			hbox {
				alignment = Pos.CENTER_LEFT
				button("Execute") {
					disableWhen(codeArea.textProperty().isBlank())
					setOnAction {
						val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<DeveloperScript>()
						BasicJvmScriptingHost().eval(text.toScriptSource(), compilationConfiguration, null)
					}
				}
			}
			add(StackPane(VirtualizedScrollPane(codeArea)))
		})
		requestFocus()
	}

	private fun CodeArea.enableHighlightingNewScript() {
		StyleSheets.getKotlin().run {
			stylesheets.add(this)
			richChanges()
			richChanges()
				.filter { ch -> ch.inserted != ch.removed }
				.subscribe { setStyleSpans(0, syntax(text, null, true)) }
		}
	}

	private fun CodeArea.enableHighlighting(path: Path?) {
		path?.run {
			// if style for path is found, add syntax
			StyleSheets.get(path)?.run {
				stylesheets.add(this)
				richChanges()
						.filter { ch -> ch.inserted != ch.removed }
						.subscribe { setStyleSpans(0, syntax(text, path)) }
			}
		}
	}

	private fun loadCodeArea(path: Path) {
		task {
			Files.readAllLines(path).joinToString("\n")
		} success {
			initCodeArea(it)
		} fail {
			println("Could not load content of $path")
		}
	}

	private fun determineTabName(path: Path) {
		val savedTitle = path.fileName.toString()
		val unsavedTitle = "*$savedTitle"
		text = if (codeArea.undoManager.isAtMarkedPosition) savedTitle else unsavedTitle
		tooltip = Tooltip(path.toString())
		EventStreams.valuesOf(codeArea.undoManager.atMarkedPositionProperty())
				.map { saved -> if (saved) savedTitle else unsavedTitle }
				.subscribe { title -> text = title }
	}

	fun requestFocus() {
		codeArea.requestFocus()
	}

	fun save(savePath: Path? = path) {
		if (savePath != null) {
			if (codeArea.isUndoAvailable) {
				path = savePath
				writeToFile()
				codeArea.undoManager.mark()
				determineTabName(savePath)
			}
		} else {
			saveAs()
		}
	}

	fun saveAs() {
		ProjectChooser.chooseFile().ifPresent {
			val oldPath = path
			save(it)
			checkStyleAfterFileChange(it, oldPath)
		}
	}

	fun uncomment() {
		val (left, right) = Comments.of(path)
		val index = codeArea.currentParagraph
		val column = codeArea.caretColumn
		val paragraph = codeArea.getParagraph(index)
		var text = paragraph.text
		var jumpToNextLine = true

		if (text.trim().startsWith(left)) {
			text = text.replaceFirst(left, "")
			if (right.isNotEmpty()) {
				text = text.replaceLast(right, "")
			}
			if (text.isBlank()) jumpToNextLine = false
		} else {
			if (text.isBlank()) jumpToNextLine = false
			text = "$left$text$right"
		}

		codeArea.replaceText(index, 0, index, paragraph.length(), text)
		if (!codeArea.onLastLine(index)) {
			if (jumpToNextLine) {
				val lengthOfNextParagraph = codeArea.getParagraph(index + 1).text.length
				val newColumn = if (column > lengthOfNextParagraph) lengthOfNextParagraph else column
				codeArea.moveTo(index + 1, newColumn)
			} else {
				if (text.isNotBlank()) {
					codeArea.moveTo(index, left.length)
				}
			}
		}
	}

	private fun checkStyleAfterFileChange(it: Path, oldPath: Path?) {
		when {
			oldPath == null -> enableStyleAfterSave(it)
			FileEndings.isSame(it, oldPath).not() -> (tabPane as EditorPane).reloadTabIfFileEndingsChanges(it)
			else -> (tabPane as EditorPane).closeTabsWithSamePathAsThis(this, it)
		}
	}

	private fun enableStyleAfterSave(it: Path?) {
		codeArea.enableHighlighting(it)
		val position = codeArea.caretPosition
		codeArea.appendText(" ")
		codeArea.deletePreviousChar()
		codeArea.moveTo(position)
	}

	private fun writeToFile() {
		task {
			Files.write(path, codeArea.text.toByteArray())
		} success {
			println("Saved path: $path")
		} fail {
			println("Save failed for path: $path")
		}
	}

}
