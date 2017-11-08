package com.burguer.zap.burguer.rest.usuario;

import com.burguer.zap.burguer.rest.usuario.request.RegisterRequest;
import com.burguer.zap.burguer.vo.Usuario;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by LucasOrso on 10/5/17.
 */

public class UserRest {

    public interface Api {
        @POST("api/unesc/usuarioinserir")
        Call<Usuario> registerUser(@Body RegisterRequest aRequest);

        @GET("api/unesc/usuariobuscar")
        Call<List<Usuario>> doLogin(@QueryMap Map<String, String> aParams);

        @GET("usuario")
        Call<List<Usuario>> getUsersList();
    }
}