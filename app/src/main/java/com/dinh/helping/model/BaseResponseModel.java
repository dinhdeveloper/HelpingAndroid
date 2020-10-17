package com.dinh.helping.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class BaseResponseModel <T> implements Serializable {
    @Nullable
    private String success;

    @Nullable
    private String message;

    @Nullable
    private String total_page;

    @Nullable
    private String error_code;

    @Nullable
    private T[] data;

    @Nullable
    public String getSuccess() {
        return success;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @Nullable
    public T[] getData() {
        return data;
    }

    @Nullable
    public String getTotal_page() {
        return total_page;
    }

    @Nullable
    public String getError_code() {
        return error_code;
    }
}
