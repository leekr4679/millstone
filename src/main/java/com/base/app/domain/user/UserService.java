package com.base.app.domain.user;

import com.base.app.domain.BaseService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService extends BaseService<User, String> {

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public List<User> list() {
        List<User> list = select().from(qUser)
                .leftJoin(qUser.userRoles, qRole).fetchJoin()
                .fetch();

        return list;
    }

    public User findByUserId(String userId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.userId.eq(userId));

        User user = select().from(qUser)
                .leftJoin(qUser.userRoles, qRole)
                .where(builder).fetchOne();

        return user;
    }

    @Transactional
    public void saveUser(User user) {
        User find = findByUserId(user.getUserId());

        if (find == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            save(user);
        }
    }
}
