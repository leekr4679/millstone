package com.base.app.domain.system.resource;

import com.base.app.domain.BaseService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResourcesService extends BaseService<Resources, Long> {

    private ResourcesRepository resourcesRepository;

    public ResourcesService(ResourcesRepository resourcesRepository) {
        super(resourcesRepository);
        this.resourcesRepository = resourcesRepository;
    }

    public List<Resources> list(String resurceType) {
        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(resurceType)) {
            builder.and(qResources.resourceType.eq(resurceType));
        }

        List<Resources> list = select().from(qResources)
                .leftJoin(qResources.roleSet, qRole).fetchJoin()
                .where(builder).fetch();

        return list;
    }

    public Resources get(Long id) {
        return findOne(id);
    }

    @Transactional
    public void saveResources(Resources resources) {
        save(resources);
    }
}
