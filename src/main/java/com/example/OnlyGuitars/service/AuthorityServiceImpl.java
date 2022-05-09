package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.dto.AuthorityOutputDto;
import com.example.OnlyGuitars.model.Authority;
import com.example.OnlyGuitars.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    AuthorityRepository authorityRepository;

    public AuthorityOutputDto fromAuthority(Authority authority) {
        AuthorityOutputDto authorityOutputDto = new AuthorityOutputDto();
        authorityOutputDto.autority = authority.getAuthority();
        return authorityOutputDto;
    }
}
