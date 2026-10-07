package org.example.quan_ly_diem.repository;

import org.example.quan_ly_diem.entity.Khoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KhoaRepository extends JpaRepository<Khoa, String> {
    List<Khoa> findAllByOrderByTenKhoaAsc();
}