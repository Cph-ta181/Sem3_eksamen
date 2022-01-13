package facades;

import dtos.OwnerDTO;
import dtos.RenameMeDTO;
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

public class OwnerFacade {

    private static OwnerFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private OwnerFacade() {}
    

    public static OwnerFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new OwnerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public RenameMeDTO create(RenameMeDTO rm){
        RenameMe rme = new RenameMe(rm.getDummyStr1(), rm.getDummyStr2());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rme);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new RenameMeDTO(rme);
    }
    public RenameMeDTO getById(long id) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        RenameMe rm = em.find(RenameMe.class, id);
//        if (rm == null)
//            throw new RenameMeNotFoundException("The RenameMe entity with ID: "+id+" Was not found");
        return new RenameMeDTO(rm);
    }


    
    public List<OwnerDTO> getAllOwners(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o", Owner.class);
        List<Owner> owners = query.getResultList();
        return OwnerDTO.getOwnerDTO(owners);
    }

    public List<OwnerDTO> getAllBoatOwners(Long id){
        EntityManager em = emf.createEntityManager();
        Boat boat = em.find(Boat.class, id);
        return OwnerDTO.getOwnerDTO(boat.getOwners());
    }

    public Owner findOwner(String userName){
        EntityManager em = emf.createEntityManager();
        try {
            Owner owner = em.find(Owner.class, userName);
            if (owner != null){
                return owner;
            }
            else {
                throw new NotFoundException("User was not found in database");
            }
        } catch (NotFoundException e){
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        OwnerFacade fe = getFacade(emf);
        fe.getAllOwners().forEach(dto->System.out.println(dto));
    }

}
