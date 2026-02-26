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
        List<UserEntity> listaUsuariosBanco = this.userRepository.findAll().stream().toList();
        return FetchUserInfos.mapearListaEntidadeDTO(listaUsuariosBanco);
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

        if(usuariosBucados.isEmpty()){
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        return FetchUserInfos.mapearListaEntidadeDTO(usuariosBucados);
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

            return FetchUserInfos.mapearEntidadeDTO(usuarioCriadoNovo);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Todos os campos devem estar preenchidos");
        }
    }
    public FetchUserInfos listarUsuarioPorId(long userId){
        UserEntity usuarioEncontrado = userRepository.findById(userId);
        return FetchUserInfos.mapearEntidadeDTO(usuarioEncontrado);
    }

    @Transactional
    public void deletarUsuarioPorId(long userId){
        try{
            UserEntity usuarioEncontrado = userRepository.findById(userId);
            if(usuarioEncontrado == null){
                throw new IllegalArgumentException("Usuário não encontrado no sistema");
            }
            userRepository.deleteById(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
    }
    @Transactional
    public void updateUserById(long userId,CreateNewUserDTORequest dadosAtuallizacao){
        try{
            UserEntity usuarioEncontrado = userRepository.findById(userId);
            if(usuarioEncontrado == null){
                throw new IllegalArgumentException("Usuário não encontrado no sistema");
            }
            usuarioEncontrado.setName(dadosAtuallizacao.getName());
            usuarioEncontrado.setEmail(dadosAtuallizacao.getEmail());
            usuarioEncontrado.setPasswordHash(dadosAtuallizacao.getPasswordHash());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Usuário não encontrado");
        }

    }
}
