package com.nrd.o2o.service;

import java.io.IOException;
import java.util.List;

import com.nrd.o2o.entity.HeadLine;

public interface HeadLineService {
	/**
	 * 根据条件返回头条列表
	 * 
	 * @param leadLineCondition
	 * @return
	 * @throws IOException
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
