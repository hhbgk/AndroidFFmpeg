cmake_minimum_required(VERSION 3.6)

set(CPP_PATH ${CMAKE_CURRENT_SOURCE_DIR})

include_directories(${CPP_PATH}/inc/${ANDROID_ABI})
include_directories(${CPP_PATH}/src)

#ffmpeg so文件路径
set(lib_src_DIR ${CMAKE_SOURCE_DIR}/nativeLibs/${ANDROID_ABI})
#动态方式加载ffmepg的so文件 第三方库加载方式
add_library(ffmpeg SHARED IMPORTED)
#引入libffmpeg.so文件
set_target_properties(ffmpeg PROPERTIES IMPORTED_LOCATION ${lib_src_DIR}/libffmpeg.so)

file(GLOB_RECURSE C_FILES_IN_FFMPEG ${CPP_PATH}/src/*.c)

add_library( # Sets the name of the library.
        jni_ff

        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
        ff_cmd_jni.c
        ${C_FILES_IN_FFMPEG}
        )

target_link_libraries( # Specifies the target library.
        jni_ff

        log
        ffmpeg
        z m
        )