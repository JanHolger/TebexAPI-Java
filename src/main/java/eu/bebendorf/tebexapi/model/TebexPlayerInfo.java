package eu.bebendorf.tebexapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TebexPlayerInfo {
	public String id;
	@SerializedName("created_at")
	public Date   createdAt;
	@SerializedName("updated_at")
	public Date   updatedAt;
	@SerializedName("cache_expire")
	public Date   cacheExpire;
	public String username;
	public String meta;
	@SerializedName("plugin_username_id")
	public int    pluginUsernameId;
}
