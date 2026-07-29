//package com.blogapp.controller;
//
//import com.blogapp.entity.User;
//import com.blogapp.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//  only use for to encode password before implementing the Bcrypt password once use
//@RestController
//@RequestMapping("/api/admin")
//@RequiredArgsConstructor
//public class PasswordMigrationController {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @PostMapping("/migrate-passwords")
//    public String migratePasswords() {
//
//        Map<String, String> users = Map.of(
//                "hemant@gmail.com","123456",
//                "Ashish12@gmail.com","987654",
//                "rohan01@gmail.com","password123",
//                "manyasingh@gmail.com","manya123",
//                "singhdashrath@gmail.com","Dasrath123"
//        );
//
//        for (Map.Entry<String, String> entry : users.entrySet()) {
//
//            User user = userRepository
//                    .findByEmail((entry.getKey()))
//                    .orElse(null);
//
//            if (user != null) {
//                user.setPassword(
//                        passwordEncoder.encode(entry.getValue())
//                );
//                userRepository.save(user);
//            }
//        }
//        return "Passwords migrated successfully";
//    }
//}
