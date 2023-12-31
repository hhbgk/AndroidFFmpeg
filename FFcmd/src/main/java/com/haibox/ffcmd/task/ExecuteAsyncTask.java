package com.haibox.ffcmd.task;

import android.os.AsyncTask;

import com.haibox.ffcmd.FFmpeg;
import com.haibox.ffcmd.resp.ExecuteResponse;


public class ExecuteAsyncTask extends AsyncTask<Void, Long, Boolean> {
    private String tag = getClass().getSimpleName();
    private final String[] cmd;
    private final ExecuteResponse mExecuteResponse;
    private final FFmpeg shellCommand;
    private final long timeout;
    private long startTime;
    private boolean isCompleted = false;

    public ExecuteAsyncTask(String[] cmd, FFmpeg fFmpeg, long timeout, ExecuteResponse response) {
        this.cmd = cmd;
        this.timeout = timeout;
        this.mExecuteResponse = response;
        this.shellCommand = fFmpeg;
    }

    @Override
    protected void onPreExecute() {
        isCompleted = false;
        startTime = System.currentTimeMillis();
        if (mExecuteResponse != null) {
            mExecuteResponse.onStart();
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return shellCommand.runCommand(cmd);
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        if (values != null && values[0] != null && mExecuteResponse != null) {
            mExecuteResponse.onProgress(values[0]);
        }
    }

    public void updateProgress(long values) {
        publishProgress(values);
    }

    @Override
    protected void onPostExecute(Boolean commandResult) {
        isCompleted = true;
        if (commandResult) {
            if (mExecuteResponse != null) {
                mExecuteResponse.onSuccess();
            }
        } else {
            if (mExecuteResponse != null) {
                mExecuteResponse.onFailure("");
            }
        }
        if (mExecuteResponse != null) {
            mExecuteResponse.onFinish();
        }
    }

    public boolean isExecuteCompleted() {
        return isCompleted;
    }
}
