package com.stockanalyzer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stockanalyzer.model.StockModel;
import com.stockanalyzer.repository.StockRepo;
import com.stockanalyzer.util.CsvHelper;

@Service
public class StockService {

	@Autowired
	StockRepo repo;

	public void save(MultipartFile file) {
		try {
			List<StockModel> tutorials = CsvHelper.csvToStocks(file.getInputStream());
			repo.saveAll(tutorials);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public List<StockModel> getAllStocks() {
		Iterable<StockModel> data = repo.findAll();
		List<StockModel> stocks = new ArrayList<>();
		data.forEach(stocks::add);
		return stocks;
	}
	
	public List<StockModel> getStockByName(String name){
		return repo.findByStock(name);
	}

}
