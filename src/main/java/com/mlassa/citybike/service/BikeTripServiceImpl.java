package com.mlassa.citybike.service;

import com.mlassa.citybike.entity.BikeTrip;
import com.mlassa.citybike.repository.BikeTripRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<BikeTrip> getPaginatedBikeTrips(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bikeTripRepository.findAll(pageable);
    }
}
