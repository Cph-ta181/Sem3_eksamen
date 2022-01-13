package facades;

import dtos.BoatDTO;
import dtos.OwnerDTO;
import dtos.RenameMeDTO;
import entities.Auction;
import entities.Boat;
import entities.Owner;
import entities.RenameMe;
import errorhandling.NotFoundException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import java.util.List;

public class BoatFacade {

    private static BoatFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private BoatFacade() {}
    

    public static BoatFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BoatFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public BoatDTO createBoat(String userName, Boat boat, Long auctionId){
        Owner owner = OwnerFacade.getFacade(emf).findOwner(userName);
        boat.addOwner(owner);
        Auction auction = AuctionFacade.getFacade(emf).findAuction(auctionId);
        boat.setAuction(auction);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BoatDTO(boat);
    }

    public String deleteBoat(Long id){
        EntityManager em = getEntityManager();
        try{
            Boat boat = em.find(Boat.class, id);
            if (boat == null){
                throw new NotFoundException("Boat with id: " + id + " was not found.");
            }
            else{
                em.getTransaction().begin();
                em.remove(boat);
                em.getTransaction().commit();
                return "Boat with id: " + id + " was successfully removed!";
            }
        } catch (NotFoundException e){
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }


    public RenameMeDTO findBoat(long id) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        RenameMe rm = em.find(RenameMe.class, id);
//        if (rm == null)
//            throw new RenameMeNotFoundException("The RenameMe entity with ID: "+id+" Was not found");
        return new RenameMeDTO(rm);
    }


    
    public List<BoatDTO> getUserBoats(String name){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b join Owner o on o.userName = :name", Boat.class);
            query.setParameter("name", name);
            List<Boat> boats = query.getResultList();
            if (boats.size() == 0){
                throw new NotFoundException("You do not currently own a boat registered on our site.");
            }
            return BoatDTO.getBoatDTO(boats);
        }
        catch (NotFoundException e){
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        BoatFacade fe = getFacade(emf);
    }

}
