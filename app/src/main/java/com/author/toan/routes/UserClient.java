package com.author.toan.routes;

public class UserClient extends BaseClient {
    private static final String BASE_URL = "http://10.0.2.2:8000/api/user/";
    private static final String BASE_URL_ONLINE = "https://zalo.herokuapp.com/api/user/";
    private static APIUserService apiUserService;
    public static APIUserService getInstance() {
        if (apiUserService == null) return createService(APIUserService.class, BASE_URL_ONLINE);
        return apiUserService;
    }
}

