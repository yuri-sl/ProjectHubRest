package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.acme.entity.UserEntity;

import java.util.List;

@AllArgsConstructor
@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {

    public List<UserEntity> fetchUsersByName(String name){
        return find("WHERE name = ?1",name).stream().toList();
    }



}
