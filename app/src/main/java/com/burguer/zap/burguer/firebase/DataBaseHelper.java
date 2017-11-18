package com.burguer.zap.burguer.firebase;

import com.burguer.zap.burguer.fragments.dummy.Promotion;
import com.burguer.zap.burguer.properties.APP_PROPS;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LucasOrso on 11/18/17.
 */

public class DataBaseHelper {

    private DatabaseReference mDatabaseReference;
    private List<Promotion> mPromotionsList = new ArrayList<>();


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

    public List<Promotion> retrieve() {
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return mPromotionsList;
    }

    private void fetchData(DataSnapshot aDataSnapshot) {
        mPromotionsList.clear();
        for (DataSnapshot lSnapshot : aDataSnapshot.getChildren()) {
            Promotion lPromotion = new Gson().fromJson(lSnapshot.toString(), Promotion.class);
            mPromotionsList.add(lPromotion);
        }
    }
}


