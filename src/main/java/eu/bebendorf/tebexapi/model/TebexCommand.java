package eu.bebendorf.tebexapi.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class TebexCommand {
	public  int         id;
	public  String      command;
	@SerializedName("payment")
	public  int         paymentId;
	@SerializedName("package")
	public  int         packageId;
	private JsonObject  conditions;
	public  TebexPlayer player;
}
