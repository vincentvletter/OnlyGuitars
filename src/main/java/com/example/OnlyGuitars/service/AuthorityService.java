package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.dto.AuthorityOutputDto;
import com.example.OnlyGuitars.model.Authority;

public interface AuthorityService {
    public AuthorityOutputDto fromAuthority(Authority authority);
}
