package org.example.quan_ly_diem.repository;

import org.example.quan_ly_diem.entity.QuanTriVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuanTriVienRepository extends JpaRepository<QuanTriVien, Long> {
    Optional<QuanTriVien> findByTenDangNhap(String tenDangNhap);
}
