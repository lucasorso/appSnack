package com.burguer.zap.burguer.rest.cardapio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.burguer.zap.burguer.R;

import java.util.ArrayList;

import static android.R.id.list;

/**
 * Created by Anderson Slaifer on 16/11/2017.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    int backgroundColor;
    public  ItemAdapter(Context context, ArrayList<Item> items, int backgroundColor){
        super(context,0,list);
        this.backgroundColor = backgroundColor;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Item item = getItem(position);

        TextView titulo = (TextView) itemView.findViewById(R.id.item_titulo);
        titulo.setText(item.getTitulo());

        TextView descricao = (TextView) itemView.findViewById(R.id.item_descr);
        titulo.setText(item.getTitulo());

        TextView valor = (TextView) itemView.findViewById(R.id.valor);
        titulo.setText(item.getTitulo());

        ImageView icone = (ImageView) itemView.findViewById(R.id.item_icone);
        icone.setImageResource(item.getImg());

        return itemView;
    }
}
