package com.burguer.zap.burguer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.burguer.zap.burguer.R;
import com.burguer.zap.burguer.activities.base.BaseActivity;
import com.burguer.zap.burguer.rest.cardapio.CardapioRest;
import com.burguer.zap.burguer.rest.cardapio.request.CardapioRequest;
import com.burguer.zap.burguer.rest.generic.BaseRetrofit;
import com.burguer.zap.burguer.rest.generic.GenericResponse;
import com.burguer.zap.burguer.util.Monetary;
import com.burguer.zap.burguer.util.MonetaryWatcher;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by LucasOrso on 10/5/17.
 */

public class MenuRegisterActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MenuRegisterActivity";
    
    private CardapioRest.Api mCardapioResApi;
    private Call<GenericResponse> mCAllInsertCardapio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuregister);
        configureActionBar();
        createViewsListeners();
        createInterface();
    }

    private void createInterface() {
        Retrofit lRetrofit = new BaseRetrofit().buildRetrofit();
        mCardapioResApi = lRetrofit.create(CardapioRest.Api.class);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        findViewById(R.id.name).requestFocus();
    }

    private void configureActionBar() {
        ActionBar lSupportActionBar = getSupportActionBar();
        if (lSupportActionBar != null) {
            lSupportActionBar.setDisplayHomeAsUpEnabled(true);
            lSupportActionBar.setTitle(R.string.menu_register);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem lAdicionar = menu.add(0, 0, 0, "Adicionar");
        lAdicionar.setIcon(R.drawable.ic_check).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        lAdicionar.setOnMenuItemClickListener(aMenuItem -> {
            attempRegister();
            return false;
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void createViewsListeners() {
        EditText lEditTexValue = findViewById(R.id.value);
        lEditTexValue.addTextChangedListener(new MonetaryWatcher(lEditTexValue));
        lEditTexValue.setText("0");
    }

    private void attempRegister() {
        EditText lNameView = findViewById(R.id.name);
        EditText lDescriptionView = findViewById(R.id.description);
        EditText lValueView = findViewById(R.id.value);

        lNameView.setError(null);
        lDescriptionView.setError(null);
        lValueView.setError(null);

        String lName = lNameView.getText().toString();
        String lDescription = lDescriptionView.getText().toString();
        String lValue = lValueView.getText().toString();
        Double lDoubleValue;

        boolean cancel = false;
        View focusView;
        List<View> lListResquestFocus = new ArrayList<>();

        if (TextUtils.isEmpty(lName)) {
            lNameView.setError(getString(R.string.error_field_required));
            focusView = lNameView;
            lListResquestFocus.add(focusView);
            cancel = true;
        }

        if (TextUtils.isEmpty(lDescription)) {
            lDescriptionView.setError(getString(R.string.error_field_required));
            focusView = lDescriptionView;
            lListResquestFocus.add(focusView);
            cancel = true;
        }

        try {
            lDoubleValue = Monetary.sStringWithCurrencyToDouble(lValue);
        } catch (NumberFormatException aE) {
            aE.printStackTrace();
            lDoubleValue = null;
            cancel = true;
        }

        if (lDoubleValue == null) {
            lValueView.setError(getString(R.string.error_field_required));
            focusView = lValueView;
            lListResquestFocus.add(focusView);
            cancel = true;
        }

        if (cancel) {
            for (View lResquestFocus : lListResquestFocus) {
                lResquestFocus.requestFocus();
            }
        } else {
            makeRequest(lName, lDescription, lDoubleValue);
        }
    }

    private void makeRequest(String aName, String aDescription, Double aValue) {
        CardapioRequest lRequest = new CardapioRequest();
        lRequest.setNomePrato(aName);
        lRequest.setDescricao(aDescription);
        lRequest.setValor(String.valueOf(aValue));
        sendCardapio(lRequest);
    }

    private void sendCardapio(CardapioRequest aRequest) {
        mCAllInsertCardapio = mCardapioResApi.insertCardapio(aRequest);
        createCallbackListeners();
    }

    private void createCallbackListeners() {
        mCAllInsertCardapio.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenericResponse> call, @NonNull Response<GenericResponse> response) {
                Log.e(TAG, response.message());
                GenericResponse lBody = response.body();
                if (lBody != null) {
                    if (lBody.getSuccess()) {
                        Toasty.success(MenuRegisterActivity.this, "Cardápio inserido com sucesso!", Toast.LENGTH_SHORT, true).show();
                        clearFields();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GenericResponse> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
                
                String lMessage;
                if (t.getMessage() != null) {
                    lMessage = t.getMessage();
                } else {
                    lMessage = "ERROR !";
                }
                Toasty.error(MenuRegisterActivity.this, lMessage, Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void clearFields() {
        EditText lNameView = findViewById(R.id.name);
        EditText lDescriptionView = findViewById(R.id.description);
        EditText lValueView = findViewById(R.id.value);
        lNameView.setText("");
        lDescriptionView.setText("");
        lValueView.setText("");
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(Activity.RESULT_OK);
        finish();
        return true;
    }

    @Override
    public void onClick(View aView) {
        int lIdView = aView.getId();
        switch (lIdView) {
            case android.R.id.home:
                finish();
                break;
        }
    }
}
