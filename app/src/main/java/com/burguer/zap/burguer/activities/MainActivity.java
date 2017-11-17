package com.burguer.zap.burguer.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.MenuItem;

import com.burguer.zap.burguer.R;
import com.burguer.zap.burguer.activities.base.BaseActivity;
import com.burguer.zap.burguer.fragments.MenuFragment;
import com.burguer.zap.burguer.fragments.MenuRequestFragment;
import com.burguer.zap.burguer.fragments.PromotionFragment;
import com.burguer.zap.burguer.fragments.dummy.DummyContent;
import com.burguer.zap.burguer.fragments.dummy.Menu;
import com.burguer.zap.burguer.fragments.dummy.MenuRequest;
import com.burguer.zap.burguer.properties.APP_PROPS;
import com.burguer.zap.burguer.repository.UsuarioRepository;
import com.burguer.zap.burguer.vo.Usuario;

public class MainActivity extends BaseActivity
        implements
        MenuFragment.OnListFragmentInteractionListener,
        MenuRequestFragment.OnListFragmentInteractionListener,
        PromotionFragment.OnListFragmentInteractionListener {

    private BottomNavigationView mBottomNavigationView;
    private Usuario mUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsuario = new UsuarioRepository().getLoggedUser();

        setReferences();
        changeFragment(0);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        if (mUsuario.getIdApp() == APP_PROPS.USER_TYPE.MANAGER) {
            MenuItem lAdd = menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.ic_add), getResources().getString(R.string.add_item)));
            lAdd.setOnMenuItemClickListener(aMenuItem -> {
                startActivity(new Intent(this, AdminCard.class));
                return false;
            });
        }
        MenuItem lExit = menu.add(0, 2, 2, menuIconWithText(getResources().getDrawable(R.drawable.ic_exit_to_app), getResources().getString(R.string.exit)));
        lExit.setOnMenuItemClickListener(aMenuItem -> {
            logOutUser();
            return false;
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void logOutUser() {
        new UsuarioRepository().logOutUser(mUsuario);
        Intent lLogin = new Intent(this, LoginActivity.class);
        lLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(lLogin);
        finish();
    }

    private CharSequence menuIconWithText(Drawable r, String title) {
        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                startActivity(new Intent());
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setReferences() {
        mBottomNavigationView = findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment(0);
                    return true;
                case R.id.navigation_dashboard:
                    changeFragment(1);
                    return true;
                case R.id.navigation_notifications:
                    changeFragment(2);
                    return true;
            }
            return false;
        });
    }

    private void changeFragment(int position) {
        Fragment lNewFragment;
        if (position == 0) {
            lNewFragment = MenuFragment.newInstance(mUsuario);
        } else if (position % 2 != 0) {
            lNewFragment = MenuRequestFragment.newInstance(mUsuario);
        } else {
            lNewFragment = PromotionFragment.newInstance(mUsuario);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, lNewFragment).commit();
    }

    @Override
    public void onListFragmentInteraction(Menu.DummyItem item) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onListFragmentInteraction(MenuRequest.DummyItem item) {

    }
}
