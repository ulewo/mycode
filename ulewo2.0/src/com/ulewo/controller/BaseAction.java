package com.ulewo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2013-10-31 下午3:40:09
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class BaseAction {
	public Map builderParams(HttpServletRequest req, Model model) {
		Map params = req.getParameterMap();
		if (params != null && params.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
				java.util.Map.Entry p = (java.util.Map.Entry) iterator.next();
				if (p.getValue() != null && !StringUtils.isEmpty(p.getValue().toString())) {
					String values[] = (String[]) p.getValue();
					String match = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
					if (values[0].matches(match))
						try {
							p.setValue(sdf.parse(values[0]));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					else if (((String) p.getKey()).equals("queryCondition")
							&& model.asMap().containsKey("queryCondition"))
						p.setValue(model.asMap().get("queryCondition"));
					else
						p.setValue(values[0]);
				}
			}

		}
		return params;
	}
}
