package com.ulewo.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.AttachmentDownload;
import com.ulewo.model.SessionUser;

public interface AttachmentDownloadService {
	/**
	 * 记录下载人信息
	 * 
	 * @param attachedUser
	 * @param sessionUser
	 *            TODO
	 */
	public void createAttachedUser(Map<String, String> attachedUser,
			SessionUser sessionUser);

	/**
	 * 查询附件下载人信息
	 * 
	 * @param param
	 * @return
	 */
	public AttachmentDownload queryAttachedUser(Map<String, String> param);

	/**
	 * 查询附件下载的人
	 * 
	 * @param param
	 * @return
	 * @throws BusinessException
	 *             TODO
	 */
	public List<AttachmentDownload> queryAttachedUserByAttachedId(
			Map<String, String> param) throws BusinessException;

	/**
	 * 校验下载相关信息
	 * 
	 * @param map
	 * @param sessionUser
	 */
	public void checkDownLoad(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException;

	public void downloadFile(Map<String, String> map, SessionUser sessionUser,
			HttpServletResponse response, HttpSession session)
			throws BusinessException, IOException;
}
