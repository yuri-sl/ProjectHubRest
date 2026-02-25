package org.acme.dto.response.Users;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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


    public static FetchUserInfos mapearEntidadeDTO(UserEntity userEntity){
        FetchUserInfos fetchUserInfos = FetchUserInfos.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .createdAt(userEntity.getCreatedAt())
                .build();
        return fetchUserInfos;
    }

    public static List<FetchUserInfos> mapearListaEntidadeDTO(List<UserEntity> usuariosBanco){
        List<FetchUserInfos> listaUsuariosFetched = new ArrayList<>();
        for(UserEntity u: usuariosBanco){
            FetchUserInfos fetchedUser = FetchUserInfos.builder()
                    .id(u.getId()).name(u.getName()).email(u.getEmail()).createdAt(u.getCreatedAt()).build();
            listaUsuariosFetched.add(fetchedUser);
        }
        return listaUsuariosFetched;
    }
}
