# fe-app-zalo

Cách chạy:
 - Đầu tiền git clone project về

Nếu muốn chạy trên local thì config sau:
 - Sau đó config các file Client trong folder router. Chỉnh BASE_URL_ONLINE trong if (apiChatService == null) return createService(APIChatService.class, BASE_URL_ONLINE); 
thành BASE_URL.
 - Sau đó git clone BE về: https://github.com/pvhao2002/api-app-zalo.
 - Và làm theo hướng dẫn trong file readme ở repo BE

Nếu muốn chạy onlien thì chỉ cần gitclone project fe-app-zalo về là được.
