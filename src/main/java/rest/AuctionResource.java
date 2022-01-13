package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.AuctionFacade;
import facades.FacadeExample;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("auction")
public class AuctionResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final AuctionFacade FACADE =  AuctionFacade.getFacade(EMF);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllAuctions() {
        return gson.toJson(FACADE.getAllAuctions());
    }
}
