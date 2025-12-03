package com.aptlytics.response;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResponse<T> {

    @Builder.Default
    private UUID requestId = UUID.randomUUID();

    @Builder.Default
    private LocalDateTime completedAt = LocalDateTime.now();
    private boolean success;
    private T data;

    @Builder.Default
    private List<? extends RuntimeException> exceptions = new ArrayList<>();

    public static <T> ResponseEntity<ServiceResponse<T>> getResponse(
            boolean isSuccess,
            T data,
            List<? extends RuntimeException> exceptions
    ) {
        if (exceptions == null) {
            exceptions = new ArrayList<>();
        }

        var response = ServiceResponse.<T>builder()
                .success(isSuccess)
                .data(data)
                .exceptions(exceptions)
                .build();

        return new ResponseEntity<>(response, getHttpStatusCode(isSuccess));
    }

    private static HttpStatus getHttpStatusCode(boolean isSuccess) {
        return isSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }
}
