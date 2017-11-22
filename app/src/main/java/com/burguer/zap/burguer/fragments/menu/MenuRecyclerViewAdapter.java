package com.burguer.zap.burguer.fragments.menu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.burguer.zap.burguer.R;
import com.burguer.zap.burguer.vo.Cardapio;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Cardapio} and makes a call to the
 * specified {@link MenuFragment.OnListFragmentInteractionListener}.
 * DONE: Replace the implementation with code for your data type.
 */
public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.ViewHolder> {

    private final List<Cardapio> mValues;

    MenuRecyclerViewAdapter(List<Cardapio> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Cardapio lItem = holder.mItem;
        holder.mName.setText(lItem.getNomePrato());
        holder.mContentView.setText(lItem.getDescricao());
        holder.mValue.setText(lItem.getValor());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView mName;
        private TextView mContentView;
        private Cardapio mItem;
        private TextView mValue;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = view.findViewById(R.id.item_titulo);
            mContentView = view.findViewById(R.id.item_descr);
            mValue = view.findViewById(R.id.valor);

            
        }

        @Override
        public String toString() {
            return super.toString() + mName + " '" + mContentView.getText() + "'";
        }
    }
}
