package com.burguer.zap.burguer.repository;

import com.burguer.zap.burguer.vo.Cardapio;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by LucasOrso on 11/12/17.
 */

public class CardapioRepository {

    public List<Cardapio> getAllMenus() {
        Realm lRealm = Realm.getDefaultInstance();
        List<Cardapio> lList = new ArrayList<>();
        lRealm.executeTransaction(aRealm -> {
            List<Cardapio> lResults = aRealm.where(Cardapio.class).findAll();
            lList.addAll(lResults);
        });
        return lList;
    }

    public void saveAll(List<Cardapio> aBody) {
        Realm lRealm = Realm.getDefaultInstance();
        lRealm.executeTransaction(aRealm -> aRealm.insertOrUpdate(aBody));
    }

    public boolean delete(Cardapio aCardapio) {
        boolean lSuccess = true;
        Realm lRealm = Realm.getDefaultInstance();
        try {
            aCardapio.deleteFromRealm();
        } catch (Exception aE) {
            aE.printStackTrace();
            lSuccess = false;
        } finally {
            lRealm.close();
        }
        return lSuccess;
    }
}
