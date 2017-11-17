package com.burguer.zap.burguer.cardapio;

import com.burguer.zap.burguer.rest.cardapio.CardapioRest;
import com.burguer.zap.burguer.rest.cardapio.request.CardapioRequest;
import com.burguer.zap.burguer.rest.generic.BaseRetrofit;
import com.burguer.zap.burguer.rest.generic.GenericResponse;
import com.burguer.zap.burguer.vo.Cardapio;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by LucasOrso on 10/5/17.
 */

@RunWith(RobolectricTestRunner.class)
public class CardapioTest {

    @Before
    public void setup() throws Exception {
    }

    @Config(sdk = 23)
    @Test
    public void insertCardapio() throws IOException {
        Retrofit lRetrofit = new BaseRetrofit().buildRetrofit();
        CardapioRest.Api lRest = lRetrofit.create(CardapioRest.Api.class);

        CardapioRequest lRequest = new CardapioRequest();
        lRequest.setNomePrato("Classe");
        lRequest.setDescricao("teste de classe");
        lRequest.setValor("4,50");

        Call<GenericResponse> lCall = lRest.insertCardapio(lRequest);

        try {
            Response<GenericResponse> lExecute = lCall.execute();
            GenericResponse lBody = lExecute.body();

            assert (lBody != null && lBody.getSuccess());

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
            lCall.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenericResponse> call, @NonNull Response<GenericResponse> response) {
                GenericResponse lBody = response.body();
                if (lBody != null) {
                    System.out.println(lBody);
                    assert (lBody.getSuccess());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GenericResponse> call, @NonNull Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        */

    }

    @Config(sdk = 23)
    @Test
    public void getListOfCardapio() throws IOException {
        Retrofit lRetrofit = new BaseRetrofit().buildRetrofit();
        CardapioRest.Api lRest = lRetrofit.create(CardapioRest.Api.class);
        Call<List<Cardapio>> lCall = lRest.getListOfCardapio();

        try {
            Response<List<Cardapio>> lExecute = lCall.execute();
            List<Cardapio> lBody = lExecute.body();

            assert (lBody != null && lBody.size() > 0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*lCall.enqueue(new Callback<List<Cardapio>>() {
            @Override
            public void onResponse(@NonNull Call<List<Cardapio>> call, @NonNull Response<List<Cardapio>> response) {
                List<Cardapio> lBody = response.body();
                if (lBody != null) {
                    assert (lBody.size() > 0);
                    for (Cardapio lCardapio : lBody) {
                        System.out.println(lCardapio);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Cardapio>> call, @NonNull Throwable t) {
                System.out.println(t.getMessage());
            }
        });*/
    }
}
