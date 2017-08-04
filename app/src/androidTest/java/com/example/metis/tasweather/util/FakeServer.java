package com.example.metis.tasweather.util;


import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Can add mocked responses to the tests
 */
public class FakeServer {

    private static final int DEFAULT_PORT = 9999;
    private static MockWebServer mockWebServer;
    private static PathDispatcher pathDispatcher;

    public static void setup() {
        if(mockWebServer == null) {
            mockWebServer = new MockWebServer();
            pathDispatcher = new PathDispatcher();
            mockWebServer.setDispatcher(pathDispatcher);
            try {
                mockWebServer.start(DEFAULT_PORT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            pathDispatcher.reset();
        }
    }

    public static void enqueueSuccessResponse(String path, String asset) {
        pathDispatcher.putMockedResponse(path, MockResponseBuilder.newBuilder().asset(asset).build());
    }

    public static void enqueueResponse(String path, MockResponse response) {
        pathDispatcher.putMockedResponse(path, response);
    }
}
