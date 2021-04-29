package com.base.app.domain.system.role;

import com.base.app.core.annotations.Comment;
import com.base.app.domain.BaseJpaModel;
import com.base.app.domain.system.resource.Resources;
import com.base.app.domain.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Entity
@ToString(exclude = {"users", "resourcesSet"})
@Table(name = "ROLE")
@Comment(value = "권한")
public class Role extends BaseJpaModel<Long> {

    @Id
    @Column(name = "ROLE_ID", precision = 19, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "롤 아이디")
    private Long id;

    @Column(name = "ROLE_CD", length = 100, nullable = false)
    @Comment(value = "롤 코드")
    private String roleCd;

    @Column(name = "ROLE_DESC", length = 100)
    @Comment(value = "롤 설명")
    private String roleDesc;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userRoles")
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roleSet")
    @OrderBy("sort desc")
    private Set<Resources> resourcesSet = new LinkedHashSet<>();
}
