package com.rshub.javafx.ui.tabs.scripting.editor.util

import com.rshub.javafx.ui.tabs.scripting.editor.EditorPane
import com.rshub.javafx.ui.tabs.scripting.editor.EditorTab
import javafx.event.EventTarget
import javafx.scene.control.TabPane
import tornadofx.opcr
import java.nio.file.Path

/**
 * @author Artur Bosch
 */

fun EventTarget.editorPane(op: EditorPane.() -> Unit) =
		opcr(this, EditorPane(), op)

fun TabPane.editorTab(
	name: String = "",
	content: String = "",
	path: Path? = null,
	editable: Boolean = true,
	isNewScript: Boolean = false,
	op: (EditorTab.() -> Unit)? = null
): EditorTab {
	val tab = EditorTab(
		name = name,
		content = content,
		path = path,
		editable = editable,
		newBlankScript = isNewScript
	)
	tabs.add(tab)
	op?.invoke(tab)
	return tab
}

fun Any?.notNull(): Boolean = this != null

inline fun <T, R> T.onlyIfNull(block: T?.() -> R) {
	if (this == null) {
		block()
	}
}

fun String.replaceLast(oldChars: String, newChars: String, ignoreCase: Boolean = false): String {
	val index = lastIndexOf(oldChars, ignoreCase = ignoreCase)
	return if (index < 0) this else this.replaceRange(index, index + oldChars.length, newChars)
}
