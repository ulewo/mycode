package com.ulewo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.ulewo.enums.QueryUserType;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.AttachmentDownloadMapper;
import com.ulewo.mapper.AttachmentMapper;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.mapper.UserMapper;
import com.ulewo.model.Attachment;
import com.ulewo.model.AttachmentDownload;
import com.ulewo.model.SessionUser;
import com.ulewo.model.Topic;
import com.ulewo.model.User;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;

@Service("attachedDownloadService")
public class AttachmentDownloadServiceImpl implements AttachmentDownloadService {

	@Resource
	private AttachmentDownloadMapper<AttachmentDownload> attachmentDownloadMapper;

	@Resource
	private AttachmentMapper<Attachment> attachmentMapper;

	@Resource
	private TopicMapper<Topic> topicMapper;

	@Resource
	private UserMapper<User> userMapper;

	@Resource
	private UserService userService;

	@Override
	public void createAttachedUser(Map<String, String> map,
			SessionUser sessionUser) {
		AttachmentDownload download = new AttachmentDownload();
		download.setAttachmentId(Integer.parseInt(map.get("attachmentId")));
		download.setUserId(sessionUser.getUserId());
		attachmentDownloadMapper.insert(download);
	}

	@Override
	public AttachmentDownload queryAttachedUser(Map<String, String> param) {
		return attachmentDownloadMapper.selectBaseInfo(param);
	}

	public List<AttachmentDownload> queryAttachedUserByAttachedId(
			Map<String, String> param) throws BusinessException {
		String attachmentId = param.get("attachmentId");
		if (!StringUtils.isNumber(attachmentId)) {
			throw new BusinessException("参数错误");
		}
		int count = attachmentDownloadMapper.selectBaseInfoCount(param);
		SimplePage page = new SimplePage(0, count);
		return attachmentDownloadMapper.selectBaseInfoList(param, page);
	}

	@Override
	public void checkDownLoad(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException {
		String fileId = map.get("attachmentId");
		if (!StringUtils.isNumber(fileId)) {
			throw new BusinessException("请求参数错误");
		}
		Attachment attachedFile = attachmentMapper.selectBaseInfo(map);

		if (null == attachedFile) {
			throw new BusinessException("附件不存在");
		}
		map.put("topicId", String.valueOf(attachedFile.getTopicId()));
		// 判断当前用户是否是发布资源的人

		Topic article = topicMapper.selectBaseInfo(map);
		User articleAuthor = userService.findUser(article.getUserId()
				.toString(), QueryUserType.USERID);
		if (articleAuthor.getUserId() != sessionUser.getUserId()) {
			// 判断用户是否下载过
			map.put("userId", sessionUser.getUserId().toString());
			AttachmentDownload download = attachmentDownloadMapper
					.selectBaseInfo(map);
			if (download == null) {
				User user = userService.findUser(sessionUser.getUserId()
						.toString(), QueryUserType.USERID);
				if (user.getMark() < attachedFile.getDownloadMark()) {
					throw new BusinessException("你当前的积分是" + user.getMark()
							+ ",积分不够");
				}
			}
		}
	}

	public void downloadFile(Map<String, String> map, SessionUser sessionUser,
			HttpServletResponse response, HttpSession session)
			throws BusinessException, IOException {
		InputStream in = null;
		OutputStream out = null;
		File file = null;
		String attachmentId = map.get("attachmentId");
		if (!StringUtils.isNumber(attachmentId)) {
			throw new BusinessException("请求参数错误");
		}
		int attachmentId_int = Integer.parseInt(attachmentId);
		Attachment attachedFile = attachmentMapper.selectBaseInfo(map);

		if (null == attachedFile) {
			throw new BusinessException("附件不存在");
		}
		map.put("topicId", String.valueOf(attachedFile.getTopicId()));

		Topic article = topicMapper.selectBaseInfo(map);
		User articleAuthor = userService.findUser(article.getUserId()
				.toString(), QueryUserType.USERID);
		// 判断当前用户是否是发布资源的人
		if (articleAuthor.getUserId() != sessionUser.getUserId()) {
			map.put("userId", sessionUser.getUserId().toString());
			AttachmentDownload download = attachmentDownloadMapper
					.selectBaseInfo(map);
			// 判断用户是否下载过
			if (download == null) {
				User user = userMapper.selectUserByUserId(sessionUser
						.getUserId());
				if (user.getMark() < attachedFile.getDownloadMark()) {
					throw new BusinessException("你当前的积分是" + user.getMark()
							+ ",积分不够");
				}
				// 非当前用户，没有下载过，积分也够
				// 扣除积分
				user.setMark(user.getMark() - attachedFile.getDownloadMark());
				userService.updateSelective(user);
				// 给发布信息的人加积分
				User author = userMapper
						.selectUserByUserId(article.getUserId());
				author.setMark(author.getMark()
						+ attachedFile.getDownloadMark());
				userService.updateSelective(author);
				// 记录已经下载过
				AttachmentDownload attachedUser2 = new AttachmentDownload();
				attachedUser2.setAttachmentId(attachmentId_int);
				attachedUser2.setUserId(sessionUser.getUserId());
				attachmentDownloadMapper.insert(attachedUser2);
			}
		} else {// 如果是发布资源者，记录下载情况
			AttachmentDownload download = attachmentDownloadMapper
					.selectBaseInfo(map);
			if (download == null) {
				AttachmentDownload attachedUser2 = new AttachmentDownload();
				attachedUser2.setAttachmentId(attachmentId_int);
				attachedUser2.setUserId(sessionUser.getUserId());
				attachmentDownloadMapper.insert(attachedUser2);
			}
		}
		// 更新下载量
		attachedFile.setDownloadCount(attachedFile.getDownloadCount() + 1);
		attachmentMapper.updateSelective(attachedFile);
		// 开始下载
		String realPath = session.getServletContext().getRealPath("/")
				+ "upload/";
		String filePath = realPath + attachedFile.getFileUrl();
		file = new File(filePath);
		in = new FileInputStream(file);
		out = response.getOutputStream();
		response.setContentType("application/octet-stream; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ URLEncoder.encode(attachedFile.getFileName(), "UTF-8"));
		byte[] byteData = new byte[1024 * 5];
		int len = 0;
		while ((len = in.read(byteData)) != -1) {
			out.write(byteData, 0, len); // write
		}
		out.flush();
	}
}
