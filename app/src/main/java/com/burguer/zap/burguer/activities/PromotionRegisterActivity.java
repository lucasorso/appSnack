package com.burguer.zap.burguer.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.burguer.zap.burguer.R;
import com.burguer.zap.burguer.activities.base.BaseActivity;
import com.burguer.zap.burguer.properties.APP_PROPS;
import com.burguer.zap.burguer.util.Monetary;
import com.burguer.zap.burguer.util.MonetaryWatcher;
import com.burguer.zap.burguer.vo.Promotion;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

/**
 * Created by LucasOrso on 10/5/17.
 */

public class PromotionRegisterActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PromotionRegisterActivi";
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotionregister);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        configureActionBar();
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
            lSupportActionBar.setTitle(R.string.promoiton_register);
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
        findViewById(R.id.date_valid).setOnClickListener(this);
        EditText lEditTexValue = findViewById(R.id.value);
        lEditTexValue.addTextChangedListener(new MonetaryWatcher(lEditTexValue));
        lEditTexValue.setText("0");
    }

    private void attempRegister() {
        EditText lNameView = findViewById(R.id.name);
        EditText lDescriptionView = findViewById(R.id.description);
        EditText lValueView = findViewById(R.id.value);
        EditText lDateView = findViewById(R.id.date_valid);

        lNameView.setError(null);
        lDescriptionView.setError(null);
        lValueView.setError(null);
        lDateView.setError(null);

        String lName = lNameView.getText().toString();
        String lDescription = lDescriptionView.getText().toString();
        String lValue = lValueView.getText().toString();
        String lDate = lDateView.getText().toString();
        Double lDoubleValue;
        Date lValidDate;

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

        try {
            SimpleDateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            lValidDate = lDateFormat.parse(lDate);
        } catch (Exception aE) {
            aE.printStackTrace();
            lValidDate = null;
        }

        if (TextUtils.isEmpty(lDate) || lValidDate == null) {
            lDateView.setError(getString(R.string.error_field_required));
            focusView = lDateView;
            lListResquestFocus.add(focusView);
            cancel = true;
        }

        if (cancel) {
            for (View lResquestFocus : lListResquestFocus) {
                lResquestFocus.requestFocus();
            }
        } else {
            makeRequest(lName, lDescription, lDoubleValue, lValidDate);
        }
    }

    private void makeRequest(String aName, String aDescription, Double aValue, Date lValidDate) {
        Promotion lPromotion = new Promotion();
        lPromotion.setName(aName);
        lPromotion.setDescription(aDescription);
        lPromotion.setValue(aValue);
        lPromotion.setValidDate(lValidDate);
        sendToFirebase(lPromotion);
    }

    private void sendToFirebase(Promotion aPromotion) {
        Boolean lSave = save(aPromotion);
        if (lSave) {
            Toasty.success(this, "Salvo com sucesso!", Toast.LENGTH_SHORT, true).show();
        } else {
            Toasty.error(this, "Erro ao enviar", Toast.LENGTH_SHORT, true).show();
        }
    }

    public Boolean save(Promotion aPromotion) {
        boolean lSave = true;
        if (aPromotion == null) {
            lSave = false;
        }
        if (lSave) {
            try {
                mDatabaseReference.child(APP_PROPS.FIREBASE.PROMOCAO).push().setValue(aPromotion);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
        return lSave;
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
            case R.id.date_valid:
                hideSoftKeyboard(this);
                Calendar lCalendar = Calendar.getInstance();
                int lYear = lCalendar.get(Calendar.YEAR);
                int lMonth = lCalendar.get(Calendar.MONTH);
                int lDay = lCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,(view, year, monthOfYear, dayOfMonth) -> {
                    Calendar lCalendarSelected = Calendar.getInstance();
                    lCalendarSelected.set(Calendar.YEAR, year);
                    lCalendarSelected.set(Calendar.MONTH, monthOfYear);
                    lCalendarSelected.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    EditText lValidDate = findViewById(R.id.date_valid);
                    try {
                        SimpleDateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        lValidDate.setText(lDateFormat.format(lCalendarSelected.getTime()));
                    } catch (Exception aE) {
                        aE.printStackTrace();
                        lValidDate.setText("");
                    }
                }, lYear, lMonth, lDay);
                datePickerDialog.show();
                break;
            case android.R.id.home:
                finish();
                break;
        }
    }
}
