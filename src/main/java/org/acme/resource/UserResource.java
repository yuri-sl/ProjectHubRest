package org.acme.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.acme.dto.request.CreateNewUserDTORequest;
import org.acme.dto.response.Users.FetchUserInfos;
import org.acme.entity.UserEntity;
import org.acme.service.UserService;
import org.hibernate.engine.spi.Status;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@AllArgsConstructor
@ApplicationScoped
@Path("/users")
public class UserResource {
    private final UserService userService;

    @Path("/listall")
    @GET
    public RestResponse<?> fetchAllUsers(){
        try{
            List<FetchUserInfos> listaUsuarios = userService.buscarTodosUsuarios();
            return RestResponse.status(Response.Status.OK,listaUsuarios);
        } catch (RuntimeException e) {
            return RestResponse.status(RestResponse.Status.BAD_REQUEST);
        }
    }

    @Path("/{userId}")
    @GET()
    public RestResponse<?> getUserById(@PathParam("userId") long userId){
        try{
            FetchUserInfos dados = userService.listarUsuarioPorId(userId);
            return  RestResponse.status(Response.Status.ACCEPTED,dados);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    @DELETE
    @Path("/{userId}")
    public RestResponse<?> deleteUserById(@PathParam("userId") long userId){
        try{
            userService.deletarUsuarioPorId(userId);
            return RestResponse.status(RestResponse.Status.fromStatusCode(204));
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.BAD_REQUEST,e.getMessage());
        }
    }

    @PUT
    @Path("/{userId}")
    public RestResponse<?> updateUserById(@PathParam("userId") long userId, CreateNewUserDTORequest dados){
        try{
            userService.updateUserById(userId,dados);
            return  RestResponse.status(RestResponse.Status.fromStatusCode(204));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
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

    @GET
    public RestResponse<?> fetchUserByName(@QueryParam("username") String username){
        try{
            List<FetchUserInfos> listaUsuarios = userService.buscarUsuarioPorNome(username);
            return RestResponse.status(Response.Status.ACCEPTED,listaUsuarios);
        } catch (IllegalArgumentException e) {
            return RestResponse.status(Response.Status.BAD_REQUEST,e.getMessage());
        }
    }
}
