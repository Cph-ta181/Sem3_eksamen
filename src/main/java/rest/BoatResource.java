package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDTO;
import entities.Boat;
import facades.BoatFacade;
import facades.OwnerFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

//Todo Remove or change relevant parts before ACTUAL use
@Path("boat")
public class BoatResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final BoatFacade FACADE =  BoatFacade.getFacade(EMF);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Context
    SecurityContext securityContext;

    @Path("/userboats")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("user")
    public String getAllOwnerBoats() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return gson.toJson(FACADE.getUserBoats(thisuser));
    }

    @Path("/addnewtoauc/{aucid}")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("user")
    public String addNewBoat(@PathParam("aucid") Long auctionId, String string) {
        Boat boat = gson.fromJson(string, Boat.class);
        String thisuser = securityContext.getUserPrincipal().getName();
        return gson.toJson(FACADE.createBoat(thisuser, boat, auctionId));
    }

    @Path("/remove/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public String addNewBoat(@PathParam("id") Long id) {
        return FACADE.deleteBoat(id);
    }

    @Path("/updateboat")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("user")
    public String updateBoat(String string){
        BoatDTO boatDTO = gson.fromJson(string, BoatDTO.class);
        return gson.toJson(FACADE.updateBoat(boatDTO));
    }

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public String getAllBoats() {
        return gson.toJson(FACADE.getAllBoats());
    }

}

