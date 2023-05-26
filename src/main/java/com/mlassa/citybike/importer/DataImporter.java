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

/**
 * The DataImporter class contains methods to import data from the .csv files to the in-memory database.
 * It makes use of the Service instances to save the data.
 */
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

    /**
     * Checks whether data has already been imported; if now, it calls the appropriate import methods
     * by referencing the .csv files in the /resources/data folder.
     * @throws IOException
     * @throws CsvValidationException
     */
    @PostConstruct
    public void importData() throws IOException, CsvValidationException{

        if(stationsDatabaseCheck()){
            try{
                importBikeStationData("src/main/resources/data/asemat.csv");
            } catch (FileNotFoundException ex){
                System.out.println("asemat.csv not found in the resources/data folder.");
            }

            try {
                importBikeTripData("src/main/resources/data/2021-05.csv");
            } catch (FileNotFoundException ex) {
                System.out.println("2021-05.csv not found in the resources/data folder.");
            }

            try {
                importBikeTripData("src/main/resources/data/2021-06.csv");
            } catch (FileNotFoundException ex) {
                System.out.println("2021-06.csv not found in the resources/data folder.");
            }

            try {
                importBikeTripData("src/main/resources/data/2021-07.csv");
            } catch (FileNotFoundException ex) {
                System.out.println("2021-07.csv not found in the resources/data folder.");
            }

        }

    }

    private boolean stationsDatabaseCheck() {

        return bikeStationService.getAllBikeStations().size() == 0;

    }

    /**
     * Takes the file path of the bike trip csv file, and performs data validation and import of the bike trip data.
     * The .csv is parsed and read using the appropriate classes from the OpenCSV library.
     * The data is stored into instances of the BikeTrip entity and saved with the help of the Service.
     * @param filePath
     * @throws IOException
     * @throws CsvValidationException
     */
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

        // Read the values from the file
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

            // Perform validation of the csv data: journeys shorted than 10 meters or 10 seconds are excluded
            if(duration >= 10 && distance >= 10){

                BikeTrip trip = new BikeTrip();

                // set the values of the trip instance
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

        // use the BikeTripService to save the list of trips
        bikeTripService.saveAll(trips);

    }

    /**
     * Takes the file path of the bike station csv file, and performs data validation and import of the bike station data.
     * The .csv is parsed and read using the appropriate classes from the OpenCSV library.
     * The data is stored into instances of the BikeStation entity and saved with the help of the Service.
     * @param filePath
     * @throws IOException
     * @throws CsvValidationException
     */
    private void importBikeStationData(String filePath) throws IOException, CsvValidationException {

        Reader reader = new FileReader(filePath);
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').withQuoteChar('"').build();
        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(csvParser).withSkipLines(1).build();

        List<BikeStation> stations = new ArrayList<>();

        String[] line;

        // Read the values from the file
        while((line = csvReader.readNext()) != null){

            Long id = Long.parseLong(line[1]);
            String name = line[2];
            String address = line[5];
            String city = line[7];
            // it is assumed that the city is Helsinki where the value is missing
            if(city == null || city.isBlank()){
                city = "Helsinki";
            }
            String operator = line[9];
            Integer capacity = Integer.parseInt(line[10]);
            Double latitude = Double.parseDouble(line[11]);
            Double longitude = Double.parseDouble(line[12]);

            BikeStation station = new BikeStation();

            // set the values of the station instance
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

        // use the BikeStationService to save the list of trips
        bikeStationService.saveAll(stations);

    }


}
