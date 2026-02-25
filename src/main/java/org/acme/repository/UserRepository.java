package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.entity.UserEntity;

public class UserRepository implements PanacheRepository<UserEntity> {
}
