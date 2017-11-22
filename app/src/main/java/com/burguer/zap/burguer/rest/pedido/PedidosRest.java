package com.burguer.zap.burguer.rest.pedido;

import com.burguer.zap.burguer.rest.pedido.request.PedidosRequest;
import com.burguer.zap.burguer.rest.generic.GenericResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Anderson Slaifer on 22/11/2017.
 */

public class PedidosRest {

    public interface Api {

        @GET("api/unesc/pedidobuscar")
        Call<List<PedidosRequest>> getListOfPedido();

        @POST("api/unesc/pedidoinserir")
        Call<GenericResponse> insertPedido(@Body PedidosRequest aRequest);
    }
}
