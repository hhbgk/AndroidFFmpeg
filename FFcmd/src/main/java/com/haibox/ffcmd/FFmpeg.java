package com.haibox.ffcmd;

import android.util.Log;

import com.haibox.ffcmd.resp.ExecuteResponse;
import com.haibox.ffcmd.task.ExecuteAsyncTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class FFmpeg implements IFFmpeg {
    private static volatile boolean mIsLibLoaded = false;
    private final SimpleDateFormat sdf;
    private interface LibLoader {
        void loadLibrary(String libName) throws UnsatisfiedLinkError, SecurityException;
    }
    private static final LibLoader sLocalLibLoader = new LibLoader() {
        @Override
        public void loadLibrary(String libName) throws UnsatisfiedLinkError, SecurityException {
            System.loadLibrary(libName);
        }
    };
    private static void loadLibrariesOnce(LibLoader libLoader) {
        synchronized (FFmpeg.class) {
            if (!mIsLibLoaded) {
                if (libLoader == null)
                    libLoader = sLocalLibLoader;

                libLoader.loadLibrary("jni_ff");
                mIsLibLoaded = true;
            }
        }
    }

    public FFmpeg() {
        FFmpeg.loadLibrariesOnce(sLocalLibLoader);
        init();
        setEnableFFmpegLog(false);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    }

    public void setEnableFFmpegLog(boolean enable) {
        enableLog(enable);
    }


    private void onNativeProgress(String progress) {
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        String inputString = "00:01:30.500";
        Date date = null;
        try {
            date = sdf.parse("1970-01-01 " + progress);
            Log.e("test", "Progress=" + progress + ", date=" + date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (mExecuteAsyncTask != null && date != null) {
            mExecuteAsyncTask.updateProgress(date.getTime());
        }
    }

    private native void init();
    private native void enableLog(boolean enable);
    public native boolean runCommand(String[] cmd);

    private ExecuteAsyncTask mExecuteAsyncTask;
    @Override
    public void execute(String[] cmd, ExecuteResponse response) {
        if (cmd == null)
            throw new NullPointerException("commands is null");
        if (mExecuteAsyncTask != null && !mExecuteAsyncTask.isExecuteCompleted()) {
            throw new IllegalAccessError("FFmpeg command is already running, you are only allowed to run single command at a time");
        }
        if (cmd.length != 0) {
            mExecuteAsyncTask = new ExecuteAsyncTask(cmd , this, 0, response);
            mExecuteAsyncTask.execute();
        } else {
            throw new IllegalArgumentException("shell command cannot be empty");
        }
    }

    @Override
    public String getLibraryFFmpegVersion() {
        return null;
    }
}
