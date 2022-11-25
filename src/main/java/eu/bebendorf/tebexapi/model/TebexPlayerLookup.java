package eu.bebendorf.tebexapi.model;

import com.google.gson.JsonElement;

public class TebexPlayerLookup {
	public  TebexPlayerInfo player;
	public  int             banCount;
	public  int             chargebackRate;
	public  TebexPayment[]  payments;
	private JsonElement     purchaseTotals;
}
