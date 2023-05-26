package com.mlassa.citybike.repository;

import com.mlassa.citybike.entity.BikeTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing bike trips.
 * It provides methods for accessing bike trip data.
 */
@Repository
public interface BikeTripRepository extends JpaRepository<BikeTrip, Long> {

    long countByStartStationName(String stationName);

    long countByEndStationName(String stationName);

}
