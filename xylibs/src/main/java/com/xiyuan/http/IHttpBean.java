package com.xiyuan.http;

import com.google.gson.JsonObject;

/**
 * Created by YT on 2015/10/8.
 */
public interface IHttpBean {

    void onResponseSuccess(JsonObject response);

    void onErrorResponse(Exception e);

}
