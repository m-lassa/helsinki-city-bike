package com.mlassa.citybike.service;

import com.mlassa.citybike.entity.BikeTrip;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface for managing bike trips.
 * It defines methods for retrieving and manipulating bike trip data, including
 * methods to count the number of bike trips based on start station and end station.
 */
public interface BikeTripService {

    void saveAll(List<BikeTrip> trips);

    List<BikeTrip> getAllBikeTrips();

    Page<BikeTrip> getPaginatedBikeTrips(int page, int size);

    long countByStartStationName(String stationName);

    long countByEndStationName(String stationName);

}
