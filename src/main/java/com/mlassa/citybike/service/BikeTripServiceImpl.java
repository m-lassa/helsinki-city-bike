package com.mlassa.citybike.service;

import com.mlassa.citybike.entity.BikeTrip;
import com.mlassa.citybike.repository.BikeTripRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeTripServiceImpl implements BikeTripService{

    private final BikeTripRepository bikeTripRepository;

    public BikeTripServiceImpl(BikeTripRepository bikeTripRepository) {
        this.bikeTripRepository = bikeTripRepository;
    }

    @Override
    public void saveAll(List<BikeTrip> trips) {
        bikeTripRepository.saveAll(trips);
    }

    @Override
    public List<BikeTrip> getAllBikeTrips() {
        return bikeTripRepository.findAll();
    }


}
