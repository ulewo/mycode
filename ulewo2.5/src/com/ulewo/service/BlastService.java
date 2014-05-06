package com.ulewo.service;

import java.util.List;
import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.Blast;
import com.ulewo.model.SessionUser;
import com.ulewo.util.UlewoPaginationResult;

/**
 * 
 * 吐槽
 * 
 * @author username
 * @date 2014-2-10 下午2:17:19
 * @version 0.1.0
 * @copyright ulewo.com
 */
public interface BlastService {

    /**
     * 添加说说
     * 
     * @param map
     * @param sessionUser
     *            TODO
     * @return TODO
     * @throws Exception
     */
    public Blast addBlast(Map<String, String> map, SessionUser sessionUser)
	    throws BusinessException;

    /**
     * 查询最新的吐槽
     * 
     * @return
     */
    public List<Blast> queryLatestBlast();

    /**
     * 查询所有吐槽
     * 
     * @return
     */
    public UlewoPaginationResult<Blast> queryAllBlast(Map<String, String> map);

    /**
     * 查询用户所有吐槽
     * 
     * @return
     * @throws BusinessException TODO
     */
    public UlewoPaginationResult<Blast> queryBlastByUserId(
	    Map<String, String> map) throws BusinessException;

    /**
     * 删除吐槽
     * 
     * @param map
     * @param sessionUser
     */
    public void deleteBlast(Map<String, String> map, SessionUser sessionUser);

    /**
     * 吐槽详情
     * 
     * @param map
     * @return
     */
    public Blast selectBlast(Map<String, String> map) throws BusinessException;
}
