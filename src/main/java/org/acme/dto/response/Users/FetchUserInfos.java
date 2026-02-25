package org.acme.dto.response.Users;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApplicationScoped
public class FetchUserInfos {
    private long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}
