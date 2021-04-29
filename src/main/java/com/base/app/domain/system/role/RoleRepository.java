package com.base.app.domain.system.role;

import com.base.app.core.domain.base.CoreJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CoreJPAQueryDSLRepository<Role, Long> {
}
