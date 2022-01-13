package facades;

import dtos.AuctionDTO;
import dtos.RenameMeDTO;
import entities.Auction;
import entities.RenameMe;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class AuctionFacade {

    private static AuctionFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private AuctionFacade() {}
    

    public static AuctionFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AuctionFacade();
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
    
    //TODO Remove/Change this before use
    public long getRenameMeCount(){
        EntityManager em = getEntityManager();
        try{
            long renameMeCount = (long)em.createQuery("SELECT COUNT(r) FROM RenameMe r").getSingleResult();
            return renameMeCount;
        }finally{  
            em.close();
        }
    }
    
    public List<AuctionDTO> getAllAuctions(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Auction> query = em.createQuery("SELECT a FROM Auction a", Auction.class);
        List<Auction> auctions = query.getResultList();
        return AuctionDTO.getDTO(auctions);
    }

    public Auction findAuction(Long id){
        EntityManager em = emf.createEntityManager();
        Auction auction = em.find(Auction.class, id);
        return auction;
    }
    
    public static void main(String[] args) {
    }

}
