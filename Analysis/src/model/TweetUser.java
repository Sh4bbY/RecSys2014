package model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class TweetUser implements Serializable
{
	private static final long	serialVersionUID	= 1L;
	
	private Boolean follow_request_sent;
	private Boolean profile_use_background_image;
	private String id;
	private Boolean verified;
	private String profile_text_color;
	private String profile_image_url_https;
	private String profile_sidebar_fill_color;
	private Boolean is_translator;
	private Boolean geo_enabled;
	
	private Integer followers_count;
	
	@SerializedName("protected")
	private Boolean isProtected;
	
	private String location;
	private Boolean default_profile_image;
	private String id_str;
	private String lang;
	private String utc_offset;
	private Integer statuses_count;
	private String description;
	private Integer friends_count;
	private String profile_link_color;
	private String profile_image_url;
	private Boolean notifications;
	private String profile_background_image_url_https;
	private String profile_background_color;
	private String profile_background_image_url;
	private String name;
	private String is_translation_enabled;
	private String profile_background_tile;
	private Integer favourites_count;
	private String screen_name;
	private String url;
	private Boolean contributors_enabled;
	private String time_zone;
	private String profile_sidebar_border_color;
	private Boolean default_profile;
	private Boolean following;
	private Integer listed_count;

	/* disregarded objects
	 * 
	 *		entities
	 */
	
	@Override
	public String toString()
	{
		return screen_name;
	}

	public int getFriendsCount()
	{
		return friends_count;
	}

	public int getFollowersCount()
	{
		return followers_count;
	}
	
	public String getId()
	{
		return id;
	}
}
