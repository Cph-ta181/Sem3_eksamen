package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.FacadeExample;
import facades.OwnerFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("owner")
public class OwnerResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final OwnerFacade FACADE =  OwnerFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllOwners() {
        return GSON.toJson(FACADE.getAllOwners());
    }
    @Path("/owning/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllOwners(@PathParam("id") Long id) {
        return GSON.toJson(FACADE.getAllBoatOwners(id));
    }

}
