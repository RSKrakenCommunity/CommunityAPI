package com.rshub.utilities

object TextUtils {

    private val cp1252 = charArrayOf(
        '\u20ac', '\u0000', '\u201a', '\u0192', '\u201e', '\u2026', '\u2020', '\u2021', '\u02c6', '\u2030', '\u0160',
        '\u2039', '\u0152', '\u0000', '\u017d', '\u0000', '\u0000', '\u2018', '\u2019', '\u201c', '\u201d', '\u2022',
        '\u2013', '\u2014', '\u02dc', '\u2122', '\u0161', '\u203a', '\u0153', '\u0000', '\u017e', '\u0178'
    )

    fun cp1252ToChar(i: Byte): Char {
        var cp1252 = i.toInt() and 0xff
        require(0 != cp1252) { "Non cp1252 character 0x" + cp1252.toString(16) + " provided" }
        if (cp1252 in 128..159) {
            var translated = TextUtils.cp1252[cp1252 - 128].code
            if (translated == 0) {
                translated = 63
            }
            cp1252 = translated
        }
        return cp1252.toChar()
    }

    fun charToCp1252(c: Char): Int {
        if (c.code > 0 && c < '\u0080' || c in '\u00a0'..'\u00ff')
            return c.code

        return when (c) {
            '\u20ac' -> (-128)
            '\u201a' -> (-126)
            '\u0192' -> (-125)
            '\u201e' -> (-124)
            '\u2026' -> (-123)
            '\u2020' -> (-122)
            '\u2021' -> (-121)
            '\u02c6' -> (-120)
            '\u2030' -> (-119)
            '\u0160' -> (-118)
            '\u2039' -> (-117)
            '\u0152' -> (-116)
            '\u017d' -> (-114)
            '\u2018' -> (-111)
            '\u2019' -> (-110)
            '\u201c' -> (-109)
            '\u201d' -> (-108)
            '\u2022' -> (-107)
            '\u2013' -> (-106)
            '\u2014' -> (-105)
            '\u02dc' -> (-104)
            '\u2122' -> (-103)
            '\u0161' -> (-102)
            '\u203a' -> (-101)
            '\u0153' -> (-100)
            '\u017e' -> (-98)
            '\u0178' -> (-97)
            else -> 63
        }
    }
}