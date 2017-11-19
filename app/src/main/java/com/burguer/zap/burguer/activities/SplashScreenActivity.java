package com.burguer.zap.burguer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.burguer.zap.burguer.R;
import com.burguer.zap.burguer.activities.base.BaseActivity;
import com.burguer.zap.burguer.repository.UsuarioRepository;
import com.burguer.zap.burguer.vo.Usuario;

/**
 * Created by LucasOrso on 10/7/17.
 */

public class SplashScreenActivity extends BaseActivity {

    private static final int SPLASH_TIME_OUT = 2000;
    private static final int TIME_TO_CHANGE_FIRST_IMAGE = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final View lLogoView = findViewById(R.id.logo_app);
        final View lBackgroundLogoView = findViewById(R.id.logo_background);

        lBackgroundLogoView.setVisibility(View.VISIBLE);
        lBackgroundLogoView.setAlpha(0.f);
        lBackgroundLogoView.setScaleX(0.f);
        lBackgroundLogoView.setScaleY(0.f);
        lBackgroundLogoView.animate().alpha(1.f).scaleX(1.f).scaleY(1.f).setDuration(1000).start();

        new Handler().postDelayed(() -> {

            lLogoView.setVisibility(View.VISIBLE);
            lLogoView.setAlpha(0.f);
            lLogoView.setScaleX(0.f);
            lLogoView.setScaleY(0.f);
            lLogoView.animate().alpha(1.f).scaleX(1.f).scaleY(1.f).setDuration(1000).start();

            new Handler().postDelayed(() -> {
                Intent lIntent;
                UsuarioRepository lRepository = new UsuarioRepository();
                Usuario lLoggedUser = lRepository.getLoggedUser();
                if (lLoggedUser != null && lLoggedUser.getLoggedUser()){
                    lIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                } else {
                    lIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                }
                startActivity(lIntent);
            }, SPLASH_TIME_OUT);
        }, TIME_TO_CHANGE_FIRST_IMAGE);

    }
}
