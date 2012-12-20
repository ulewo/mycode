package com.lhl.common.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class BaseDao extends SqlMapClientDaoSupport
{
	@SuppressWarnings("unchecked")
	public Integer inserttBatch(String statementName, List list) throws SQLException
	{

		Object object = null;
		Integer jj = 0;
		for (int i = 0; i < list.size(); i++)
		{
			this.getSqlMapClient().startBatch();
			object = list.get(i);
			this.getSqlMapClient().insert(statementName, object);
			jj++;
			if (i >= 300)
			{

				this.getSqlMapClient().executeBatch();
			}

		}
		this.getSqlMapClient().executeBatch();
		return jj;
	}

	/**
	 * 批量修改
	 * 
	 * @param statementName
	 * @param t
	 * 
	 *            
	 */
	@SuppressWarnings("unchecked")
	public int updateBatch(String statementName, List list) throws SQLException
	{

		Object object = null;
		int jj = 0;
		for (int i = 0; i < list.size(); i++)
		{
			this.getSqlMapClient().startBatch();
			object = list.get(i);
			this.getSqlMapClient().update(statementName, object);
			jj++;
			if (i >= 300)
			{
				this.getSqlMapClient().executeBatch();
			}

		}
		this.getSqlMapClient().executeBatch();
		return jj;
	}

	/**
	 * 更新数据
	 * 
	 * @param statementName
	 * @param t
	 */
	public int update(String statementName, Object object)
	{

		return this.getSqlMapClientTemplate().update(statementName, object);
	}

	/**
	 * 添加数据
	 * 
	 * @param statementName
	 * @param t
	 * @return
	 */
	public Object add(String statementName, Object object)
	{

		Object ob = this.getSqlMapClientTemplate().insert(statementName, object);
		return ob;
	}

	/**
	 * 根据条件查询一条记录
	 * 
	 * @param statementName
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = (Propagation.NOT_SUPPORTED))
	public <T> T get(String statementName, Object object)
	{

		return (T) this.getSqlMapClientTemplate().queryForObject(statementName, object);
	}

	/**
	 * 根据条件查询数据
	 * 
	 * @param statementName
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = (Propagation.NOT_SUPPORTED))
	public <T> List<T> searchAll(String statementName, Object object) throws DataAccessException
	{

		return this.getSqlMapClientTemplate().queryForList(statementName, object);
	}

	/**
	 * 删除符合条件的记录
	 * 
	 * @param statementName
	 * @param t
	 * @return
	 */
	public int delete(String statementName, Object object)
	{

		return this.getSqlMapClientTemplate().delete(statementName, object);
	}
}
