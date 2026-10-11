package org.example.quan_ly_diem.repository;

import org.example.quan_ly_diem.entity.BangDiem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BangDiemRepository extends JpaRepository<BangDiem, Long> {
    List<BangDiem> findByLopHocPhan_MaLopHpOrderBySinhVien_MaSvAsc(Long id);

    List<BangDiem> findBySinhVien_MaSvOrderByLopHocPhan_HocKy_MaHkAsc(String maSv);

    Optional<BangDiem> findByLopHocPhan_MaLopHpAndSinhVien_MaSv(Long id, String maSv);

    boolean existsByLopHocPhan_MaLopHpAndSinhVien_MaSv(Long id, String maSv);

    void deleteByLopHocPhan_MaLopHp(Long id);
}
