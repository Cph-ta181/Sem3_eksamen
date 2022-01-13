/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Auction;
import entities.Boat;
import entities.RenameMe;

import javax.persistence.OneToMany;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AuctionDTO {
    private long id;
    private String name;
    private Date date;
    private String location;
    private List<Boat> boats;

    public AuctionDTO() {
    }

    public AuctionDTO(long id, String name, Date date, String location, List<Boat> boats) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.boats = boats;
    }

    public AuctionDTO(Auction auction){
        this.id = auction.getId();
        this.name = auction.getName();
        this.date = auction.getDate();
        this.location = auction.getLocation();
    }


    public static List<AuctionDTO> getDTO(List<Auction> auctions){
        List<AuctionDTO> auctionDTO = new ArrayList();
        auctions.forEach(auction->auctionDTO.add(new AuctionDTO(auction)));
        return auctionDTO;
    }



}
