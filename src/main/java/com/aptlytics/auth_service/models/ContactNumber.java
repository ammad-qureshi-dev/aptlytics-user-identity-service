package com.aptlytics.auth_service.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact_number", schema = "identity_service")
public class ContactNumber extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID contactNumberId;

    @NonNull
    private String countryCode;
    @NonNull
    private String areaCode;
    @NonNull
    private String number;
    private String extension;

    private UUID userId;
}
