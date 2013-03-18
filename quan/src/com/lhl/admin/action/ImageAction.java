package com.lhl.admin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhl.common.action.BaseAction;

public class ImageAction extends BaseAction {

	private String fileName;

	public void setFileName(String fileName) {

		this.fileName = fileName;
	}

	private List<String> list = new ArrayList<String>();

	public List<String> getList() {

		return list;
	}

	public String getRootPath() {

		try {
			HttpServletRequest request = getRequest();
			String root = request.getSession().getServletContext().getRealPath("/upload/");
			File rootFile = new File(root);
			File[] directories = rootFile.listFiles();
			for (File file : directories) {
				if (file.isHidden()) {
					continue;
				}
				if (file.isDirectory()) {
					list.add(file.getName());
				}
			}
			Collections.sort(list);
		}
		catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public String downLoadImage() {

		HttpServletRequest request = getRequest();
		String root = request.getSession().getServletContext().getRealPath("/upload/");
		String filePath = root + "/" + fileName;
		HttpServletResponse response = getResponse();
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".rar");
		OutputStream out = null;
		FileOutputStream fileOut = null;
		ZipOutputStream zos = null;
		try {
			out = response.getOutputStream();
			File inFile = new File(filePath);
			File zipFile = new File(filePath + ".zip");
			fileOut = new FileOutputStream(zipFile);
			zos = new ZipOutputStream(fileOut);
			zipFile(inFile, zos, "");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (zos != null) {
				try {
					zos.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOut != null) {
				try {
					fileOut.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		InputStream in = null;
		try {

			in = new FileInputStream(filePath + ".zip");
			int readLength = 0;

			byte[] buffer = new byte[1024];

			while (((readLength = in.read(buffer)) != -1)) {
				out.write(buffer, 0, readLength);
			}
			out.flush();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				//deleteFile(filePath + ".zip");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void zipFile(File inFile, ZipOutputStream zos, String dir) throws IOException {

		if (inFile.isDirectory()) {
			File[] files = inFile.listFiles();
			for (File file : files)
				zipFile(file, zos, inFile.getName());
		}
		else {
			String entryName = null;
			entryName = inFile.getName();
			ZipEntry entry = new ZipEntry(entryName);
			zos.putNextEntry(entry);
			InputStream is = null;
			try {
				is = new FileInputStream(inFile);
				int readLength;

				byte[] buffer = new byte[1024];

				while (((readLength = is.read(buffer, 0, 1024)) != -1)) {
					zos.write(buffer, 0, readLength);
				}
				zos.flush();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (is != null) {
					is.close();
				}
			}
		}

	}

	private void deleteFile(String filePath) throws Exception {

		File file = new File(filePath);

		//如果文件或目录不存在，则直接返回
		if (!file.exists()) {
			return;
		}

		//如果不是目录，则直接删除并返回
		if (!file.isDirectory()) {
			file.delete();
			return;
		}

		File[] files = file.listFiles();

		//如果是空目录则直接删除
		if (files != null && files.length == 0) {
			file.delete();
			return;
		}

		//遍历删除目录下的所有文件，如果存在子目录，则进行递归删除
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory() && !files[i].getAbsoluteFile().equals(file.getAbsoluteFile())) {
				deleteFile(files[i].getAbsolutePath());
			}

			files[i].delete();
		}

		//删除空目录
		file.delete();
	}
}
