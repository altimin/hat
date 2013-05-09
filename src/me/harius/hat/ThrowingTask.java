package me.harius.hat;

import android.os.AsyncTask;

// TODO: use this awesome stuff

/**
 * User: harius
 * Date: 4/19/13
 * Time: 10:06 AM
 */
public abstract class ThrowingTask <A, B, C> extends AsyncTask<A, B, C> {
    private Exception exception = null;

    private void saveException(Exception e) {
        exception = e;
    }

    public void throwException() throws Exception {
        if (exception != null) {
            throw exception;
        }
    }
}
