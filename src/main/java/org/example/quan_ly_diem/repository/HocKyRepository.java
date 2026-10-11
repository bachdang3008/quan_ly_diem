package org.example.quan_ly_diem.repository;

import org.example.quan_ly_diem.entity.HocKy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HocKyRepository extends JpaRepository<HocKy, Long> {
    List<HocKy> findAllByOrderByNamHocDescTenHkAsc();

    Optional<HocKy> findByTenHkAndNamHoc(String tenHk, String namHoc);
}