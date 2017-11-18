package com.burguer.zap.burguer.fragments.dummy;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Promotion {

    @SerializedName("description")
    private String mKey;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("name")
    private String mName;

    @SerializedName("valid")
    private Date mValidDate;

    @SerializedName("value")
    private Double mValue;

    public String getKey() {
        return mKey;
    }

    public void setKey(String aKey) {
        mKey = aKey;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String aDescription) {
        mDescription = aDescription;
    }

    public String getName() {
        return mName;
    }

    public void setName(String aName) {
        mName = aName;
    }

    public Date getValidDate() {
        return mValidDate;
    }

    public void setValidDate(Date aValidDate) {
        mValidDate = aValidDate;
    }

    public Double getValue() {
        return mValue;
    }

    public void setValue(Double aValue) {
        mValue = aValue;
    }
}
