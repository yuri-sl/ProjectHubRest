package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.dto.request.CreateProjectDTO;
import org.acme.entity.ProjectEntity;
import org.acme.entity.UserEntity;
import org.acme.repository.ProjectRepository;
import org.acme.repository.UserRepository;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
@ApplicationScoped
public class ProjectService {
    final ProjectRepository projectRepository;
    final ProjectService projectService;
    final UserService userService;
    final UserRepository userRepository;

    @Transactional
    public ProjectEntity criarNovoProjeto(CreateProjectDTO dados){

        UserEntity owner = userRepository.findById(dados.getUserId());
        if(owner == null){
            throw new IllegalArgumentException("Usuário não encontrado");
        }


        ProjectEntity novoProjeto = ProjectEntity.builder()
                .name(dados.getName())
                .description(dados.getDescription())
                .ownerUser(owner)
                .archived(dados.isArchived())
                .createdAt(LocalDateTime.now())
                .build();
        projectRepository.persist(novoProjeto);
        projectRepository.flush();
        return novoProjeto;
    }


}


