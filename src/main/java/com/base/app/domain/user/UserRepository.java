package com.base.app.domain.user;

import com.base.app.core.domain.base.CoreJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CoreJPAQueryDSLRepository<User, String> {
}
