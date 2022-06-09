package com.techelevator.tenmo.services;

public abstract class BaseService {

    private static String authToken = null;

    public static void setAuthToken(String authToken) {
        BaseService.authToken = authToken;
    }

    public static String getAuthToken() {
        return authToken;
    }
}


