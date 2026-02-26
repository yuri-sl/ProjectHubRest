package org.acme.entity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
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
@Entity
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn
    private UserEntity ownerUser;

    @Column
    private boolean archived;

    @Column
    private LocalDateTime createdAt;

}
