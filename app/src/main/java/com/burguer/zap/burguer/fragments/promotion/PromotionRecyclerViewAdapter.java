package com.burguer.zap.burguer.fragments.promotion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.burguer.zap.burguer.R;
import com.burguer.zap.burguer.fragments.promotion.PromotionFragment.OnListFragmentInteractionListener;
import com.burguer.zap.burguer.vo.Promotion;
import com.burguer.zap.burguer.util.Monetary;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Promotion} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * DONE: Replace the implementation with code for your data type.
 */
public class PromotionRecyclerViewAdapter extends RecyclerView.Adapter<PromotionRecyclerViewAdapter.ViewHolder> {

    private final List<Promotion> mValues;
    private final OnListFragmentInteractionListener mListener;
    private PromotionAdapterListener mAdapterListener;

    public PromotionRecyclerViewAdapter(List<Promotion> items, OnListFragmentInteractionListener listener, PromotionFragment aPromotionFragment) {
        mValues = items;
        mListener = listener;
        mAdapterListener = aPromotionFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_promotion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Promotion lItem = holder.mItem;
        holder.mName.setText(lItem.getName());
        holder.mDescription.setText(lItem.getDescription());
        try {
            SimpleDateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            holder.mValid.setText(lDateFormat.format(lItem.getValidDate()));
        } catch (Exception aE) {
            aE.printStackTrace();
            holder.mValid.setText("");
        }
        holder.mRating.setOnClickListener(aView -> {
            float lRating = holder.mRating.getRating();
            if (lRating == 1.0f) {
                holder.mRating.setRating(0.0f);
            } else {
                holder.mRating.setRating(1.0f);
            }
        });
        holder.mValue.setText(Monetary.sDoubleToString(lItem.getValue()));
        holder.mView.setOnLongClickListener(aView -> {
            mAdapterListener.onLongCLickListener(lItem);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public interface PromotionAdapterListener {
        void onLongCLickListener(Promotion aPromotion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView mName;
        private TextView mDescription;
        private Promotion mItem;
        private TextView mValid;
        private RatingBar mRating;
        private TextView mValue;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            mName = view.findViewById(R.id.name);
            mDescription = view.findViewById(R.id.description);
            mValid = view.findViewById(R.id.valid);
            mRating = view.findViewById(R.id.rating);
            mValue = view.findViewById(R.id.value);
        }

        @Override
        public String toString() {
            return super.toString() + mName + " '" + mDescription.getText() + "'";
        }
    }
}
