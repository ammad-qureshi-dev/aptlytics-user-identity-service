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
@Table(name = "app_user", schema = "identity_service")
public class AppUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @NonNull
    private String fullName;

    private String email;

    @NonNull
    private String password;

    private String dateOfBirth;
    private boolean isVerified;
    private String lastSignedInAs;
}
