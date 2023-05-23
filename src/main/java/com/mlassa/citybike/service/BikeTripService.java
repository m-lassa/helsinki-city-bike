package com.mlassa.citybike.service;

import com.mlassa.citybike.entity.BikeTrip;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BikeTripService {

    void saveAll(List<BikeTrip> trips);

    List<BikeTrip> getAllBikeTrips();

    Page<BikeTrip> getPaginatedBikeTrips(int page, int size);

    long countByStartStationName(String stationName);

    long countByEndStationName(String stationName);

}
