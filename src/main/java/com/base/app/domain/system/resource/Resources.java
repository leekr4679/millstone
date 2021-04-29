package com.base.app.domain.system.resource;

import com.base.app.core.annotations.Comment;
import com.base.app.domain.BaseJpaModel;
import com.base.app.domain.system.role.Role;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Entity
@ToString(exclude = {"roleSet"})
@Table(name = "RESOURCES")
@Comment(value = "유저정보")
public class Resources extends BaseJpaModel<Long> {

    @Id
    @Column(name = "RESOURCE_ID", precision = 19, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "리소스 아이디")
    private Long id;

    @Column(name = "RESOURCE_NM", length = 100, nullable = false)
    @Comment(value = "리소스명")
    private String resourceNm;

    @Column(name = "HTTP_METHOD", length = 100)
    @Comment(value = "메소드")
    private String httpMethod;

    @Column(name = "SORT")
    @Comment(value = "순번")
    private int sort;

    @Column(name = "RESOURCE_TYPE", length = 100)
    @Comment(value = "타입")
    private String resourceType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ROLE_RESOURCES", joinColumns = {
            @JoinColumn(name = "RESOURCE_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roleSet = new HashSet<>();
}
