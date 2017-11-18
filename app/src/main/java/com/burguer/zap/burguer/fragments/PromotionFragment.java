package com.burguer.zap.burguer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burguer.zap.burguer.R;
import com.burguer.zap.burguer.fragments.base.BaseFragment;
import com.burguer.zap.burguer.fragments.dummy.Promotion;
import com.burguer.zap.burguer.properties.APP_PROPS;
import com.burguer.zap.burguer.vo.Usuario;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PromotionFragment extends BaseFragment implements PromotionRecyclerViewAdapter.PromotionAdapterListener {

    private static final String TAG = "PromotionFragment";
    private static final String USER = "user";

    private Usuario mUsuario;
    private OnListFragmentInteractionListener mListener;
    private DatabaseReference mDatabaseReference;
    private List<Promotion> mList;
    private PromotionRecyclerViewAdapter mAdapter;

    public PromotionFragment() {/*Nothing*/}

    public static PromotionFragment newInstance(Usuario aUsuario) {
        PromotionFragment fragment = new PromotionFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER, aUsuario);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUsuario = (Usuario) getArguments().getSerializable(USER);
        }
        mList = new ArrayList<>();
        mAdapter = new PromotionRecyclerViewAdapter(mList, mListener, this);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(APP_PROPS.FIREBASE.PROMOCAO);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(mAdapter);
        }
        dataBaseListener();
        return view;
    }

    private void dataBaseListener() {
        mDatabaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Promotion lPromotion = dataSnapshot.getValue(Promotion.class);
                if (lPromotion != null) {
                    lPromotion.setKey(dataSnapshot.getKey());
                    mList.add(0, lPromotion);
                    mAdapter.notifyDataSetChanged();
                }
                Log.i(TAG, "onChildAdded: ");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, "onChildChanged: ");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String lKey = dataSnapshot.getKey();
                for (Promotion lPromotion : mList) {
                    if (Objects.equals(lKey, lPromotion.getKey())) {
                        mList.remove(lPromotion);
                        mAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                Log.i(TAG, "onChildRemoved: ");

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, "onChildMoved: ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "onCancelled: ");
            }
        });
    }

    private void fetchData(DataSnapshot aDataSnapshot) {
        mList.clear();
        for (DataSnapshot lSnapshot : aDataSnapshot.getChildren()) {
            mList.add(lSnapshot.getValue(Promotion.class));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLongCLickListener(Promotion aPromotion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getString(R.string.atention));
        builder.setMessage(String.format(getActivity().getString(R.string.remover), aPromotion.getName()));
        builder.setPositiveButton(getActivity().getString(R.string.ok), (dialog, which) -> {
            RemoveFromFirebase(aPromotion);
        });
        builder.show();
    }

    private void RemoveFromFirebase(Promotion aPromotion) {
        mDatabaseReference.child(aPromotion.getKey()).removeValue();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Promotion aPromotion);
    }
}
