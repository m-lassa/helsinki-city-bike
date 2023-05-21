package com.mlassa.citybike.service;

import com.mlassa.citybike.entity.BikeTrip;

import java.util.List;

public interface BikeTripService {

    void saveAll(List<BikeTrip> trips);

    List<BikeTrip> getAllBikeTrips();

}
