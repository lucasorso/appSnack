package com.burguer.zap.burguer.rest.generic;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LucasOrso on 11/7/17.
 */

public class GenericResponse<T> {

    @SerializedName("sucesso")
    private Boolean mSuccess;

    @SerializedName("mensagem")
    private String mMessage;

    @SerializedName("dados")
    private T mData;

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean aSuccess) {
        mSuccess = aSuccess;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String aMessage) {
        mMessage = aMessage;
    }

    public T getData() {
        return mData;
    }

    public void setData(T aData) {
        mData = aData;
    }
}
