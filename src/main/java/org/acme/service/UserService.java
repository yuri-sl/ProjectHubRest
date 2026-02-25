package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.acme.dto.request.CreateNewUserDTORequest;
import org.acme.dto.response.Users.FetchUserInfos;
import org.acme.entity.UserEntity;
import org.acme.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ApplicationScoped
public class UserService {
    final UserRepository userRepository;

    public List<FetchUserInfos> buscarTodosUsuarios(){
        List<FetchUserInfos> listaUsuarios = new ArrayList<>();
        List<UserEntity> listaUsuariosBanco = this.userRepository.findAll().stream().toList();
        for(UserEntity u : listaUsuariosBanco){
            FetchUserInfos userRebuilt = FetchUserInfos.builder()
                    .id(u.getId())
                    .name(u.getName())
                    .email(u.getEmail())
                    .createdAt(u.getCreatedAt())
                    .build();

            listaUsuarios.add(userRebuilt);
        }
        return listaUsuarios;
    }

    public boolean validarCamposInput(CreateNewUserDTORequest createNewUserDTORequest){
        if(createNewUserDTORequest.getName().isBlank() ||
            createNewUserDTORequest.getEmail().isBlank() ||
        createNewUserDTORequest.getPasswordHash().isBlank()
        ){
            throw new IllegalArgumentException("Todos os campos devem estar preenchidos");
        }
        return true;
    }

    public List<FetchUserInfos> buscarUsuarioPorNome(String username){
        List<UserEntity> usuariosBucados = userRepository.fetchUsersByName(username);
        List<FetchUserInfos> usuariosPopuladosDTO = new ArrayList<>();
        if(usuariosBucados.isEmpty()){
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        for(UserEntity u : usuariosBucados){
            FetchUserInfos userRebuilt = FetchUserInfos.builder()
                    .id(u.getId())
                    .name(u.getName())
                    .email(u.getEmail())
                    .createdAt(u.getCreatedAt())
                    .build();
            usuariosPopuladosDTO.add(userRebuilt);
        }
        return usuariosPopuladosDTO;
    }

    @Transactional
    public FetchUserInfos criarNovoUsuario(CreateNewUserDTORequest createNewUserDTORequest){
        try{
            validarCamposInput(createNewUserDTORequest);
            UserEntity usuarioCriadoNovo = UserEntity.builder()
                    .name(createNewUserDTORequest.getName())
                    .email(createNewUserDTORequest.getEmail())
                    .passwordHash(createNewUserDTORequest.getPasswordHash())
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.persist(usuarioCriadoNovo);
            userRepository.flush();

            FetchUserInfos fetchUserInfos = FetchUserInfos.builder()
                    .id(usuarioCriadoNovo.getId())
                    .name(usuarioCriadoNovo.getName())
                    .email(usuarioCriadoNovo.getEmail())
                    .createdAt(usuarioCriadoNovo.getCreatedAt())
                    .build();

            return fetchUserInfos;
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Todos os campos devem estar preenchidos");
        }
    }
}
