package com.burguer.zap.burguer.rest.pedido.request;

import com.burguer.zap.burguer.rest.pedido.PedidoItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Anderson Slaifer on 22/11/2017.
 */

public class PedidosRequest {

    @SerializedName("id_pedido")
    private Long mId;

    @SerializedName("ds_status")
    private String mStatus;

    @SerializedName("id_conta")
    private Long mId_conta;

    @SerializedName("list_item")
    private List<PedidoItem> mPedidoItem;

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public Long getmId_conta() {
        return mId_conta;
    }

    public void setmId_conta(Long mId_conta) {
        this.mId_conta = mId_conta;
    }

    public List<PedidoItem> getmPedidoItem() {
        return mPedidoItem;
    }

    public void setmPedidoItem(List<PedidoItem> mPedidoItem) {
        this.mPedidoItem = mPedidoItem;
    }
}
