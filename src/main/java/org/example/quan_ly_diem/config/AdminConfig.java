package org.example.quan_ly_diem.config;

import org.example.quan_ly_diem.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfig {

    @Bean
    CommandLineRunner taoTaiKhoanAdminMacDinh(AuthService authService) {
        return args -> authService.taoAdminNeuChuaCo(
                "admin",
                "admin123",
                "Quản trị viên"
        );
    }
}