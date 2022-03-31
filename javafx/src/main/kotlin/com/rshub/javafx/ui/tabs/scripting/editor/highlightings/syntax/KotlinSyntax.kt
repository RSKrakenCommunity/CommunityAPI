package com.rshub.javafx.ui.tabs.scripting.editor.highlightings.syntax

import java.util.regex.Matcher
import java.util.regex.Pattern

private val KEYWORDS_KOTLIN = arrayOf(
		"package",
		"import",
		"private",
		"public",
		"internal",
		"as",
		"typealias",
		"class",
		"this",
		"super",
		"val",
		"var",
		"fun",
		"for",
		"null",
		"true",
		"false",
		"is",
		"in",
		"throw",
		"return",
		"break",
		"continue",
		"object",
		"if",
		"try",
		"else",
		"while",
		"do",
		"when",
		"interface",
		"typeof"
)

private const val PAREN_PATTERN = "\\(|\\)"
private const val BRACE_PATTERN = "\\{|\\}"
private const val BRACKET_PATTERN = "\\[|\\]"
private const val SEMICOLON_PATTERN = "\\;"
private const val STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"" + "|" + "\'([^\"\\\\]|\\\\.)*\'"
private const val COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/"
private const val ANNOTATION_PATTERN = "@[A-Za-z]+"

const val BASIC_PATTERN = "|(?<ANNOTATION>$ANNOTATION_PATTERN)|(?<PAREN>$PAREN_PATTERN)|(?<BRACE>$BRACE_PATTERN)|(?<BRACKET>$BRACKET_PATTERN)|(?<SEMICOLON>$SEMICOLON_PATTERN)|(?<STRING>$STRING_PATTERN)|(?<COMMENT>$COMMENT_PATTERN)"
private val KOTLIN_KEYWORDS_PATTERN = "\\b(" + KEYWORDS_KOTLIN.joinToString("|") + ")\\b"

val KOTLIN_PATTERN: Pattern = Pattern.compile("(?<KEYWORD>$KOTLIN_KEYWORDS_PATTERN)$BASIC_PATTERN")

fun javaMatching(matcher: Matcher): String? = when {
	matcher.group("KEYWORD") != null -> "keyword"
	matcher.group("ANNOTATION") != null -> "annotation"
	matcher.group("PAREN") != null -> "paren"
	matcher.group("BRACE") != null -> "brace"
	matcher.group("BRACKET") != null -> "bracket"
	matcher.group("SEMICOLON") != null -> "semicolon"
	matcher.group("STRING") != null -> "string"
	matcher.group("COMMENT") != null -> "comment"
	else -> null
}
