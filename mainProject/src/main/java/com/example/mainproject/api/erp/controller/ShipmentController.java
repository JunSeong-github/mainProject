package com.example.mainproject.api.erp.controller;

import com.example.mainproject.api.erp.dto.shipment.*;
import com.example.mainproject.service.erp.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService svc;
    public ShipmentController(ShipmentService svc) { this.svc = svc; }

    @PostMapping
    public ShipmentResponse ship(@RequestBody @Valid ShipmentCreateRequest req) {
        return svc.ship(req);
    }
}
