package com.mlassa.citybike.service;

import com.mlassa.citybike.entity.BikeStation;

import java.util.List;


public interface BikeStationService {

    void saveAll(List<BikeStation> stations);

    List<BikeStation> getAllBikeStations();

    BikeStation findByName(String name);


}
