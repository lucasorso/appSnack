package com.burguer.zap.burguer.fragments.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.burguer.zap.burguer.R;
import com.burguer.zap.burguer.fragments.base.BaseFragment;
import com.burguer.zap.burguer.rest.cardapio.CardapioRest;
import com.burguer.zap.burguer.rest.generic.BaseRetrofit;
import com.burguer.zap.burguer.vo.Cardapio;
import com.burguer.zap.burguer.vo.Menu.DummyItem;
import com.burguer.zap.burguer.vo.Usuario;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MenuFragment extends BaseFragment {

    private static final String USER = "user";
    private Usuario mUsuario;
    private OnListFragmentInteractionListener mListener;
    private List<Cardapio> mList;
    private Call<List<Cardapio>> mCAllCardapio;
    private MenuRecyclerViewAdapter mAdapter;

    public MenuFragment() {
    }

    public static MenuFragment newInstance(Usuario aUsuario) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putSerializable("user", aUsuario);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsuario = (Usuario) getArguments().getSerializable(USER);
        }
        createInterface();
        createCallbackListeners();
        mList = new ArrayList<>();
        mAdapter = new MenuRecyclerViewAdapter(mList, mListener);
    }

    private void createCallbackListeners() {
        mCAllCardapio.enqueue(new Callback<List<Cardapio>>() {
            @Override
            public void onResponse(@NonNull Call<List<Cardapio>> call, @NonNull Response<List<Cardapio>> response) {
                List<Cardapio> lBody = response.body();
                if (lBody != null) {
                    if (lBody.size() > 0) {
                        mList.clear();
                        mList.addAll(lBody);
                        mAdapter.notifyDataSetChanged();
                        Toasty.success(getActivity(), "Lista Atualizada!", Toast.LENGTH_SHORT, true).show();
                    } else {
                        Toasty.error(getActivity(), "Nenhum cardápio encontrado!", Toast.LENGTH_LONG, true).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Cardapio>> call, @NonNull Throwable t) {
                String lMessage;
                if (t.getMessage() != null) {
                    lMessage = t.getMessage();
                } else {
                    lMessage = "ERROR !";
                }
                Toasty.error(getActivity(), lMessage, Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void createInterface() {
        CardapioRest.Api lCardapioApi = getApi();
        mCAllCardapio = lCardapioApi.getListOfCardapio();
    }

    private CardapioRest.Api getApi() {
        Retrofit lRetrofit = new BaseRetrofit().buildRetrofit();
        return lRetrofit.create(CardapioRest.Api.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item);
    }
}
