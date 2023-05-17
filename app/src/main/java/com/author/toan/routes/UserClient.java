package com.author.toan.routes;

public class UserClient extends BaseClient {
    private static final String BASE_URL = "http://192.168.1.239:8000/api/user/";
    private static final String BASE_URL_ONLINE = "https://zalo.herokuapp.com/api/user/";
    private static APIUserService apiUserService;
    public static APIUserService getInstance() {
        if (apiUserService == null) return createService(APIUserService.class, BASE_URL);
        return apiUserService;
    }
}

