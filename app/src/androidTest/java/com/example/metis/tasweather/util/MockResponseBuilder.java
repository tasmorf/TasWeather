package com.example.metis.tasweather.util;


import android.support.test.InstrumentationRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.mockwebserver.MockResponse;

public class MockResponseBuilder {

    private int code = HttpURLConnection.HTTP_OK;
    private String asset;
    private Map<String, String> tokenReplacements = new HashMap<>();

    public static MockResponseBuilder newBuilder() {
        return new MockResponseBuilder();
    }

    public MockResponseBuilder code(int code) {
        this.code = code;
        return this;
    }

    public MockResponseBuilder asset(String asset) {
        this.asset = asset;
        return this;
    }

    public MockResponseBuilder tokenreplacement(String token, String value) {
        tokenReplacements.put(token, value);
        return this;
    }

    public MockResponse build() {
        MockResponse result = new MockResponse();
        result.setResponseCode(code);
        result.setBody(generateBody());
        return result;
    }

    private String generateBody() {
        if (asset != null) {
            try {
                InputStream is = InstrumentationRegistry.getContext().getResources().getAssets().open(asset + ".json");
                StringBuilder buf = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String str;
                while ((str = in.readLine()) != null) {
                    buf.append(str);
                }
                in.close();
                String body = buf.toString();
                for (String token : tokenReplacements.keySet()) {
                    body = body.replace(token, tokenReplacements.get(token));
                }
                return body;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }
}
