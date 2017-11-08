package com.burguer.zap.burguer.rest.cardapio.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LucasOrso on 11/7/17.
 */

public class CardapioRequest {

    @SerializedName("id")
    private Long mId;

    @SerializedName("nm_prato")
    private String mNomePrato;

    @SerializedName("ds_descricao")
    private String mDescricao;

    @SerializedName("ds_valor")
    private String mValor; // String Realy ??

    @SerializedName("dh_inc")
    private String mDataInserida;

    @SerializedName("cliente")
    private String mCliente;

    @SerializedName("idConta")
    private Long mIdConta;

    public Long getId() {
        return mId;
    }

    public void setId(Long aId) {
        mId = aId;
    }

    public String getNomePrato() {
        return mNomePrato;
    }

    public void setNomePrato(String aNomePrato) {
        mNomePrato = aNomePrato;
    }

    public String getDescricao() {
        return mDescricao;
    }

    public void setDescricao(String aDescricao) {
        mDescricao = aDescricao;
    }

    public String getValor() {
        return mValor;
    }

    public void setValor(String aValor) {
        mValor = aValor;
    }

    public String getDataInserida() {
        return mDataInserida;
    }

    public void setDataInserida(String aDataInserida) {
        mDataInserida = aDataInserida;
    }

    public String getCliente() {
        return mCliente;
    }

    public void setCliente(String aCliente) {
        mCliente = aCliente;
    }

    public Long getIdConta() {
        return mIdConta;
    }

    public void setIdConta(Long aIdConta) {
        mIdConta = aIdConta;
    }
}
