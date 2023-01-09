package com.stock.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.stock.api.repository.StockRepository;

@Service
public class StockService {
	private RedissonClient redissonClient;
	
	public StockService(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}

	@Autowired
	private StockRepository stockRepository;
	
	public List<Map<String, Object>> getStockInfo(String prdTitle, String optTitle){
		return stockRepository.getStockInfo(prdTitle, optTitle);
	}
	
	public Map<String, Object> countProcess(String prdTitle, String optTitle, Long amount, boolean isType) {
		String lockName = prdTitle + ":lock";
        RLock lock = redissonClient.getLock(lockName);
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            if (lock.tryLock(10, 3, TimeUnit.SECONDS)) {
            	try {
            		result = isType ? decreaseCount(prdTitle, optTitle, amount) : increaseCount(prdTitle, optTitle, amount);
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
    public Map<String, Object> decreaseCount(String prdTitle, String optTitle, Long amount) {
		Map<String, Object> result = new HashMap<>();
		boolean isSuccess = false;
		String message = "수량이 부족합니다.";
		
		Long count = stockRepository.getStockCount(prdTitle, optTitle);
		
		if(count - amount >= 0) {
			int stockAmount = stockRepository.stockDecrease(prdTitle, optTitle, amount);
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
    public Map<String, Object> increaseCount(String prdTitle, String optTitle, Long amount) {
		Map<String, Object> result = new HashMap<>();
		boolean isSuccess = true;
		String message = "성공되었습니다.";
		
		int stockAmount = stockRepository.stockIncrease(prdTitle, optTitle, amount);
		
		if(stockAmount < 0) {
			isSuccess = false;
			message = "시스템오류입니다.";
		}
		
		result.put("success", isSuccess);
		result.put("message", message);
		return result;
    }
}