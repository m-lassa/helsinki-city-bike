package com.mlassa.citybike.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class BikeAppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        // Get error status code and message
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object error = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        // Pass the status code and message to the model
        model.addAttribute("status", status != null ? status.toString() : "");
        model.addAttribute("error", error != null ? error.toString() : "");

        // Return the error page
        return "error";

    }

}
