package com.stockanalyzer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.stockanalyzer.model.StockModel;

public class CsvHelper {
	public static String TYPE = "text/csv";

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<StockModel> csvToStocks(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<StockModel> stocks = new ArrayList<StockModel>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				StockModel tutorial = new StockModel(csvRecord.get("quarter"), csvRecord.get("stock"),
						csvRecord.get("date"), csvRecord.get("open"), csvRecord.get("high"), csvRecord.get("low"),
						csvRecord.get("close"), csvRecord.get("volume"), csvRecord.get("percent_change_price"),
						csvRecord.get("percent_change_volume_over_last_wk"), csvRecord.get("previous_weeks_volume"),
						csvRecord.get("next_weeks_open"), csvRecord.get("next_weeks_close"),
						csvRecord.get("percent_change_next_weeks_price"), csvRecord.get("days_to_next_dividend"),
						csvRecord.get("percent_return_next_dividend"));

				stocks.add(tutorial);
			}

			return stocks;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

}