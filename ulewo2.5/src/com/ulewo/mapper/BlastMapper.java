package com.ulewo.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 吐槽
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:15:54
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface BlastMapper<T> extends BaseMapper<T> {
    public T selectBlastByBlastId(@Param("blastId") Integer blastId);
}
