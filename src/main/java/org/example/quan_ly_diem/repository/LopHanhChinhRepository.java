package org.example.quan_ly_diem.repository;
import org.example.quan_ly_diem.entity.LopHanhChinh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LopHanhChinhRepository extends JpaRepository<LopHanhChinh, String> {
    List<LopHanhChinh> findAllByOrderByMaLopHcDesc();
}
