package org.example.quan_ly_diem.repository;

import org.example.quan_ly_diem.entity.ChiTietVangNghi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietVangNghiRepository extends JpaRepository<ChiTietVangNghi, Long> {
    List<ChiTietVangNghi> findByLopHocPhan_MaLopHpAndSinhVien_MaSvOrderByNgayVangDesc(Long id, String maSv);

    long countByLopHocPhan_MaLopHpAndSinhVien_MaSv(Long id, String maSv);

    void deleteByLopHocPhan_MaLopHp(Long id);
}

