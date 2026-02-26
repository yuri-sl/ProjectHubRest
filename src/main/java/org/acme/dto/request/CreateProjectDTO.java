package org.acme.dto.request;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApplicationScoped
public class CreateProjectDTO {
    private String name;
    private String description;
    private long userId;
    private boolean archived;
}
