package com.vishnu

import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.StandardCharsets.UTF_8
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

fun gzip(content: String): ByteArray {
    val bos = ByteArrayOutputStream()
    GZIPOutputStream(bos).bufferedWriter(UTF_8).use { it.write(content) }
    return bos.toByteArray()
}

fun ungzip(content: ByteArray): String =
    GZIPInputStream(content.inputStream()).bufferedReader(UTF_8).use { it.readText() }

fun main(args: Array<String>) {
    if (args.size < 2) return

    val zippedFilename = args[2]
    if (args[0] == "zip") {
        println("zipping ${args[1]} to $zippedFilename")
        val fileContents = File(args[1]).readText()
        val zipped = gzip(fileContents)
        File(zippedFilename).writeBytes(zipped)
        println("zipped file size: ${zipped.size}")

        ungzip(zipped)
    } else if (args[0] == "unzip") {
        val unzipFilename = "${args[1]}.unzipped.xml"
        println("unzipping $zippedFilename to $unzipFilename")

        val fileContents = File(zippedFilename).readBytes()
        println("zipped file size: ${fileContents.size}")
        File(unzipFilename).writeText(ungzip(fileContents))
    }

    println("done")
}
