package com.stock.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

@Repository
public class StockRepository {
	@Autowired(required=true)
	private SqlSession sqlSession;
	private static final String ns = "com.stock.";
	
	public List<Map<String, Object>> getStockInfo(String prdTitle, String optTitle) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("prdTitle", prdTitle);
		parameters.put("optTitle", optTitle);
		
		return sqlSession.selectList(ns + "getStockInfo", parameters);
	}
	
	public Long getStockCount(String prdTitle, String optTitle) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("prdTitle", prdTitle);
		parameters.put("optTitle", optTitle);
		
		return sqlSession.selectOne(ns + "getStockCount", parameters);
	}
	
	public int stockDecrease(String prdTitle, String optTitle, Long amount) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("prdTitle", prdTitle);
		parameters.put("optTitle", optTitle);
		parameters.put("amount", amount);
		
		return sqlSession.update(ns + "stockDecrease", parameters);
	}
	
	public int stockIncrease(String prdTitle, String optTitle, Long amount) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("prdTitle", prdTitle);
		parameters.put("optTitle", optTitle);
		parameters.put("amount", amount);
		
		return sqlSession.update(ns + "stockIncrease", parameters);
	}
}