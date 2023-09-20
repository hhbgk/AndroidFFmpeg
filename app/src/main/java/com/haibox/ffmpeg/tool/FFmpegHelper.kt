package com.haibox.ffmpeg.tool

import android.util.Log
import androidx.core.util.Consumer
import com.haibox.ffcmd.FFmpeg
import com.haibox.ffcmd.resp.ExecuteResponse
import java.io.File

object FFmpegHelper {
    const val TAG = "FFmpegHelper"
    private var ffmpeg = FFmpeg()

    init {
        ffmpeg.setEnableFFmpegLog(false) // Enable debug
    }
    fun executeCommand(command: String, consumer: Consumer<Boolean>? = null) {
        if (command.isEmpty()) consumer?.accept(false)

        val cmdArray = command.split(" ").toList().toTypedArray()
        ffmpeg.execute(cmdArray, object : ExecuteResponse() {
            override fun onSuccess() {
                Log.w(TAG, "on Success")
                consumer?.accept(true)
            }

            override fun onFailure(message: String?) {
                Log.e(TAG, "on Failure:$message")
                consumer?.accept(false)
            }

            override fun onStart() {
                Log.i(TAG, "on start")
            }

            override fun onFinish() {
                Log.i(TAG, "on Finish")
            }

            override fun onProgress(message: Long) {
                Log.i(TAG, "on Progress:$message")
            }
        })
    }

    fun convertAviToMp4(srcFile: File, callback: Consumer<Boolean>? = null) {
        if (!srcFile.exists()) {
            Log.w(TAG, "File not exist:${srcFile.absolutePath}")
            callback?.accept(false)
            return
        }

        ffmpeg.setEnableFFmpegLog(true)
        val dstFilePath = srcFile.absolutePath.replace("avi", "mp4", true)
        Log.i(TAG, "Dst Path=$dstFilePath")
        val detFile = File(dstFilePath)
        if(detFile.exists()) detFile.delete()
        val cmd = "ffmpeg -i ${srcFile.absolutePath} -acodec copy $dstFilePath"
        val cmdArray = cmd.split(" ").toList().toTypedArray()
        ffmpeg.execute(cmdArray, object : ExecuteResponse() {
            override fun onSuccess() {
                Log.w(TAG, "on Success")
                if (!srcFile.delete()) {
                    Log.w(TAG, "Delete failed: ${srcFile.absolutePath}")
                }
                callback?.accept(true)
            }

            override fun onFailure(message: String?) {
                Log.e(TAG, "on Failure:$message")
                callback?.accept(false)
            }

            override fun onStart() {
                Log.i(TAG, "on start")
            }

            override fun onFinish() {
                Log.i(TAG, "on Finish")
            }

            override fun onProgress(message: Long) {
                Log.i(TAG, "on Progress:$message")
            }
        })
    }
}