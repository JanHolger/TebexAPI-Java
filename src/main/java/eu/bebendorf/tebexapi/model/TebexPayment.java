package eu.bebendorf.tebexapi.model;

import com.google.gson.annotations.SerializedName;

public class TebexPayment {
    @SerializedName("txn_id")
    public String txnId;
    public long time;
    public double price;
    public String currency;
    public int status;
}
