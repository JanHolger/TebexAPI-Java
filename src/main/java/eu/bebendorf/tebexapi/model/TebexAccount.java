package eu.bebendorf.tebexapi.model;

import com.google.gson.annotations.SerializedName;

public class TebexAccount {
    public int id;
    public String domain;
    public String name;
    public TebexCurrency currency;
    @SerializedName("online_mode")
    public boolean onlineMode;
    @SerializedName("game_type")
    public String gameType;
    @SerializedName("log_events")
    public boolean logEvents;
}
