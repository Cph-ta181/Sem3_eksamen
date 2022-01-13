/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Owner;

import java.util.ArrayList;
import java.util.List;

public class OwnerDTO {
    private String userName;
    private String name;
    private String phone;
    private String email;
    private List<BoatDTO> boatDTOs;



    public OwnerDTO(Owner owner){
        this.userName = owner.getUserName();
        this.name = owner.getName();
        this.phone = owner.getPhone();
        this.email = owner.getEmail();
        //this.boatDTOs = BoatDTO.getBoatDTO(owner.getBoats());
    }

    public static List<OwnerDTO> getOwnerDTO(List<Owner> owners){
        List<OwnerDTO> ownerDTO = new ArrayList();
        owners.forEach(owner->ownerDTO.add(new OwnerDTO(owner)));
        return ownerDTO;
    }


}
