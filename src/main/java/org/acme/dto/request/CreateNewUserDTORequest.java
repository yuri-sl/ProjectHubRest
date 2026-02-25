package org.acme.dto.request;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
@NoArgsConstructor
@Builder
@Data
public class CreateNewUserDTORequest {
    private String name;
    private String email;
    private String passwordHash;
}
