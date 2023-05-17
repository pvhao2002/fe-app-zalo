package com.author.toan.routes;

public class MessageClient extends BaseClient {
    private static final String BASE_URL = "http://192.168.1.239:8000/api/message/";
    private static final String BASE_URL_ONLINE = "https://zalo.herokuapp.com/api/message/";
    private static APIMessageService apiMessageService;
    public static APIMessageService getInstance() {
        if (apiMessageService == null) return createService(APIMessageService.class, BASE_URL);
        return apiMessageService;
    }
}
