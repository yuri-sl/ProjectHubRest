package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.acme.entity.ProjectEntity;

@AllArgsConstructor
@ApplicationScoped
public class ProjectRepository implements PanacheRepository<ProjectEntity> {
}
