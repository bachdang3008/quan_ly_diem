package org.example.quan_ly_diem.repository;

import org.example.quan_ly_diem.entity.LopHocPhan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LopHocPhanRepository extends JpaRepository<LopHocPhan, Long> {
    List<LopHocPhan> findAllByOrderByMaLopHpDesc();

    List<LopHocPhan> findByHocKy_MaHkOrderByMaLopHpDesc(Long maHk);
}

