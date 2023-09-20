package com.haibox.ffcmd.resp;

public abstract class ExecuteResponse implements IFFmpegExecuteResponse {
    /**
     * on Progress
     * @param message current output of FFmpeg command
     */
    public abstract void onProgress(long message);
}
