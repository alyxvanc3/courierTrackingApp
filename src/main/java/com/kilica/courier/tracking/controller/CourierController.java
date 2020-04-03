package com.kilica.courier.tracking.controller;

import com.kilica.courier.tracking.model.LogCourierRequest;
import com.kilica.courier.tracking.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourierController {

    @Autowired
    private CourierService courierService;

    @GetMapping(path="/log")
    public Void logIfCourierinZone(@RequestBody LogCourierRequest request) {

        courierService.logIfCourierinZone(request.getTime(), request.getId(),
                request.getLatitude(), request.getLongitude());

        return null;
    }

    @GetMapping(path="/travelDistance")
    public Double getTotalTravelDistance(@RequestParam Long id) {
        return courierService.getTotalTravelDistance(id);
    }

}
