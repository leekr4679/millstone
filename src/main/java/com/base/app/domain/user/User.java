package com.base.app.domain.user;

import com.base.app.core.annotations.Comment;
import com.base.app.core.code.Types;
import com.base.app.domain.BaseJpaModel;
import com.base.app.domain.system.role.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@DynamicUpdate
@DynamicInsert
@ToString(exclude = {"userRoles"})
@Table(name = "USER")
@Comment(value = "유저정보")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User extends BaseJpaModel<String> {

    @Id
    @Column(name = "USER_ID", length = 50, nullable = false)
    @Comment(value = "사용자 아이디")
    private String userId;

    @Column(name = "PASSWORD", length = 128, nullable = false)
    @Comment(value = "비밀번호")
    private String password;

    @Column(name = "USER_NM", length = 30)
    @Comment(value = "사용자명")
    private String userNm;

    @Column(name = "EMAIL", length = 50)
    @Comment(value = "이메일")
    private String email;

    @Column(name = "HP_NO", length = 15)
    @Comment(value = "휴대폰")
    private String hpNo;

    @Column(name = "LAST_LOGIN_DATE")
    @Comment(value = "마지막로그인일시")
    private Instant lastLoginDate;

    @Column(name = "PASSWORD_UPDATE_DATE")
    @Comment(value = "비밀번호변경일시")
    private Instant passwordUpdateDate;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    @Enumerated
    private Types.Used useYn = Types.Used.YES;

    @Column(name = "DEL_YN", length = 1)
    @Comment(value = "삭제여부")
    @Enumerated
    private Types.Deleted delYn = Types.Deleted.NO;

    @ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    @JoinTable(name = "USER_ROLES", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID") })
    private Set<Role> userRoles = new HashSet<>();

    @Override
    public String getId() {
        return userId;
    }
}
