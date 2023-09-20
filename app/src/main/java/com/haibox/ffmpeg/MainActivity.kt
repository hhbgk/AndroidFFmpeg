package com.haibox.ffmpeg

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.haibox.ffmpeg.databinding.ActivityMainBinding
import com.haibox.ffmpeg.tool.FFmpegHelper
import java.io.File

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etCommand.hint = "ffmpeg -i video.mp4 video.avi"
//        val root = getExternalFilesDir(null)?.absolutePath
//        val srcPath = "$root/test.mp4"
//        val dstPath = "$root/video.avi"
//        val cmd = "ffmpeg -i $srcPath -acodec copy -c:v mjpeg $dstPath"
//        binding.etCommand.setText(cmd)

        binding.btnCommand.setOnClickListener {
//            val file = File(dstPath)
//            if (file.exists()) file.delete()
            val command = binding.etCommand.text.toString().trim()
            Log.i(TAG, "cmd:$command")
            FFmpegHelper.executeCommand(command) {
                val msg = if (it) {
                    "Success"
                } else {
                    "Failed"
                }
                Log.i(TAG, msg)
                show(msg)
            }
        }

        binding.btnExample.setOnClickListener {
            val filepath = getExternalFilesDir(null)?.absolutePath + "/test.avi"
            val file = File(filepath)
            FFmpegHelper.convertAviToMp4(file) {
                val msg = if (it) {
                    "Convert OK"
                } else {
                    "Convert failed"
                }
                Log.i(TAG, msg)
                show(msg)
            }
        }
    }

    private fun show(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}