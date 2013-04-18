package me.harius.hat;

import com.github.kevinsawicki.http.HttpRequest;

/**
 * User: harius
 * Date: 4/18/13
 * Time: 11:03 PM
 */
public class ConnectionError extends Exception {
    public ConnectionError(String what, Exception cause) {
        super(what, cause);
    }
}
