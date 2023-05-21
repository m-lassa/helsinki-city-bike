package com.mlassa.citybike.importer;

import com.mlassa.citybike.entity.BikeStation;
import com.mlassa.citybike.entity.BikeTrip;
import com.mlassa.citybike.service.BikeStationService;
import com.mlassa.citybike.service.BikeTripService;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class DataImporter {

    final BikeStationService bikeStationService;
    final BikeTripService bikeTripService;
    final JdbcTemplate jdbcTemplate;

    public DataImporter(BikeStationService bikeStationService, BikeTripService bikeTripService, DataSource source) {
        this.bikeStationService = bikeStationService;
        this.bikeTripService = bikeTripService;
        this.jdbcTemplate = new JdbcTemplate(source);
    }

    @PostConstruct
    public void importData() throws IOException, CsvValidationException{

        boolean firstTimeDatabaseSetup = databaseCheck();

        if(firstTimeDatabaseSetup){
            try{
                importBikeStationData("src/main/resources/data/asemat.csv");
            } catch (FileNotFoundException ex){
                System.out.println("asemat.csv not found in the resources/data folder.");
            }

            try{
                importBikeTripData("src/main/resources/data/2021-05.csv");
            } catch (FileNotFoundException ex){
                System.out.println("2021-05.csv not found in the resources/data folder.");
            }

        }

    }

    private boolean databaseCheck() {

        System.out.println(bikeStationService.getAllBikeStations().size());

        return bikeStationService.getAllBikeStations().size() == 0;

    }

    private void importBikeTripData(String filePath) throws IOException, CsvValidationException {

        Reader reader = new FileReader(filePath);
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').withQuoteChar('"').build();
        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(csvParser).withSkipLines(1).build();

        List<BikeTrip> trips = new ArrayList<>();

        String[] line;

        LocalDateTime departureTime;
        LocalDateTime returnTime;
        double distance;
        double duration;

        while((line = csvReader.readNext()) != null){

            try{
                departureTime = LocalDateTime.parse(line[0], DateTimeFormatter.ISO_DATE_TIME);
            } catch (DateTimeException ex) {continue;}

            try{
                returnTime = LocalDateTime.parse(line[1], DateTimeFormatter.ISO_DATE_TIME);
            } catch (DateTimeException ex) {continue;}

            Long startStationId = Long.parseLong(line[2]);
            String startStationName = line[3];
            Long endStationId = Long.parseLong(line[4]);
            String endStationName = line[5];

            try{
                distance = Double.parseDouble(line[6]);
            } catch (NumberFormatException ex) {continue;}

            try{
                duration = Double.parseDouble(line[7]);
            } catch (NumberFormatException ex) {continue;}

            // Perform validation of the csv data: journeys shorted than 10 meters or 10 seconds are exluded
            if(duration >= 10 && distance >= 10){
                BikeTrip trip = new BikeTrip();
                trip.setDepartureTime(departureTime);
                trip.setReturnTime(returnTime);
                trip.setStartStationId(startStationId);
                trip.setStartStationName(startStationName);
                trip.setEndStationId(endStationId);
                trip.setEndStationName(endStationName);
                trip.setDistance(distance / 1000);
                trip.setDuration(duration / 60);

                trips.add(trip);
            }
        }

        bikeTripService.saveAll(trips);

    }

    private void importBikeStationData(String filePath) throws IOException, CsvValidationException {

        Reader reader = new FileReader(filePath);
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
