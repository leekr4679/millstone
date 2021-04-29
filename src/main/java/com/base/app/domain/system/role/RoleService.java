package com.base.app.domain.system.role;

import com.base.app.domain.BaseService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService extends BaseService<Role, Long> {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
    }

    public List<Role> list() {
        return findAll();
    }

    public Role getRole(Long id) {
        return findOne(id);
    }

    public Role findByRoleCd(String roleCd) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qRole.roleCd.eq(roleCd));

        Role role = select().from(qRole)
                .where(builder).fetchOne();

        return role;
    }

    @Transactional
    public void saveRole(Role role) {
        save(role);
    }
}
