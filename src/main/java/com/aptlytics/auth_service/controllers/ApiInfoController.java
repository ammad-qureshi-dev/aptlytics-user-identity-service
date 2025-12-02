package com.aptlytics.auth_service.controllers;

import com.aptlytics.auth_service.models.dtos.ApiInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/health")
public class ApiInfoController {

    @GetMapping("/info")
    public ResponseEntity<ApiInfoDTO> apiInfo() {
        return ResponseEntity.ok(ApiInfoDTO.builder()
                        .dateChecked(LocalDate.now())
                        .running(true)
                        .serviceName("auth-service")
                .build());
    }

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Running");
    }

}
