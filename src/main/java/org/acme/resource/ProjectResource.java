package org.acme.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;
import org.acme.dto.request.CreateProjectDTO;
import org.acme.entity.ProjectEntity;
import org.acme.service.ProjectService;
import org.jboss.resteasy.reactive.RestResponse;

@AllArgsConstructor
@ApplicationScoped
@Path("/project")
public class ProjectResource {
    final ProjectService projectService;

    @POST
    public RestResponse<?> createNewProject(CreateProjectDTO dadosCriar){
        try{
            ProjectEntity projetoCriado = projectService.criarNovoProjeto(dadosCriar);
            return RestResponse.status(RestResponse.Status.fromStatusCode(201),projetoCriado);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


}
