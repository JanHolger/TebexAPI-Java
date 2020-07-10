package eu.bebendorf.tebexapi.model;

import com.google.gson.annotations.SerializedName;

public class TebexCurrency {
    @SerializedName("iso_4217")
    public String iso;
    public String symbol;
}
