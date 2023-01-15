package com.stock.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.stock.api.service.StockService;
import com.stock.model.DeliveryStockInfo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class stockServiceTest {
	
	@Autowired
	StockService stockService;

	@Test
    public void increaseTest() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        DeliveryStockInfo deliveryStockMap = new DeliveryStockInfo();
		deliveryStockMap.setPrdTitle("prd-a");
		deliveryStockMap.setOptTitle("opt-aa");
		deliveryStockMap.setAmount(1L);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                	stockService.countProcess(deliveryStockMap, false);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

    }
	
	@Test
    public void decreaseTest() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        DeliveryStockInfo deliveryStockMap = new DeliveryStockInfo();
		deliveryStockMap.setPrdTitle("prd-a");
		deliveryStockMap.setOptTitle("opt-aa");
		deliveryStockMap.setAmount(1L);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                	stockService.countProcess(deliveryStockMap, true);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

    }
}
