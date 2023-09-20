package com.haibox.ffcmd;

import com.haibox.ffcmd.resp.ExecuteResponse;

public interface IFFmpeg {

    /**
     * Executes a command
     * @param cmd command to execute
     * @param response {@link ExecuteResponse}
     */
    void execute(String[] cmd, ExecuteResponse response);


    /**
     * Tells FFmpeg version shipped with current library
     * @return FFmpeg version shipped with Library
     */
    String getLibraryFFmpegVersion();
}
