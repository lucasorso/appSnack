package com.burguer.zap.burguer.repository;

import com.burguer.zap.burguer.vo.Usuario;

import io.realm.Realm;

/**
 * Created by LucasOrso on 11/12/17.
 */

public class UsuarioRepository {

    public void logInUser(Usuario aUsuario) {
        aUsuario.setLoggedUser(true);
        Realm lRealm = Realm.getDefaultInstance();
        try {
            lRealm.executeTransaction(aRealm -> aRealm.insertOrUpdate(aUsuario));
        } catch (Exception aE) {
            aE.printStackTrace();
        } finally {
            lRealm.close();
        }
    }

    public void logOutUser(Usuario aUsuario) {
        aUsuario.setLoggedUser(false);
        Realm lRealm = Realm.getDefaultInstance();
        try {
            lRealm.executeTransaction(aRealm -> aRealm.insertOrUpdate(aUsuario));
        } catch (Exception aE) {
            aE.printStackTrace();
        } finally {
            lRealm.close();
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
            lRealm.commitTransaction();
        } catch (Exception aE) {
            aE.printStackTrace();
            return null;
        } finally {
            lRealm.close();
        }
        return lUsuario;
    }
}
