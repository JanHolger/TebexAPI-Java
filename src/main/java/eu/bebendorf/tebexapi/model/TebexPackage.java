package eu.bebendorf.tebexapi.model;


import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

public class TebexPackage {
	public int           id;
	public String        name;
	public boolean       image;
	public double        price;
	@SerializedName("expiry_length")
	public int           expiryLength;
	@SerializedName("expiry_period")
	public String        expiryPeriod;
	public String        type;
	public TebexCategory category;
	@SerializedName("global_limit")
	public int           globalLimit;
	@SerializedName("global_limit_period")
	public String        globalLimitPeriod;
	@SerializedName("user_limit")
	public int           userLimit;
	@SerializedName("user_limit_period")
	public String        userLimitPeriod;
	public TebexServer[] servers;
	@SerializedName("required_packages")
	public JsonArray     requiredPackages;
	@SerializedName("require_any")
	public boolean       requireAny;
	@SerializedName("create_giftcard")
	public boolean       createGiftcard;
	public boolean       showUntil;
	@SerializedName("gui_item")
	public String        guiItem;
	public boolean       disabled;
	@SerializedName("disable_quantity")
	public boolean       disableQuantity;
	@SerializedName("custom_price")
	public boolean       customPrice;
	@SerializedName("choose_server")
	public boolean       chooseServer;
	@SerializedName("limit_expires")
	public boolean       limitExpires;
	public boolean       inheritCommands;
	public boolean       variableGiftcard;

}