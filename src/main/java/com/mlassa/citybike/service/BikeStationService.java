package com.mlassa.citybike.service;

import com.mlassa.citybike.entity.BikeStation;
import org.springframework.data.domain.Page;

import java.util.List;


public interface BikeStationService {

    void saveAll(List<BikeStation> stations);

    List<BikeStation> getAllBikeStations();

    BikeStation findByName(String name);

    Page<BikeStation> getPaginatedBikeStations(int page, int size);


}
