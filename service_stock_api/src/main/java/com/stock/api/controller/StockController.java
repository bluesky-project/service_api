package com.stock.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.api.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {
	@Autowired
	StockService stockService;
	
	@GetMapping("/getInfo")
	public Object getStockInfo(
			@RequestParam("prdTitle") String prdTitle,
			@RequestParam(value = "optTitle", required = false, defaultValue = "") String optTitle) {
		return stockService.getStockInfo(prdTitle, optTitle);
	}
	
	@PostMapping("/decrease")
	public Object decrease(
			@RequestParam("prdTitle") String prdTitle,
			@RequestParam("optTitle") String optTitle,
			@RequestParam("amount") Long amount) {
		return stockService.countProcess(prdTitle, optTitle, amount, true);
	}
	
	@PostMapping("/increase")
	public Object increase(
			@RequestParam("prdTitle") String prdTitle,
			@RequestParam("optTitle") String optTitle,
			@RequestParam("amount") Long amount) {
		return stockService.countProcess(prdTitle, optTitle, amount, false);
	}
}