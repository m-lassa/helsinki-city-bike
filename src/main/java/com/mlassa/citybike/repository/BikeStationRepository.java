package com.mlassa.citybike.repository;

import com.mlassa.citybike.entity.BikeStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeStationRepository extends JpaRepository<BikeStation, Long> {

    BikeStation findByName(String name);

}
