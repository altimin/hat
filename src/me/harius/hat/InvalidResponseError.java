package me.harius.hat;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonSyntaxException;

/**
 * User: harius
 * Date: 4/18/13
 * Time: 11:06 PM
 */
public class InvalidResponseError extends Exception {
    public InvalidResponseError(HttpRequest request, Exception cause) {
        super("Invalid response from server." +
                "Code: " + request.code() +
                "Body: \n" + request.body() + "\n-----Body end-----", cause);
    }
}
