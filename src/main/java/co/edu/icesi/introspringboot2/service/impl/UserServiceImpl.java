package co.edu.icesi.introspringboot2.service.impl;

import co.edu.icesi.introspringboot2.entity.User;
import co.edu.icesi.introspringboot2.repository.UserRepository;
import co.edu.icesi.introspringboot2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(String username) {
        return userRepository.findByEmail(username).orElseThrow();
    }
}
