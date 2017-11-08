package com.burguer.zap.burguer.rest.usuario.request;

import com.burguer.zap.burguer.rest.generic.GenericRequest;
import com.google.gson.annotations.SerializedName;

/**
 * Created by LucasOrso on 10/8/17.
 */

public class LoginRequest extends GenericRequest {

    @SerializedName("ds_login")
    private String Email;

    @SerializedName("ds_senha")
    private String Password;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String aEmail) {
        Email = aEmail;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String aPassword) {
        Password = aPassword;
    }
}
