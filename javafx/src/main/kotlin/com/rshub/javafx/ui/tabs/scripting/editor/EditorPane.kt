package com.rshub.javafx.ui.tabs.scripting.editor

import com.rshub.javafx.ui.tabs.scripting.editor.util.FileEndings
import com.rshub.javafx.ui.tabs.scripting.editor.util.HELP_TEXT
import com.rshub.javafx.ui.tabs.scripting.editor.util.notNull
import com.rshub.javafx.ui.tabs.scripting.editor.util.onlyIfNull
import com.rshub.javafx.ui.tabs.scripting.editor.util.registerShortKeys
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import tornadofx.fail
import tornadofx.success
import tornadofx.task
import java.nio.file.Path

/**
 * @author Artur Bosch
 */
class EditorPane : TabPane() {

	private val name = "kdit"
	val titleProperty = SimpleStringProperty(name)

	private var title: String
		get() = titleProperty.get() ?: name
		set(value) = titleProperty.set(value)

	init {
		registerShortKeys()
	}

	fun switchFocus() {
		selectionModel.selectedItem?.run {
			if (isFocused) {
				focus(this)
			} else {
				requestFocus()
			}
		}
	}

	fun newTab(path: Path) {
		if (FileEndings.isSupported(path)) {
			val maybeTab = openTabs().find { path == it.path }
			if (maybeTab == null) {
				createNewTabInBackground {
					EditorTab(path = path, newBlankScript = true)
				}
			} else {
				focus(maybeTab)
			}
		}
	}

	internal fun switchTabLeft() {
		selectionModel.selectPrevious()
		focus(selectionModel.selectedItem)
	}

	internal fun switchTabRight() {
		selectionModel.selectNext()
		focus(selectionModel.selectedItem)
	}

	internal fun focus(tab: Tab) {
		this.selectionModel.select(tab)
		if (tab is EditorTab) {
			tab.requestFocus()
			title = "$name - ${retrieveTabName(tab)}"
		}
	}

	fun showHelp() {
		tabs.find { it.text == "Help" }.onlyIfNull {
			createNewTabInBackground {
				EditorTab(name = "Help", editable = false, content = HELP_TEXT)
			}
		}
	}

	internal fun closeCurrentTab() {
		val tab = selectionModel.selectedItem
		if (tab.notNull()) {
			val editorTab = tab as EditorTab
			tabs.remove(editorTab)
		}
	}

	internal fun saveTab() {
		findOpenTab().save()
	}

	internal fun saveAsNewPath() {
		findOpenTab().saveAs()
	}

	internal fun saveEditedTabs() {
		openTabs().filterNot { it.path == null }.forEach { it.save() }
	}

	internal fun reloadTabIfFileEndingsChanges(savePath: Path) {
		val openTab = findOpenTab()
		tabs.remove(openTab)
		openTabs().filter { savePath == it.path }
				.forEach { tabs.remove(it) }
		createNewTabInBackground {
			EditorTab(path = savePath)
		}
	}

	internal fun closeTabsWithSamePathAsThis(tab: EditorTab, path: Path) {
		openTabs().filter { path == it.path }
				.filter { tab != it }
				.forEach { tabs.remove(it) }
	}

	private fun findOpenTab(): EditorTab {
		return selectionModel.selectedItem as EditorTab
	}

	private fun openTabs(): List<EditorTab> = tabs.map { (it as EditorTab) }

	private fun retrieveTabName(tab: EditorTab) = tab.path?.toString() ?: tab.name

	private fun createNewTabInBackground(tab: () -> EditorTab) {
		task {
			tab.invoke()
		} success {
			tabs.add(it)
			focus(it)
			println("Successful opened tab for ${retrieveTabName(it)}")
		} fail {
			println("Failed to open new tab.")
		}
	}

}
