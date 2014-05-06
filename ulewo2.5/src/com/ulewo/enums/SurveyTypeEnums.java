package com.ulewo.enums;

/**
 * 投票类型
 * 
 * @author yougoupublic
 * 
 */
public enum SurveyTypeEnums {
    SINGLE("S"), MULTI("M");

    private String type;

    SurveyTypeEnums(String type) {

	this.type = type;
    }

    public String getValue() {

	return type;
    }
}
