package com.stockanalyzer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stockanalyzer.dto.JwtResponseDto;
import com.stockanalyzer.dto.ResponseDto;
import com.stockanalyzer.model.StockModel;
import com.stockanalyzer.service.StockService;
import com.stockanalyzer.util.CsvHelper;


@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/csv")
public class StockController {

  @Autowired
  StockService service;

  @PostMapping("/upload")
  public ResponseEntity<JwtResponseDto> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";

    if (CsvHelper.hasCSVFormat(file)) {
      try {
        service.save(file);
        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponseDto(message));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new JwtResponseDto(message));
      }
    }

    message = "Please upload a csv file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JwtResponseDto(message));
  }

  @GetMapping("/stock")
  public ResponseEntity<List<StockModel>> getAllStock() {
    try {
      List<StockModel> data = service.getAllStocks();

      if (data.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(data, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
    
    @GetMapping("/stock/{name}")
    public ResponseEntity<Object> getStockByName(@PathVariable String name) {
      try {
        List<StockModel> stock = service.getStockByName(name);
        if (stock.isEmpty()) {
          return new ResponseEntity<>(new ResponseDto(HttpStatus.NO_CONTENT.value(), "Stock found in the Database!!"),
  				HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(stock, HttpStatus.OK);
      } catch (Exception e) {
    	  e.printStackTrace();
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

}