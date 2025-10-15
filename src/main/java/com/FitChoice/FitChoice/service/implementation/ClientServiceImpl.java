package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.ClientDto;
import com.FitChoice.FitChoice.model.dto.MembershipDto;
import com.FitChoice.FitChoice.model.entity.Client;
import com.FitChoice.FitChoice.model.entity.Membership;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import com.FitChoice.FitChoice.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    MembershipRepository membershipRepo;

   public Client toEntity (ClientDto dto){
       Client client = new Client();

       client.setUserName(dto.getUserName());
       client.setEmail(dto.getEmail());

       if (dto.getMemberships() != null && !dto.getMemberships().isEmpty()){
           List<Membership> memberships = dto.getMemberships().stream()
                   .filter(n -> n != null && !n.isBlank())
                   .map( name -> membershipRepo.findMembershipByName(name)
                           .orElseGet(() -> {
                               Membership m = new Membership();
                               try {
                                   m.setType(MembershipType.valueOf(name.toUpperCase()));
                               } catch (IllegalArgumentException e) {
                                   m.setType(MembershipType.FULLFITNESS);
                               }
                               return membershipRepo.save(m);
                           }))
                   .collect(Collectors.toList());
           client.setMemberships(memberships);
           memberships.forEach(m -> m.setClient(client));
       }
       return client;
   }

   public ClientDto toDto (Client client){

       ClientDto dto = new ClientDto();
       dto.setUserName(client.getUserName());
       dto.setEmail(client.getEmail());

       if(client.getMemberships() != null && !client.getMemberships().isEmpty()){
          List<String> membershipsName = client.getMemberships().stream()
                  .map(m -> m.getType() != null ? m.getType().name() : "UNKNOWN")
                  .toList();
          dto.setMemberships(membershipsName);
       }
       return dto;
   }
}
