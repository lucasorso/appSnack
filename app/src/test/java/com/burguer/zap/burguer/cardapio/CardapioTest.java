package com.burguer.zap.burguer.cardapio;

import android.support.annotation.NonNull;

import com.burguer.zap.burguer.rest.cardapio.CardapioRest;
import com.burguer.zap.burguer.rest.cardapio.request.CardapioRequest;
import com.burguer.zap.burguer.rest.generic.BaseRetrofit;
import com.burguer.zap.burguer.rest.generic.GenericResponse;
import com.burguer.zap.burguer.vo.Cardapio;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Implements;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by LucasOrso on 10/5/17.
 */

@RunWith(RobolectricTestRunner.class)
@Implements
public class CardapioTest {


    @Test
    public void insertCardapio() throws IOException {
        Retrofit lRetrofit = new BaseRetrofit().buildRetrofit();
        CardapioRest.Api lRest = lRetrofit.create(CardapioRest.Api.class);

        CardapioRequest lRequest = new CardapioRequest();
        lRequest.setId(1L);
        lRequest.setNomePrato("Salgado");
        lRequest.setDescricao("Pão de batata!");
        lRequest.setValor("4,50");

        Call<GenericResponse> lCall = lRest.insertCardapio(lRequest);

        lCall.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    @Test
    public void getListOfCardapio() throws IOException {
        Retrofit lRetrofit = new BaseRetrofit().buildRetrofit();
        CardapioRest.Api lRest = lRetrofit.create(CardapioRest.Api.class);
        Call<List<Cardapio>> lCall = lRest.getListOfCardapio();
        lCall.enqueue(new Callback<List<Cardapio>>() {
            @Override
            public void onResponse(@NonNull Call<List<Cardapio>> call, @NonNull Response<List<Cardapio>> response) {
                System.out.println(response.body());
                // TODO: 11/7/17 Fazer função de teste
            }

            @Override
            public void onFailure(@NonNull Call<List<Cardapio>> call, @NonNull Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
