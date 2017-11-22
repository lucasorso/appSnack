package com.burguer.zap.burguer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.burguer.zap.burguer.R;
import com.burguer.zap.burguer.rest.generic.BaseRetrofit;
import com.burguer.zap.burguer.rest.generic.GenericResponse;
import com.burguer.zap.burguer.rest.pedido.PedidosRest;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ListaPedidosActivity extends AppCompatActivity {

    private PedidosRest.Api mPedidoResApi;
    private Call<GenericResponse> mCAllInsertPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);
        createInterface();
    }

    private void createInterface() {
        Retrofit lRetrofit = new BaseRetrofit().buildRetrofit();
        mPedidoResApi = lRetrofit.create(PedidosRest.Api.class);
    }
}
