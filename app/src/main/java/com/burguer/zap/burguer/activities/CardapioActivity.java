package com.burguer.zap.burguer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.burguer.zap.burguer.R;
import com.burguer.zap.burguer.rest.cardapio.Item;
import com.burguer.zap.burguer.rest.cardapio.ItemAdapter;
import com.burguer.zap.burguer.rest.usuario.UserRest;
import com.burguer.zap.burguer.vo.Cardapio;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Anderson Slaifer on 16/11/2017.
 */

public class CardapioActivity extends AppCompatActivity {

    private Call<List<Cardapio>> mCAllGetUser;
    private UserRest.Api mUserRestApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentos);

        String[] nomeAlimento = new String[5];
        String[] descricaoAlimento = new String[5];
        String[] valorAlimento = new String[5];


        ArrayList<Item> lista = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String titulo = nomeAlimento[i];
            String desc = descricaoAlimento[i];
            Float valor = Float.parseFloat(valorAlimento[i]);

            Item item = new Item(titulo, desc, valor, R.mipmap.ic_launcher);
            lista.add(item);
        }

        ItemAdapter adapter = new ItemAdapter(this, lista, R.color.gray);

        ListView view = (ListView) findViewById(R.id.alimentosView);
        view.setAdapter((ListAdapter) adapter);
    }
}
