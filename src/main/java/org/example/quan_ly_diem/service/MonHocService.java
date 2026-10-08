package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.entity.MonHoc;
import org.example.quan_ly_diem.repository.MonHocRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void them(String maMon, String tenMon, Integer soTinChi) {
        MonHoc monHoc = new MonHoc();
        monHoc.setMaMon(maMon);
        monHoc.setTenMon(tenMon);
        monHoc.setSoTinChi(soTinChi);
        repository.save(monHoc);
    }

    @Transactional
    public void capNhat(String maMon, String tenMon, Integer soTinChi) {
        MonHoc monHoc = layTheoMa(maMon);
        monHoc.setTenMon(tenMon);
        monHoc.setSoTinChi(soTinChi);
        repository.save(monHoc);
    }

    @Transactional
    public void xoa(String maMon) {
        repository.deleteById(maMon);
    }
}
