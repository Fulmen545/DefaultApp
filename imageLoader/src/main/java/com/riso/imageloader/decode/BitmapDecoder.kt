package com.riso.imageloader.decode

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

class BitmapDecoder {
    fun fromBytes(bytes: ByteArray): Bitmap =
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            ?: error("Cannot decode bitmap")


    fun fromFile(file: File): Bitmap =
        BitmapFactory.decodeFile(file.absolutePath)
            ?: error("Cannot decode bitmap file")

}