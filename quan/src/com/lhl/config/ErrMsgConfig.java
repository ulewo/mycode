package com.lhl.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/** 
 * @Title:
 * @Description: 错误码及描述配置管理
 * @author glping
 * @date 2011-11-23
 * @version V1.0
*/
public class ErrMsgConfig {
	private static String CONFIG_FILE = "config/errorMessage";

	private static ResourceBundle bundle;

	static {
		try {
			bundle = ResourceBundle.getBundle(CONFIG_FILE);
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
		}
	}

	public static String getErrMsg(int code) {

		String errMsg = "";
		try {
			errMsg = bundle.getString(String.valueOf(code));
			errMsg = new String(errMsg.getBytes("ISO-8859-1"), "UTF-8");
		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return errMsg;
	}
}
