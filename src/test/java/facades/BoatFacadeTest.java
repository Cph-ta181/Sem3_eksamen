package facades;

import dtos.BoatDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class BoatFacadeTest {

    private static EntityManagerFactory emf;
    private static BoatFacade facade;

    public BoatFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = BoatFacade.getFacade(emf);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
        em.createNamedQuery("Auction.deleteAllRows").executeUpdate();
        em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
        em.createNamedQuery("User.deleteAllRows").executeUpdate();
        em.createNamedQuery("Role.deleteAllRows").executeUpdate();
        em.getTransaction().commit();
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            //set up test users
            Role userRole = new Role("user");
            User user1 = new User("testuser","test");
            user1.addRole(userRole);

            Role adminRole = new Role("admin");
            User admin1 = new User("testadmin", "test");
            admin1.addRole(adminRole);


            //setup boats, owner and auction
            Calendar cal = Calendar.getInstance();
            cal.set(2020, 9, 10, 12, 00, 00);
            Auction auction = new Auction("Auction1", cal.getTime(), "location1", new ArrayList<Boat>());
            Owner owner1 = new Owner("testuser", "user", "88888888", "user@email.com", new ArrayList<Boat>());
            List<Owner> ownerlist = new ArrayList<>();
            ownerlist.add(owner1);
            Boat boat1 = new Boat(new Long(1), "name1", "brand1", "make1", 2000, "URL1", ownerlist, auction);

            List<Boat> boatlist = new ArrayList<>();
            boatlist.add(boat1);

            auction.setBoats(boatlist);
            owner1.setBoats(boatlist);


            em.getTransaction().begin();
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user1);
            em.persist(admin1);
            //em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
            em.persist(auction);
            em.persist(owner1);
            em.persist(boat1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
        em.createNamedQuery("Auction.deleteAllRows").executeUpdate();
        em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
        em.createNamedQuery("User.deleteAllRows").executeUpdate();
        em.createNamedQuery("Role.deleteAllRows").executeUpdate();
        em.getTransaction().commit();
    }
    @Test
    public void testGetAllBoats(){
        assertEquals(1, facade.getAllBoats().size(), "Expects one row in the database");
    }

    @Test
    public void testCreateBoat() throws Exception {
        facade.createBoat("testuser", new Boat("Raceren", "Fiat", "Punto", 2003, "URL"), new Long(1));
        assertEquals(2, facade.getAllBoats().size(), "Expects two rows in the database");
    }

    @Test
    public void testDeleteBoat() throws Exception {
        facade.createBoat("testuser", new Boat("Raceren", "Fiat", "Punto", 2003, "URL"), new Long(1));
        Long id = facade.getAllBoats().get(0).getId();

        facade.deleteBoat(id);
        assertEquals(1, facade.getAllBoats().size(), "Expects one rows in the database");
    }

    @Test
    public void testGetUserBoats() throws Exception {
        facade.createBoat("testuser", new Boat("Raceren", "Fiat", "Punto", 2003, "URL"), new Long(1));
        facade.createBoat("testuser", new Boat("Raceren", "Fiat", "Punto", 2003, "URL"), new Long(1));
        assertEquals(3, facade.getUserBoats("testuser").size(), "Expects three rows in the database");
    }
    

}
