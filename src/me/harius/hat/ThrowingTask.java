package me.harius.hat;

import android.os.AsyncTask;

/**
 * User: harius
 * Date: 4/19/13
 * Time: 10:06 AM
 */
public abstract class ThrowingTask<A, B, C> extends AsyncTask<A, B, C> {
    private Exception exception = null;

    protected void saveException(Exception e) {
        exception = e;
    }

    public void throwException() throws Exception {
        if (exception != null) {
            throw exception;
        }
    }
}
