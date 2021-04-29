package com.base.app.domain.system.role.hierarchy;

import com.base.app.core.annotations.Comment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@ToString(exclude = {"parentNm", "roleHierarchy"})
@Table(name = "ROLE_HIERARCHY")
public class RoleHierarchy implements Serializable {

    @Id
    @Column(name = "ID", precision = 19, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "ID")
    private Long id;

    @Column(name = "CHILD_NM", length = 100)
    private String childNm;

    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_NM", referencedColumnName = "CHILD_NM")
    private RoleHierarchy parentNm;

    @OneToMany(mappedBy = "parentNm", cascade={CascadeType.ALL})
    private Set<RoleHierarchy> roleHierarchy = new HashSet<RoleHierarchy>();
}
