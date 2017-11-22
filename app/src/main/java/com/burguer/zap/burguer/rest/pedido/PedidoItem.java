package com.burguer.zap.burguer.rest.pedido;

import java.util.ArrayList;

/**
 * Created by Anderson Slaifer on 22/11/2017.
 */

public class PedidoItem extends ArrayList {

    private long id_pedido;
    private long id_item;
    private String ds_item;
    private float vl_item;
    private long id_conta;

    public long getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public long getId_item() {
        return id_item;
    }

    public void setId_item(long id_item) {
        this.id_item = id_item;
    }

    public String getDs_item() {
        return ds_item;
    }

    public void setDs_item(String ds_item) {
        this.ds_item = ds_item;
    }

    public float getVl_item() {
        return vl_item;
    }

    public void setVl_item(float vl_item) {
        this.vl_item = vl_item;
    }

    public long getId_conta() {
        return id_conta;
    }

    public void setId_conta(long id_conta) {
        this.id_conta = id_conta;
    }
}
