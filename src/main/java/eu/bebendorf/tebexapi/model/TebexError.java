package eu.bebendorf.tebexapi.model;

import com.google.gson.annotations.SerializedName;

public class TebexError {
	@SerializedName("error_code")
	public int    errorCode;
	@SerializedName("error_message")
	public String errorMessage;
}
