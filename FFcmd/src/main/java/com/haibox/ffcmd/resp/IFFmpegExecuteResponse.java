package com.haibox.ffcmd.resp;

public interface IFFmpegExecuteResponse {

    /**
     * on Success
     */
    void onSuccess();

    /**
     * on Failure
     * @param message complete output of the FFmpeg command
     */
    void onFailure(String message);

    void onStart();

    void onFinish();
}
