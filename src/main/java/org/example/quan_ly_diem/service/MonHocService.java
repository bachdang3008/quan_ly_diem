package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.entity.MonHoc;
import org.example.quan_ly_diem.repository.MonHocRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MonHocService {
    private final MonHocRepository repository;

    public MonHocService(MonHocRepository repository) {
        this.repository = repository;
    }

    public List<MonHoc> layTatCa() {
        return repository.findAll();
    }

    public MonHoc layTheoMa(String maMon) {
        return repository.findById(maMon).orElseThrow();
    }
}
