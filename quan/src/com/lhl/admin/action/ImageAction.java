package com.lhl.admin.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lhl.common.action.BaseAction;
import com.lhl.entity.ImageFile;

public class ImageAction extends BaseAction {

	private List<ImageFile> list = new ArrayList<ImageFile>();

	public List<ImageFile> getList() {
		return list;
	}

	public String getRootPath() {
		try {
			HttpServletRequest request = getRequest();
			String root = request.getSession().getServletContext()
					.getRealPath("/upload/");
			File rootFile = new File(root);
			File[] directories = rootFile.listFiles();
			for (File file : directories) {
				if (file.isHidden()) {
					continue;
				}
				if (file.isDirectory()) {
					ImageFile imageFile = new ImageFile();
					imageFile.setDirectory(true);
					imageFile.setFileName(file.getName());
					imageFile.setFilePath(file.getPath());
					list.add(imageFile);
				}

			}
		} catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}
}
