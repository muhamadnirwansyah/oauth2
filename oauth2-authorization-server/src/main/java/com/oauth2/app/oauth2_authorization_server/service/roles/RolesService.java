package com.oauth2.app.oauth2_authorization_server.service.roles;

import com.oauth2.app.oauth2_authorization_server.entity.Roles;
import com.oauth2.app.oauth2_authorization_server.model.RolesRequest;
import com.oauth2.app.oauth2_authorization_server.model.RolesResponse;
import com.oauth2.app.oauth2_authorization_server.repository.AccountRepository;
import com.oauth2.app.oauth2_authorization_server.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolesService {

    private final RolesRepository rolesRepository;
    private final AccountRepository accountRepository;

    public List<RolesResponse> fetchRoles(){
        log.info("process fetchRoles..");
        return rolesRepository.findAll()
                .stream().map(roles -> RolesResponse.builder().id(roles.getId())
                        .name(roles.getName()).build()).toList();
    }

    public RolesResponse delete(Long id){
        log.info("process delete roles with id : {}",id);
        if (Objects.isNull(id)){
            throw new IllegalArgumentException("Role ID cannot be empty");
        }
        if (accountRepository.getAccountByRoleId(id).isPresent()){
            throw new IllegalArgumentException("Role ID is already in user !");
        }
        var currentRoles = rolesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role ID "+id+" is not found !"));
        RolesResponse response = new RolesResponse();
        response.setId(currentRoles.getId());
        response.setName(currentRoles.getName());
        rolesRepository.delete(currentRoles);
        return response;
    }

    public RolesResponse save(RolesRequest request){
        if (Objects.isNull(request.getId())){
            log.info("process save roles..");
            if (Objects.isNull(request.getName()) || request.getName().trim().isEmpty()){
                throw new IllegalArgumentException("Role name cannot be empty");
            }
            if (rolesRepository.findByName(request.getName()).isPresent()){
                throw new IllegalStateException("Role name already exists");
            }
            Roles roles = Roles.builder()
                    .name(request.getName())
                    .build();
            var rolesResponse = rolesRepository.save(roles);
            return RolesResponse.builder()
                    .id(rolesResponse.getId())
                    .name(rolesResponse.getName())
                    .build();
        }
        return update(request);
    }

    public RolesResponse update(RolesRequest request){
        log.info("process update roles..");
        var currentRoles = rolesRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Roles ID not found "+request.getId()));
        currentRoles.setName(request.getName());
        rolesRepository.save(currentRoles);
        return RolesResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .build();
    }
}
