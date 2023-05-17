package com.author.toan.routes;

public class ChatClient extends BaseClient {
    private static final String BASE_URL = "http://10.0.2.2:8000/api/chat/";
    private static final String BASE_URL_ONLINE = "https://zalo.herokuapp.com/api/chat/";
    private static APIChatService apiChatService;
    public static APIChatService getInstance() {
        if (apiChatService == null) return createService(APIChatService.class, BASE_URL_ONLINE);
        return apiChatService;
    }
}
