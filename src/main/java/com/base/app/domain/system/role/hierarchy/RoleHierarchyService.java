package com.base.app.domain.system.role.hierarchy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleHierarchyService {

    private final RoleHierarchyRepository roleHierarchyRepository;

    public String findAllHierarchy() {
        List<RoleHierarchy> roleHierarchyList = roleHierarchyRepository.findAll();

        Iterator<RoleHierarchy> itr = roleHierarchyList.iterator();
        StringBuffer concatedRoles = new StringBuffer();

        while (itr.hasNext()) {
            RoleHierarchy model = itr.next();
            if (model.getParentNm() != null) {
                concatedRoles.append(model.getParentNm().getChildNm());
                concatedRoles.append(" > ");
                concatedRoles.append(model.getChildNm());
                concatedRoles.append("\n");
            }
        }

        return concatedRoles.toString();
    }
}
