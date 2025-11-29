package com.aptlytics.auth_service.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiInfoDTO {
    private String serviceName;
    private boolean running;
    private LocalDate dateChecked;
}
