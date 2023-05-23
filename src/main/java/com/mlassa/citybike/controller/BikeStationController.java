package com.mlassa.citybike.controller;

import com.mlassa.citybike.entity.BikeStation;
import com.mlassa.citybike.service.BikeStationService;
import com.mlassa.citybike.service.BikeTripService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BikeStationController {

    @Autowired
    private BikeStationService bikeStationService;

    @Autowired
    private BikeTripService bikeTripService;

    @GetMapping("/stations")
    public ModelAndView getStations(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "25") int size,
                                    @RequestParam(defaultValue = "name") String sortBy,
                                    HttpServletRequest request){

        Page<BikeStation> bikeStations = bikeStationService.getPaginatedBikeStations(page, size, Sort.by(sortBy).ascending());

        ModelAndView modelAndView = new ModelAndView("station-list");
        modelAndView.addObject("stations", bikeStations);
        modelAndView.addObject("requestUri", request.getRequestURI());

        return modelAndView;

    }

    @GetMapping("stations/{name}")
    public String getStationDetails(@PathVariable("name") String name, Model model){

        BikeStation station = bikeStationService.findByName(name);

        long tripsCountStarting = bikeTripService.countByStartStationName(name);
        long tripsCountEnding = bikeTripService.countByEndStationName(name);

        model.addAttribute("bikeStation", station);
        model.addAttribute("tripsCountStarting", tripsCountStarting);
        model.addAttribute("tripsCountEnding", tripsCountEnding);

        return "station-details";

    }
}
