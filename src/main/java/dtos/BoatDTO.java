/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Boat;
import entities.Owner;

import java.util.ArrayList;
import java.util.List;

public class BoatDTO {
    private Long id;
    private String name;
    private String brand;
    private String make;
    private int year;
    private String image;
    private List<OwnerDTO> ownerDTOs;
    private AuctionDTO auctionDTO;

    public BoatDTO(Boat boat) {
        this.id = boat.getId();
        this.name = boat.getName();
        this.brand = boat.getBrand();
        this.make = boat.getMake();
        this.year = boat.getYear();
        this.image = boat.getImage();
        //this.ownerDTOs = OwnerDTO.getOwnerDTO(boat.getOwners());
        //this.auctionDTO = new AuctionDTO(auction.getAuction());
    }


    public static List<BoatDTO> getBoatDTO(List<Boat> boats){
        List<BoatDTO> boatDTO = new ArrayList<>();
        boats.forEach(boat->boatDTO.add(new BoatDTO(boat)));
        return boatDTO;
    }

}
