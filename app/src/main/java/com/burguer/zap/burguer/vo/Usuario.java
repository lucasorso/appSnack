package com.burguer.zap.burguer.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by LucasOrso on 10/5/17.
 */

public class Usuario extends RealmObject implements Serializable{

    @PrimaryKey
    @SerializedName("nm_usuario")
    private String mNome;

    @SerializedName("id")
    private Long mIdUsuario;

    @SerializedName("ds_login")
    private String mEmail;

    @SerializedName("id_app")
    private Long mIdApp;

    @SerializedName("ds_senha")
    private String mSenha;

    private Boolean mLoggedUser;

    public String getNome() {
        return mNome;
    }

    public void setNome(String aNome) {
        mNome = aNome;
    }

    public Long getId() {
        return mIdUsuario;
    }

    public void setId(Long aId) {
        mIdUsuario = aId;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String aEmail) {
        mEmail = aEmail;
    }

    public Long getIdApp() {
        return mIdApp;
    }

    public void setIdApp(Long aIdApp) {
        mIdApp = aIdApp;
    }

    public String getSenha() {
        return mSenha;
    }

    public void setSenha(String aSenha) {
        mSenha = aSenha;
    }

    public Long getIdUsuario() {
        return mIdUsuario;
    }

    public void setIdUsuario(Long aIdUsuario) {
        mIdUsuario = aIdUsuario;
    }

    public Boolean getLoggedUser() {
        return mLoggedUser;
    }

    public void setLoggedUser(Boolean aLoggedUser) {
        mLoggedUser = aLoggedUser;
    }
}
