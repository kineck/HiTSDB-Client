package com.aliyun.hitsdb.client.exception.http;

import com.aliyun.hitsdb.client.http.response.ResultResponse;

public class HttpServerErrorException extends HttpUnknowStatusException {

    public HttpServerErrorException(int status, String message) {
        super(status, message);
    }

    public HttpServerErrorException(ResultResponse result) {
        super(result);
    }

    private static final long serialVersionUID = -7013317353179949073L;
}