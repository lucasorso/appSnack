package com.burguer.zap.burguer.rest.cardapio;

/**
 * Created by Anderson Slaifer on 16/11/2017.
 */

public class Item {
    private String titulo;
    private String descricao;
    private int img;
    private float valor;

    public Item(String titulo, String descricao, float valor, int img) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.valor = valor;
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
