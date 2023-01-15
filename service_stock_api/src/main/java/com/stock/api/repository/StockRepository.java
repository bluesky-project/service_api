package com.stock.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stock.model.DeliveryStockInfo;

import java.util.List;
import org.apache.ibatis.session.SqlSession;

@Repository
public class StockRepository {
	@Autowired(required=true)
	private SqlSession sqlSession;
	private static final String ns = "com.stock.";
	
	public List<DeliveryStockInfo> getStockInfo(DeliveryStockInfo deliveryStockInfo) {
		return sqlSession.selectList(ns + "getStockInfo", deliveryStockInfo);
	}
	
	public Long getStockCount(DeliveryStockInfo deliveryStockInfo) {
		return sqlSession.selectOne(ns + "getStockCount", deliveryStockInfo);
	}
	
	public int stockDecrease(DeliveryStockInfo deliveryStockInfo) {
		return sqlSession.update(ns + "stockDecrease", deliveryStockInfo);
	}
	
	public int stockIncrease(DeliveryStockInfo deliveryStockInfo) {
		return sqlSession.update(ns + "stockIncrease", deliveryStockInfo);
	}
}