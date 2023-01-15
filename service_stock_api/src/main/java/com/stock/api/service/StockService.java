package com.stock.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.stock.config.RedisConfig;
import com.stock.api.repository.StockRepository;
import com.stock.model.DeliveryStockInfo;

@Service
public class StockService {
	@Autowired
	private RedisConfig redisConfig;
	
	@Autowired
	private StockRepository stockRepository;
	
	public List<DeliveryStockInfo> getStockInfo(DeliveryStockInfo deliveryStockInfo){
		return stockRepository.getStockInfo(deliveryStockInfo);
	}
	
	public Map<String, Object> countProcess(DeliveryStockInfo deliveryStockInfo, boolean isType) {
		String lockName = deliveryStockInfo.getPrdTitle() + ":lock";
		RLock lock = redisConfig.redissonClient().getLock(lockName);
        Map<String, Object> result = new HashMap<>();
        
        try {
            if (lock.tryLock(10, 3, TimeUnit.SECONDS)) {
            	try {
            		result = isType ? decreaseCount(deliveryStockInfo) : increaseCount(deliveryStockInfo);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (lock != null && lock.isLocked()) {
						lock.unlock();
					}
		        }
            }
        } catch (InterruptedException e) {
        	Thread.currentThread().interrupt();
        } 
        
        return result;
    }
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Object> decreaseCount(DeliveryStockInfo deliveryStockInfo) {
		Map<String, Object> result = new HashMap<>();
		boolean isSuccess = false;
		String message = "수량이 부족합니다.";
		
		Long count = stockRepository.getStockCount(deliveryStockInfo);
		
		if(count - deliveryStockInfo.getAmount() >= 0) {
			int stockAmount = stockRepository.stockDecrease(deliveryStockInfo);
			isSuccess = true;
			message = "성공되었습니다.";
			
			if(stockAmount < 0) {
				isSuccess = false;
				message = "시스템오류입니다.";
			}
		}
		
		result.put("success", isSuccess);
		result.put("message", message);
		return result;
    }
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Object> increaseCount(DeliveryStockInfo deliveryStockInfo) {
		Map<String, Object> result = new HashMap<>();
		boolean isSuccess = true;
		String message = "성공되었습니다.";
		
		int stockAmount = stockRepository.stockIncrease(deliveryStockInfo);
		
		if(stockAmount < 0) {
			isSuccess = false;
			message = "시스템오류입니다.";
		}
		
		result.put("success", isSuccess);
		result.put("message", message);
		return result;
    }
}