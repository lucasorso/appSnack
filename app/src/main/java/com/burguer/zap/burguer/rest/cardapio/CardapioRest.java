package com.burguer.zap.burguer.rest.cardapio;

import com.burguer.zap.burguer.rest.cardapio.request.CardapioRequest;
import com.burguer.zap.burguer.rest.generic.GenericResponse;
import com.burguer.zap.burguer.vo.Cardapio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by LucasOrso on 11/7/17.
 */

public class CardapioRest {

    public interface Api {

        @GET("api/unesc/cardapiobuscar")
        Call<List<Cardapio>> getListOfCardapio();

        @POST("api/unesc/cardapioinserir")
        Call<GenericResponse> insertCardapio(@Body CardapioRequest aRequest);
    }
}
