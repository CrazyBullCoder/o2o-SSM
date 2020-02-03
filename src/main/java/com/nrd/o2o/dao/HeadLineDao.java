package com.nrd.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nrd.o2o.entity.HeadLine;

public interface HeadLineDao {
	/**
	 * 根据条件查询头条
	 * @param headLineCondition
	 * @return
	 */
	List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
