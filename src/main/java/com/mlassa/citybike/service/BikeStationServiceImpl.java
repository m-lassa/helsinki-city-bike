package com.mlassa.citybike.service;

import com.mlassa.citybike.entity.BikeStation;
import com.mlassa.citybike.repository.BikeStationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeStationServiceImpl implements BikeStationService{

    private final BikeStationRepository bikeStationRepository;

    public BikeStationServiceImpl(BikeStationRepository bikeStationRepository) {
        this.bikeStationRepository = bikeStationRepository;
    }

    @Override
    public void saveAll(List<BikeStation> stations) {
        bikeStationRepository.saveAll(stations);
    }

    @Override
    public List<BikeStation> getAllBikeStations() {
        return bikeStationRepository.findAll();
    }

    @Override
    public BikeStation findByName(String name) {
        return bikeStationRepository.findByName(name);
    }

    @Override
    public Page<BikeStation> getPaginatedBikeStations(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return bikeStationRepository.findAll(pageable);
    }

}
