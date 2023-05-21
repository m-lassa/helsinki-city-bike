package com.mlassa.citybike.importer;

import com.mlassa.citybike.entity.BikeStation;
import com.mlassa.citybike.service.BikeStationService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class DataImporter {

    final BikeStationService bikeStationService;

    final JdbcTemplate jdbcTemplate;

    public DataImporter(BikeStationService bikeStationService, DataSource source) {
        this.bikeStationService = bikeStationService;
        this.jdbcTemplate = new JdbcTemplate(source);
    }

    @PostConstruct
    public void importData() throws IOException, CsvValidationException{

        importBikeStationData();

    }

    private void importBikeStationData() throws IOException, CsvValidationException {

        Reader reader = new FileReader("src/main/resources/data/asemat.csv");
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').withQuoteChar('"').build();
        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(csvParser).withSkipLines(1).build();

        List<BikeStation> stations = new ArrayList<>();

        String[] line;

        while((line = csvReader.readNext()) != null){

            Long id = Long.parseLong(line[1]);
            String name = line[2];
            String address = line[5];
            String city = line[7];
            if(city == null || city.isBlank()){
                city = "Helsinki";
            }
            String operator = line[9];
            Integer capacity = Integer.parseInt(line[10]);
            Double latitude = Double.parseDouble(line[11]);
            Double longitude = Double.parseDouble(line[12]);

            BikeStation station = new BikeStation();

            station.setStationId(id);
            station.setName(name);
            station.setAddress(address);
            station.setCity(city);
            station.setOperator(operator);
            station.setTotalCapacity(capacity);
            station.setLatitude(latitude);
            station.setLongitude(longitude);

            stations.add(station);

        }

        bikeStationService.saveAll(stations);

    }


}
