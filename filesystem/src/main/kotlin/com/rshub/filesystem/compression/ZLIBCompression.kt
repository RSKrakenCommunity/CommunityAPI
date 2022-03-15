package com.rshub.filesystem.compression

import java.util.zip.Inflater

object ZLIBCompression {
    fun decompress(compressed: ByteArray, uncompressedSize: Int): ByteArray {
        val inflater = Inflater()
        inflater.setInput(compressed)
        val infData = ByteArray(uncompressedSize)
        inflater.inflate(infData)
        return infData
    }
}