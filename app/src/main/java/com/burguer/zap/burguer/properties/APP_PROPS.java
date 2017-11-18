package com.burguer.zap.burguer.properties;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by LucasOrso on 10/5/17.
 */

@Retention(SOURCE)

public @interface APP_PROPS {

    @IntDef()
    @interface USER_TYPE {
        int USER = 98;
        int MANAGER = 99;
    }

    @StringDef()
    @interface BUNDLE {
        String EMAIL = "email";
    }

    @StringDef()
    @interface FIREBASE {
        String PROMOCAO = "Promotions";
    }
}
