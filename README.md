# AndroidFFmpeg
<img src="https://github.com/hhbgk/AndroidFFmpeg/blob/master/pic/screenshot.png" width="300">

Currently, this project support some of ffmpeg command operation only, like convertion, but no play features. Also, it contains a few codecs only, but you can add and build your own.

## Test
  [APK demo](https://github.com/hhbgk/AndroidFFmpeg/blob/master/app/apk/)

## Features
1. FFmpeg-4.0.2 + openh264-2.1.1
2. Execute command on Android APP
3. support "armeabi-v7a", "arm64-v8a"

## Usage
```kotlin
private var ffmpeg = FFmpeg()

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
```



## Build
1. Download [FFmpeg 4.0.2](https://github.com/FFmpeg/FFmpeg/releases/tag/n4.0.2) and [openh264 2.1.1](https://github.com/cisco/openh264/releases/tag/v2.1.1) to your directory, like MyFFmpeg. Then unzip them.
2. Use [scripts](https://github.com/hhbgk/AndroidFFmpeg/tree/master/script) to build your FFmpeg. And put them in MyFFmpeg.
 **Note: change codes in FFmpeg/configure file:**
 ```bash
	#Comments the codes and replace new codes in configure:
	#enabled libopenh264       && require_pkg_config libopenh264 openh264 wels/codec_api.h WelsGetCodecVersion
	enabled libopenh264       && require  libopenh264 wels/codec_api.h WelsGetCodecVersion -lopenh264 -lm -lstdc++
```

3. Output dir: `MyFFmpeg/libs`

## Thanks
1. [FFmpeg_Build4Android](https://github.com/alanwang4523/FFmpeg_Build4Android)

	
