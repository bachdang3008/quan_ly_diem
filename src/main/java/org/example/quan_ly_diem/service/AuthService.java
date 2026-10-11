package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.entity.QuanTriVien;
import org.example.quan_ly_diem.repository.QuanTriVienRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final QuanTriVienRepository repository;

    public AuthService(QuanTriVienRepository repository) {
        this.repository = repository;
    }

    public Optional<QuanTriVien> dangNhap(String username, String password) {
        return repository.findByTenDangNhap(username)
                .filter(admin -> admin.getMatKhau().equals(password));
    }

    public void taoAdminNeuChuaCo(String username, String password, String hoTen) {
        if (repository.findByTenDangNhap(username).isPresent()) {
            return;
        }

        QuanTriVien admin = new QuanTriVien();
        admin.setTenDangNhap(username);
        admin.setMatKhau(password);
        admin.setHoTen(hoTen);
        repository.save(admin);
    }
}
