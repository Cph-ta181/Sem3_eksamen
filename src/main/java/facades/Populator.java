/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Auction;
import entities.Boat;
import entities.Owner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import utils.EMF_Creator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em =  emf.createEntityManager();
        em.getTransaction().begin();
        Calendar cal = Calendar.getInstance();
        cal.set(2021, 0, 1, 14, 30, 00);
        Auction auction = new Auction("Auction1", cal.getTime(), "Danmark", new ArrayList<Boat>());
        Owner owner1 = new Owner("user", "Thomas", "88888888", "email@email.com", new ArrayList<Boat>());
        List<Owner> ownerlist = new ArrayList<>();
        ownerlist.add(owner1);
        Boat boat1 = new Boat(new Long(1), "Raceren", "Fiat", "Punto", 2003, "URL", ownerlist, auction);

        List<Boat> boatlist = new ArrayList<>();
        boatlist.add(boat1);

        auction.setBoats(boatlist);
        owner1.setBoats(boatlist);
        em.persist(auction);
        em.persist(owner1);
        em.persist(boat1);
        em.getTransaction().commit();
        em.close();
    }
    
    public static void main(String[] args) {
        populate();
    }
}
