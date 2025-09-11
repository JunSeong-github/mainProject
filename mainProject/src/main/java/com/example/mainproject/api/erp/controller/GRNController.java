package com.example.mainproject.api.erp.controller;

import com.example.mainproject.api.erp.dto.grn.*;
import com.example.mainproject.service.erp.GRNService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grn")
public class GRNController {

    private final GRNService svc;
    public GRNController(GRNService svc) { this.svc = svc; }

    @PostMapping
    public GRNResponse receive(@RequestBody @Valid GRNCreateRequest req) {
        return svc.receive(req);
    }
}
