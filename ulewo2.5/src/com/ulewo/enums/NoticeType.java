package com.ulewo.enums;

public enum NoticeType {
    ATINTOPIC("在文章中@"), RETOPIC("回复文章"), ATINBLOG("在博客中@"), REBLOG("回复博客"), ATINBLAST(
	    "在吐槽中@"), REBLAST("回复吐槽");
    private NoticeType(String desc) {
    }
}
