package com.base.app.domain.system.resource;

import com.base.app.core.domain.base.CoreJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourcesRepository extends CoreJPAQueryDSLRepository<Resources, Long> {
}
