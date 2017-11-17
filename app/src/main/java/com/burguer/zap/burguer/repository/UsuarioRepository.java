package com.burguer.zap.burguer.repository;

import com.burguer.zap.burguer.vo.Usuario;

import io.realm.Realm;

/**
 * Created by LucasOrso on 11/12/17.
 */

public class UsuarioRepository {

    public void logInUser(Usuario aUsuario) {
        aUsuario.setLoggedUser(true);
        try {
            Realm lRealm = Realm.getDefaultInstance();
            lRealm.executeTransaction(aRealm -> aRealm.insertOrUpdate(aUsuario));
        } catch (Exception aE) {
            aE.printStackTrace();
        }
    }

    public void logOutUser(Usuario aUsuario) {
        aUsuario.setLoggedUser(false);
        try {
            Realm lRealm = Realm.getDefaultInstance();
            lRealm.executeTransaction(aRealm -> aRealm.insertOrUpdate(aUsuario));
        } catch (Exception aE) {
            aE.printStackTrace();
        }
    }

    public Usuario getLoggedUser() {
        Realm lRealm = Realm.getDefaultInstance();
        Usuario lUsuario = null;
        try {
            lRealm.beginTransaction();
            Usuario lResult = lRealm.where(Usuario.class).findFirst();
            if (lResult != null) {
                lUsuario = lRealm.copyFromRealm(lResult);
            }
        } catch (Exception aE) {
            aE.printStackTrace();
            return null;
        } finally {
            lRealm.close();
        }
        return lUsuario;
    }
}
