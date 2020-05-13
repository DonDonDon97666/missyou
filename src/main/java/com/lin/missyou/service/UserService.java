package com.lin.missyou.service;

import com.lin.missyou.model.User;
import com.lin.missyou.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/27
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(Long uid) {
        return userRepository.findUserById(uid);
    }
}
