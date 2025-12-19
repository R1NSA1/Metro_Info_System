package com.example.metroserver.controller;

import com.example.metroserver.dto.RouteResponse;
import com.example.metroserver.service.RouteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")
@CrossOrigin
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public RouteResponse findRoute(@RequestParam("fromId") Long fromId,
                                   @RequestParam("toId") Long toId) {
        return routeService.findRoute(fromId, toId);
    }
}
