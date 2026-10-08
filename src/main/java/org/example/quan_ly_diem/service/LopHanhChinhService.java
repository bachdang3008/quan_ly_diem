package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.entity.Khoa;
import org.example.quan_ly_diem.entity.LopHanhChinh;
import org.example.quan_ly_diem.entity.SinhVien;
import org.example.quan_ly_diem.repository.KhoaRepository;
import org.example.quan_ly_diem.repository.LopHanhChinhRepository;
import org.example.quan_ly_diem.repository.SinhVienRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LopHanhChinhService {
    private final LopHanhChinhRepository lopRepository;
    private final KhoaRepository khoaRepository;
    private final SinhVienRepository sinhVienRepository;

    public LopHanhChinhService(LopHanhChinhRepository lopRepository, KhoaRepository khoaRepository, SinhVienRepository sinhVienRepository) {
        this.lopRepository = lopRepository;
        this.khoaRepository = khoaRepository;
        this.sinhVienRepository = sinhVienRepository;
    }

    public List<LopHanhChinh> layTatCa() {
        return lopRepository.findAllByOrderByMaLopHcDesc();
    }

    public List<Khoa> layTatCaKhoa() {
        return khoaRepository.findAllByOrderByTenKhoaAsc();
    }

    public LopHanhChinh layTheoMa(String maLopHc) {
        return lopRepository.findById(maLopHc).orElseThrow();
    }
    public List<SinhVien> laySinhVienTrongLop(String maLopHc) {
        return sinhVienRepository.findByLopHanhChinh_MaLopHcOrderByMaSvAsc(maLopHc);
    }

    @Transactional
    public void them(String maLopHc,
                     String tenLopHc,
                     String maKhoa,
                     String maKhoaMoi,
                     String tenKhoaMoi) {
        String maKhoaSuDung = maKhoa;

        if (maKhoaMoi != null && !maKhoaMoi.isBlank()
                && tenKhoaMoi != null && !tenKhoaMoi.isBlank()) {
            if (khoaRepository.existsById(maKhoaMoi)) {
                throw new IllegalArgumentException("Mã khoa mới đã tồn tại!");
            }

            Khoa khoa = new Khoa();
            khoa.setMaKhoa(maKhoaMoi);
            khoa.setTenKhoa(tenKhoaMoi);
            khoaRepository.save(khoa);
            maKhoaSuDung = maKhoaMoi;
        }

        if (maKhoaSuDung == null || maKhoaSuDung.isBlank()) {
            throw new IllegalArgumentException("Vui lòng chọn khoa.");
        }

        LopHanhChinh lop = new LopHanhChinh();
        lop.setMaLopHc(maLopHc);
        lop.setTenLopHc(tenLopHc);
        lop.setKhoa(khoaRepository.findById(maKhoaSuDung).orElseThrow());
        lopRepository.save(lop);
    }

    @Transactional
    public void capNhat(String maLopHc, String tenLopHc, String maKhoa) {
        LopHanhChinh lop = layTheoMa(maLopHc);
        lop.setTenLopHc(tenLopHc);
        lop.setKhoa(khoaRepository.findById(maKhoa).orElseThrow());
        lopRepository.save(lop);
    }

    @Transactional
    public void xoa(String maLopHc) {
        lopRepository.deleteById(maLopHc);
    }

}
