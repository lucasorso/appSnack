package com.burguer.zap.burguer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.burguer.zap.burguer.R;
import com.burguer.zap.burguer.activities.base.BaseActivity;
import com.burguer.zap.burguer.repository.UsuarioRepository;
import com.burguer.zap.burguer.rest.generic.BaseRetrofit;
import com.burguer.zap.burguer.rest.usuario.UserRest;
import com.burguer.zap.burguer.rest.usuario.request.LoginRequest;
import com.burguer.zap.burguer.vo.Usuario;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.security.auth.login.LoginException;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.burguer.zap.burguer.util.FieldValidation.isEmailValid;
import static com.burguer.zap.burguer.util.FieldValidation.isPasswordValid;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private Call<List<Usuario>> mCAllGetUser;
    private UserRest.Api mUserRestApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createInterface();
        createViewsListeners();
    }

    @Override
    public void onClick(View aView) {
        int lIdView = aView.getId();
        switch (lIdView) {
            case R.id.register:
                openRegisterActivity();
                break;
            case R.id.button_login:
                attemptLogin();
                break;
        }
    }

    private void openRegisterActivity() {
        Intent lIntent = new Intent(this, RegisterActivity.class);
        lIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(lIntent);
    }

    private void createCallbackListeners() {
        mCAllGetUser.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(@NonNull Call<List<Usuario>> call, @NonNull Response<List<Usuario>> response) {
                Log.e(TAG, response.message());
                showProgress(false);
                List<Usuario> lBody = response.body();
                boolean lSucess = true;
                if (lBody != null && lBody.get(0) != null) {
                    lSucess = validateWsRepose(lBody.get(0));
                } else {
                    onFailure(call, new LoginException("Verificar Retorno do WS!"));
                }
                if (lSucess) {
                    UsuarioRepository lRepository = new UsuarioRepository();
                    lRepository.logInUser(lBody.get(0));
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    onFailure(call, new LoginException("Login Inválido!"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Usuario>> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
                showProgress(false);
                String lMessage;
                if (t.getMessage() != null) {
                    lMessage = t.getMessage();
                } else {
                    lMessage = "ERROR !";
                }
                Toast.makeText(LoginActivity.this, lMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateWsRepose(Usuario aUsuario) {
        String lEmail = aUsuario.getEmail();
        EditText lEmailView = findViewById(R.id.email);
        boolean lEmailSuccess = Objects.equals(lEmail, lEmailView.getText().toString());
        String lSenha = aUsuario.getSenha();
        EditText lPasswordView = findViewById(R.id.password);
        boolean lPasswordSucess = Objects.equals(lSenha, lPasswordView.getText().toString());
        return lEmailSuccess && lPasswordSucess;
    }


    private void createViewsListeners() {
        findViewById(R.id.email).setOnClickListener(this);
        findViewById(R.id.password).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);
    }

    private void createInterface() {
        Retrofit lRetrofit = new BaseRetrofit().buildRetrofit();
        mUserRestApi = lRetrofit.create(UserRest.Api.class);
    }

    private void attemptLogin() {
        EditText lPasswordView = findViewById(R.id.password);
        EditText lEmailView = findViewById(R.id.email);

        lPasswordView.setError(null);
        lEmailView.setError(null);

        String lEmail = lEmailView.getText().toString();
        String lPassword = lPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

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
            makeRequest(lEmail, lPassword);
        }
    }

    private void makeRequest(String aEmail, String aPassword) {
        LoginRequest lRequest = new LoginRequest();
        lRequest.setEmail(aEmail);
        lRequest.setPassword(aPassword);
        doLogin(lRequest);
    }

    private void doLogin(LoginRequest aRequest) {
        showProgress(true);
        Map<String, String> lParams = BaseRetrofit.getParams(aRequest);
        mCAllGetUser = mUserRestApi.doLogin(lParams);
        createCallbackListeners();
    }

    private void showProgress(boolean aShow) {
        CircularProgressButton lView = findViewById(R.id.button_login);
        if (aShow) {
            lView.startAnimation();
        } else {
            lView.revertAnimation();
        }
    }
}

