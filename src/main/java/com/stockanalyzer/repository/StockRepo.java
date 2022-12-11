package com.stockanalyzer.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.stockanalyzer.model.StockModel;

@Repository
public interface StockRepo extends PagingAndSortingRepository<StockModel, Integer>{
	
	List<StockModel> findByStock(String name);	
}
