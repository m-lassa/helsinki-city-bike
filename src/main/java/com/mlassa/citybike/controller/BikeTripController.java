package com.mlassa.citybike.controller;

import com.mlassa.citybike.entity.BikeTrip;
import com.mlassa.citybike.service.BikeTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BikeTripController {

    @Autowired
    private BikeTripService bikeTripService;

    @GetMapping("/trips")
    public String getBikeTrips(Model model){

        List<BikeTrip> trips = bikeTripService.getAllBikeTrips();

        model.addAttribute("trips", trips);

        return "trip-list";

    }

}
