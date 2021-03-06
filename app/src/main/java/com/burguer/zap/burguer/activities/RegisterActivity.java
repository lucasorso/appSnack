package com.burguer.zap.burguer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.burguer.zap.burguer.R;
import com.burguer.zap.burguer.activities.base.BaseActivity;
import com.burguer.zap.burguer.properties.APP_PROPS;
import com.burguer.zap.burguer.rest.generic.BaseRetrofit;
import com.burguer.zap.burguer.rest.usuario.UserRest;
import com.burguer.zap.burguer.rest.usuario.request.RegisterRequest;
import com.burguer.zap.burguer.vo.Usuario;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.burguer.zap.burguer.util.FieldValidation.isEmailValid;
import static com.burguer.zap.burguer.util.FieldValidation.isPasswordValid;

/**
 * Created by LucasOrso on 10/5/17.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "RegisterActivity";

    private Call<Usuario> mCAllGetUsers;
    private UserRest.Api mUserRestApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        configureActionBar();
        createInterface();
        createViewsListeners();
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
            lSupportActionBar.setTitle(R.string.register);
        }
    }

    private void createInterface() {
        Retrofit lRetrofit = new BaseRetrofit().buildRetrofit();
        mUserRestApi = lRetrofit.create(UserRest.Api.class);
    }

    private void createViewsListeners() {
        findViewById(R.id.button_register).setOnClickListener(this);
    }

    private void attempRegister() {
        EditText lNameView = findViewById(R.id.name);
        EditText lEmailView = findViewById(R.id.email);
        EditText lPasswordView = findViewById(R.id.password);

        lNameView.setError(null);
        lPasswordView.setError(null);
        lEmailView.setError(null);

        String lName = lNameView.getText().toString();
        String lEmail = lEmailView.getText().toString();
        String lPassword = lPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(lName)) {
            lNameView.setError(getString(R.string.error_field_required));
            focusView = lNameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(lPassword)) {
            lPasswordView.setError(getString(R.string.error_field_required));
            focusView = lPasswordView;
            cancel = true;
        } else if (!isPasswordValid(lPassword)) {
            lPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = lPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(lEmail)) {
            lEmailView.setError(getString(R.string.error_field_required));
            focusView = lEmailView;
            cancel = true;
        } else if (!isEmailValid(lEmail)) {
            lEmailView.setError(getString(R.string.error_invalid_email));
            focusView = lEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            makeRequest(lName, lEmail, lPassword);
        }
    }

    private void makeRequest(String aName, String aEmail, String aPassword) {
        RegisterRequest lRequest = new RegisterRequest();
        lRequest.setNome(aName);
        lRequest.setEmail(aEmail);
        lRequest.setPassword(aPassword);
        doRegister(lRequest);
    }

    private void doRegister(RegisterRequest aRequest) {
        showProgress(true);
        mCAllGetUsers = mUserRestApi.registerUser(aRequest);
        createCallbackListeners();
    }

    private void createCallbackListeners() {
        mCAllGetUsers.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(@NonNull Call<Usuario> call, @NonNull Response<Usuario> response) {
                Log.e(TAG, response.message());
                showProgress(false);
                Toasty.success(RegisterActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT, true).show();
                new Handler().postDelayed(() -> completeRegister(), 1000);
            }

            @Override
            public void onFailure(@NonNull Call<Usuario> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
                showProgress(false);
                Toasty.error(RegisterActivity.this, "Erro realizar cadastro!", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void completeRegister() {
        Bundle lBundle = new Bundle();
        EditText lEmailView = findViewById(R.id.email);
        lBundle.putString(APP_PROPS.BUNDLE.EMAIL, lEmailView.getText().toString());
        Intent lLogin = new Intent(RegisterActivity.this, LoginActivity.class);
        lLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        lLogin.putExtras(lBundle);
        startActivity(lLogin);
    }

    private void showProgress(boolean aShow) {
        CircularProgressButton lView = findViewById(R.id.button_register);
        if (aShow) {
            lView.startAnimation();
        } else {
            lView.revertAnimation();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View aView) {
        int lIdView = aView.getId();
        switch (lIdView) {
            case R.id.button_register:
                attempRegister();
                break;
            case android.R.id.home:
                finish();
                break;
        }
    }
}
