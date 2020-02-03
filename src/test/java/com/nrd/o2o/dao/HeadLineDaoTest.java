package com.nrd.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nrd.o2o.BaseTest;
import com.nrd.o2o.entity.HeadLine;

public class HeadLineDaoTest extends BaseTest{
	@Autowired
	private HeadLineDao headLineDao;

	@Test
	public void testSelectHeadLine() throws Exception {
		HeadLine headLineCondition = new HeadLine();
		List<HeadLine> headLineList = headLineDao.queryHeadLine(headLineCondition);
		assertEquals(0, headLineList.size());
	}
}
