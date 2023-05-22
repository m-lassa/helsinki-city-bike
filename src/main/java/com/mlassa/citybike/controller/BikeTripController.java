package com.mlassa.citybike.controller;

import com.mlassa.citybike.entity.BikeTrip;
import com.mlassa.citybike.service.BikeTripService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BikeTripController {

    @Autowired
    private BikeTripService bikeTripService;

    @GetMapping("/trips")
    public ModelAndView getBikeTrips(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            HttpServletRequest request) {

        Page<BikeTrip> bikeTrips = bikeTripService.getPaginatedBikeTrips(page, size);

        ModelAndView modelAndView = new ModelAndView("trip-list");
        modelAndView.addObject("trips", bikeTrips);
        modelAndView.addObject("requestUri", request.getRequestURI());

        return modelAndView;

    }

}
