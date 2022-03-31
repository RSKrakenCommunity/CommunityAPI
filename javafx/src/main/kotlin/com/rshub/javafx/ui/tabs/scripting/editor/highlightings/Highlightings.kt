package com.rshub.javafx.ui.tabs.scripting.editor.highlightings

import com.rshub.javafx.ui.tabs.scripting.editor.highlightings.syntax.KOTLIN_PATTERN
import com.rshub.javafx.ui.tabs.scripting.editor.highlightings.syntax.javaMatching
import org.fxmisc.richtext.model.StyleSpans
import org.fxmisc.richtext.model.StyleSpansBuilder
import java.nio.file.Path
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author Artur Bosch
 */

fun syntax(text: String, path: Path?, isNewScript: Boolean = false): StyleSpans<Collection<String>>? {
	if (path == null && !isNewScript) return null
	if(isNewScript) {
		return computeHighlighting(text, KOTLIN_PATTERN, ::javaMatching)
	}
	return when (path.toString().substringAfterLast(".")) {
		"kt" -> computeHighlighting(text, KOTLIN_PATTERN, ::javaMatching)
		else -> null
	}
}

private fun computeHighlighting(text: String, pattern: Pattern,
								groupMatching: (Matcher) -> String?): StyleSpans<Collection<String>> {
	val matcher = pattern.matcher(text)
	var lastKwEnd = 0
	val spansBuilder = StyleSpansBuilder<Collection<String>>()
	while (matcher.find()) {
		val styleClass: String? = groupMatching.invoke(matcher)!!
		spansBuilder.add(emptyList(), matcher.start() - lastKwEnd)
		spansBuilder.add(setOf(styleClass ?: ""), matcher.end() - matcher.start())
		lastKwEnd = matcher.end()
	}
	spansBuilder.add(emptyList(), text.length - lastKwEnd)
	return spansBuilder.create()
}
