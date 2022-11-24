package eu.bebendorf.tebexapi.model;

import com.google.gson.JsonObject;

public class TebexPlayerLookup {
	public  TebexPlayerInfo player;
	public  int             banCount;
	public  int             chargebackRate;
	public  TebexPayment[]  payments;
	private JsonObject      purchaseTotals;
}
