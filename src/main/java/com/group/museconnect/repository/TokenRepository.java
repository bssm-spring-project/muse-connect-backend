package com.group.museconnect.repository;

import com.group.museconnect.domain.user.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, String> {
}
