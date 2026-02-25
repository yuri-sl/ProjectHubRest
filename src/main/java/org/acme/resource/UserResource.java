package org.acme.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.acme.dto.request.CreateNewUserDTORequest;
import org.acme.dto.response.Users.FetchUserInfos;
import org.acme.entity.UserEntity;
import org.acme.service.UserService;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@AllArgsConstructor
@ApplicationScoped
@Path("/users")
public class UserResource {
    private final UserService userService;

    @GET
    public RestResponse<?> fetchAllUsers(){
        try{
            List<FetchUserInfos> listaUsuarios = userService.buscarTodosUsuarios();
            return RestResponse.status(Response.Status.OK,listaUsuarios);
        } catch (RuntimeException e) {
            return RestResponse.status(RestResponse.Status.BAD_REQUEST);
        }
    }

    @POST
    public RestResponse<?> createNewUser(CreateNewUserDTORequest dados) {
        try {
            FetchUserInfos resposta = userService.criarNovoUsuario(dados);
            return RestResponse.status(RestResponse.Status.ACCEPTED, resposta);
        } catch (IllegalArgumentException e){
            return RestResponse.status(RestResponse.Status.BAD_REQUEST,e.getMessage());
        }catch (RuntimeException e) {
            return RestResponse.status(RestResponse.Status.CONFLICT,e.getMessage());
        }
    }

    @Path("/{username}")
    @GET
    public RestResponse<?> fetchUserById(@PathParam("username") String username){
        try{
            List<FetchUserInfos> listaUsuarios = userService.buscarUsuarioPorNome(username);
            return RestResponse.status(Response.Status.ACCEPTED,listaUsuarios);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.BAD_REQUEST,e.getMessage());
        }


    }
}
