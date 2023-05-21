package com.mlassa.citybike.controller;

import com.mlassa.citybike.entity.BikeStation;
import com.mlassa.citybike.service.BikeStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BikeStationController {

    @Autowired
    private BikeStationService bikeStationService;

    @GetMapping("/stations")
    public String getStations(Model model){

        List<BikeStation> stations = bikeStationService.getAllBikeStations();

        model.addAttribute("stations", stations);

        return "station-list";

    }

    @GetMapping("stations/{name}")
    public String getStationDetails(@PathVariable("name") String name, Model model){

        BikeStation station = bikeStationService.findByName(name);

        model.addAttribute("bikeStation", station);

        return "station-details";

    }
}
