package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.entity.LopHanhChinh;
import org.example.quan_ly_diem.repository.KhoaRepository;
import org.example.quan_ly_diem.repository.LopHanhChinhRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LopHanhChinhService {
    private final LopHanhChinhRepository lopRepository;
    private final KhoaRepository khoaRepository;

    public LopHanhChinhService(LopHanhChinhRepository lopRepository, KhoaRepository khoaRepository) {
        this.lopRepository = lopRepository;
        this.khoaRepository = khoaRepository;
    }

    public List<LopHanhChinh> layTatCa() {
        return lopRepository.findAllByOrderByMaLopHcDesc();
    }
}
