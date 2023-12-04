package com.example.affem

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class AssetCopier(var context: Context, var filenameA: String, var filenameB: String) {
    var `in`: InputStream? = null
    var out: OutputStream? = null
    var a: File? = null
    var b: File

    init {

        //String path = context.getDataDir().getPath().toString() + "/databases/" + filenameB;
        //b = new File(path);
        // b = new File(context.getDataDir().getPath() + "/databases" + filenameB);
        b = context.getDatabasePath(filenameB)
        try {
            copyFromAssets(filenameA)
        } catch (e: IOException) {
            Log.e(TAG, " Exception caught in copyFromAssets()")
        }
    }

    @Throws(IOException::class)
    fun copyFromAssets(filenameA: String?) {
        val assetManager = context.assets
        `in` = assetManager.open(filenameA!!)
        out = FileOutputStream(b)
        try {
            var n: Int
            while (`in`!!.read().also { n = it } != -1) {
                (out as FileOutputStream).write(n)
            }
        } catch (e: IOException) {
            Log.e(TAG, " Exception caught in copyFromAssets()")
        } finally {
            if (`in` != null) {
                `in`!!.close()
            }
            if (out != null) {
                (out as FileOutputStream).close()
            }
        }
        Log.e(TAG, " File copied.")
        Log.e(TAG, " the path to the file is :" + b.path)
    }

    companion object {
        const val TAG = "De-bug Message : "
    }
}