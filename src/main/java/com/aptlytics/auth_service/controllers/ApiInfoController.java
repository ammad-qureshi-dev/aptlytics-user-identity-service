package com.aptlytics.auth_service.controllers;

import com.aptlytics.auth_service.models.dtos.ApiInfoDTO;
import com.aptlytics.response.ServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;

import static com.aptlytics.response.ServiceResponse.getResponse;

@RestController
@RequestMapping("/api/health")
public class ApiInfoController {

    @GetMapping("/info")
    public ResponseEntity<ServiceResponse<ApiInfoDTO>> apiInfo() {
        var info = ApiInfoDTO.builder()
                .dateChecked(LocalDate.now())
                .running(true)
                .serviceName("auth-service")
                .build();

        return getResponse(true, info, Collections.emptyList());
    }

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Running");
    }

}
