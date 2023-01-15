package com.stock.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.api.service.StockService;
import com.stock.model.DeliveryStockInfo;

@RestController
@RequestMapping("/stock")
public class StockController {
	
	@Autowired
	StockService stockService;
	
	@RequestMapping(method = RequestMethod.GET, path = "/getInfo")
	public Object getStockInfo(
			@RequestParam("prdTitle") String prdTitle,
			@RequestParam(value = "optTitle", required = false, defaultValue = "") String optTitle) {
		DeliveryStockInfo deliveryStockMap = new DeliveryStockInfo();
		deliveryStockMap.setPrdTitle(prdTitle);
		deliveryStockMap.setOptTitle(optTitle);		
		
		return stockService.getStockInfo(deliveryStockMap);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/decrease")
	public Object decrease(
			@RequestParam("prdTitle") String prdTitle,
			@RequestParam("optTitle") String optTitle,
			@RequestParam("amount") Long amount) {
		DeliveryStockInfo deliveryStockMap = new DeliveryStockInfo();
		deliveryStockMap.setPrdTitle(prdTitle);
		deliveryStockMap.setOptTitle(optTitle);
		deliveryStockMap.setAmount(amount);
		
		return stockService.countProcess(deliveryStockMap, true);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/increase")
	public Object increase(
			@RequestParam("prdTitle") String prdTitle,
			@RequestParam("optTitle") String optTitle,
			@RequestParam("amount") Long amount) {
		DeliveryStockInfo deliveryStockMap = new DeliveryStockInfo();
		deliveryStockMap.setPrdTitle(prdTitle);
		deliveryStockMap.setOptTitle(optTitle);
		deliveryStockMap.setAmount(amount);
		
		return stockService.countProcess(deliveryStockMap, false);
	}
}