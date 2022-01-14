package rest;

import entities.*;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class BoatResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/devops_starter_war_exploded/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
        em.createNamedQuery("Auction.deleteAllRows").executeUpdate();
        em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
        em.createNamedQuery("User.deleteAllRows").executeUpdate();
        em.createNamedQuery("Role.deleteAllRows").executeUpdate();
        em.getTransaction().commit();

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
    }
    private static String securityToken;

    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        System.out.println("TOKEN ---> " + securityToken);
    }

    @Test
    public void testGetUserBoat() throws Exception {
        login("testuser", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .get("/boat/userboats").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("brand", hasItems("brand1"));
    }

    @Test
    public void testDeleteBoat() throws Exception {
        login("testadmin", "test");
        EntityManager em = emf.createEntityManager();
        TypedQuery query = em.createQuery("select b from Boat b", Boat.class);
        List<Boat> boats = query.getResultList();
        Long id = boats.get(0).getId();
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .delete("/boat/remove/" + id).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }


    @Test
    public void testCreateBoat() throws Exception {
        login("testuser", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken).body("{\"id\": 4,\"name\": \"test3\", \"brand\": \"brand3\", \"make\": \"make3\", \"year\": 2003, \"image\": \"URL1\"}")
                .post("/boat/addnewtoauc/1").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", Matchers.equalTo(2));
    }

}
