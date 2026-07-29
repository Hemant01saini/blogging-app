//package com.blogapp.controller;
//
//import com.blogapp.dto.response.UserResponseDto;
//import com.blogapp.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
////import org.springframework.boot.test.context.TestConfiguration;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Import;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//@WebMvcTest(UserController.class)
//@AutoConfigureMockMvc(addFilters = false)
////@Import(UserControllerTest.TestSecurityConfig.class)
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private UserService userService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void shouldReturnAllUsers() throws Exception {
//
//        UserResponseDto user = UserResponseDto.builder()
//                .id(1L)
//                .displayName("Hemant")
//                .username("hemant")
//                .email("hemant@gmail.com")
//                .build();
//
//        List<UserResponseDto> users = List.of(user);
//
//        when(userService.getAllUsers())
//                .thenReturn(users);
//
//        mockMvc.perform(get("/api/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].displayName")
//                        .value("Hemant"))
//                .andExpect(jsonPath("$[0].email")
//                        .value("hemant@gmail.com"));
//
//        verify(userService)
//                .getAllUsers();
//    }
//
//
////    @TestConfiguration
////    static class TestSecurityConfig {
////
////        @Bean
////        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////
////            http
////                    .csrf(csrf -> csrf.disable())
////                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
////
////            return http.build();
////        }
////    }
//
//}
