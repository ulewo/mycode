package com.ulewo.enums;

public enum UserManagUrlEnums {
    CATEGORY("blog_category"), COMMENT("blog_comment"), BLOG("blog"), CHANGEPWD(
	    "changepwd"), FAVEBLOG("fave_blog"), FAVETOPIC("fave_topic"), NEWBLOG(
	    "new_blog"), NOTICE("notice"), ICON("user_icon"), INFO("user_inf"), MAIN(
	    "user_main");

    private String url;

    UserManagUrlEnums(String url) {

	this.url = url;
    }

    public String getUrl() {

	return url;
    }

    public static UserManagUrlEnums getUrl(String url) {
	return valueOf(url);
    }

}
