package eu.bebendorf.tebexapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TebexPurchase {
	@SerializedName("txn_id")
	public String        txnId;
	public Date          date;
	public int           quantity;
	@SerializedName("package")
	public SimplePackage pkg;

	public static class SimplePackage {
		public int    id;
		public String name;
	}
}
