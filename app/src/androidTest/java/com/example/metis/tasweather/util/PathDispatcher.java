package com.example.metis.tasweather.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

public class PathDispatcher extends Dispatcher {

    private static final String TAG = PathDispatcher.class.getSimpleName();

    private Map<String, MockResponse> responseMap = new HashMap<>();

    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        String requestPath = request.getPath();
        Log.d(TAG, "Dispatch for requestPath: " + requestPath);
        if (requestPath.contains("?")) {
            requestPath = requestPath.substring(0, requestPath.indexOf('?'));
        }
        MockResponse mockResponse = null;
        for (String keyPath : responseMap.keySet()) {
            if (Pattern.matches(wildcardToRegex(keyPath), requestPath)) {
                mockResponse = responseMap.get(keyPath);
                break;
            }
        }
        if (mockResponse == null) {
            mockResponse = new MockResponse();
            mockResponse.setResponseCode(200);
        }
        return mockResponse;
    }

    public void reset() {
        responseMap.clear();
    }

    /**
     * Put a mocked response for a given path. Accepts wildcards. Don't put parameters here, as they are ignored.
     */
    public void putMockedResponse(String path, MockResponse mockResponse) {
        responseMap.put(path, mockResponse);
    }

    private static String wildcardToRegex(String wildcard) {
        StringBuffer s = new StringBuffer(wildcard.length());
        s.append('^');
        for (int i = 0, is = wildcard.length(); i < is; i++) {
            char c = wildcard.charAt(i);
            switch (c) {
                case '*':
                    s.append(".*");
                    break;
                case '?':
                    s.append(".");
                    break;
                // escape special regexp-characters
                case '(':
                case ')':
                case '[':
                case ']':
                case '$':
                case '^':
                case '.':
                case '{':
                case '}':
                case '|':
                case '\\':
                    s.append("\\");
                    s.append(c);
                    break;
                default:
                    s.append(c);
                    break;
            }
        }
        s.append('$');
        return (s.toString());
    }
}
