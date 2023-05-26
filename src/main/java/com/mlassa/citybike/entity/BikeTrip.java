package com.mlassa.citybike.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Represents a bike trip entity.
 * A bike trip consists of information about the departure/end time of trips, start station, end station,
 * distance, and duration of the trip.
 */
@Entity
@Table(name="bike_trip")
public class BikeTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="start_time")
    private LocalDateTime departureTime;

    @Column(name="end_time")
    private LocalDateTime returnTime;

    @Column(name="start_station_id")
    private Long startStationId;

    @Column(name="start_station_name")
    private String startStationName;

    @Column(name="end_station_id")
    private Long endStationId;

    @Column(name="end_station_name")
    private String endStationName;

    @Column(name="distance")
    private Double distance;

    @Column(name="duration")
    private Double duration;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }

    public Long getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(Long startStationId) {
        this.startStationId = startStationId;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public Long getEndStationId() {
        return endStationId;
    }

    public void setEndStationId(Long endStationId) {
        this.endStationId = endStationId;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "BikeTrip{" +
                "id=" + id +
                ", departureTime=" + departureTime +
                ", returnTime=" + returnTime +
                ", startStationId=" + startStationId +
                ", startStationName='" + startStationName + '\'' +
                ", endStationId=" + endStationId +
                ", endStationName='" + endStationName + '\'' +
                ", distance=" + distance +
                ", duration=" + duration +
                '}';
    }
}
