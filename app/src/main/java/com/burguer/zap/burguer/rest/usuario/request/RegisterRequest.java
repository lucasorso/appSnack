package com.burguer.zap.burguer.rest.usuario.request;

import com.burguer.zap.burguer.rest.generic.GenericRequest;
import com.google.gson.annotations.SerializedName;

/**
 * Created by LucasOrso on 10/8/17.
 */

public class RegisterRequest extends GenericRequest {

    @SerializedName("nm_usuario")
    private String Nome;

    @SerializedName("ds_login")
    private String Email;

    @SerializedName("ds_senha")
    private String Password;

    @SerializedName("id_app")
    private Long IdApp = 99L;

    @SerializedName("cliente")
    private String cliente = "Unesc";

    @SerializedName("idConta")
    private Long idConta = 24L;

    public String getNome() {
        return Nome;
    }

    public void setNome(String aNome) {
        Nome = aNome;
    }

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
