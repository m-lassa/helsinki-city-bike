package com.mlassa.citybike.service;

import com.mlassa.citybike.entity.BikeStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Service interface for managing bike stations.
 * It defines methods for retrieving and manipulating bike station data
 */
public interface BikeStationService {

    void saveAll(List<BikeStation> stations);

    List<BikeStation> getAllBikeStations();

    BikeStation findByName(String name);

    Page<BikeStation> getPaginatedBikeStations(int page, int size, Sort sort);


}
