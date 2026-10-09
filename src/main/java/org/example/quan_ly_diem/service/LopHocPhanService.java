package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.entity.*;
import org.example.quan_ly_diem.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LopHocPhanService {
    private final LopHocPhanRepository lopRepository;
    private final MonHocRepository monHocRepository;
    private final HocKyRepository hocKyRepository;
    private final LopHanhChinhRepository lopHanhChinhRepository;
    private final SinhVienRepository sinhVienRepository;
    private final BangDiemRepository bangDiemRepository;
    private final ChiTietVangNghiRepository vangNghiRepository;

    public LopHocPhanService(LopHocPhanRepository lopRepository,
                             MonHocRepository monHocRepository,
                             HocKyRepository hocKyRepository,
                             LopHanhChinhRepository lopHanhChinhRepository,
                             SinhVienRepository sinhVienRepository,
                             BangDiemRepository bangDiemRepository,
                             ChiTietVangNghiRepository vangNghiRepository) {
        this.lopRepository = lopRepository;
        this.monHocRepository = monHocRepository;
        this.hocKyRepository = hocKyRepository;
        this.lopHanhChinhRepository = lopHanhChinhRepository;
        this.sinhVienRepository = sinhVienRepository;
        this.bangDiemRepository = bangDiemRepository;
        this.vangNghiRepository = vangNghiRepository;
    }

    public List<HocKy> layTatCaHocKy() {
        return hocKyRepository.findAllByOrderByNamHocDescTenHkAsc();
    }

    public Long chonHocKyMacDinh(Long maHk) {
        if (maHk != null) return maHk;
        List<HocKy> ds = layTatCaHocKy();
        return ds.isEmpty() ? null : ds.get(0).getMaHk();
    }

    public List<LopHocPhan> layDanhSachLop(Long maHk) {
        return maHk == null
                ? lopRepository.findAllByOrderByMaLopHpDesc()
                : lopRepository.findByHocKy_MaHkOrderByMaLopHpDesc(maHk);
    }

    public List<MonHoc> layTatCaMonHoc() {
        return monHocRepository.findAll();
    }

    public List<LopHanhChinh> layTatCaLopHanhChinh() {
        return lopHanhChinhRepository.findAllByOrderByMaLopHcDesc();
    }

    public List<SinhVien> laySinhVienTheoLopHanhChinh(String maLopHc) {
        if (maLopHc == null || maLopHc.isBlank()) return List.of();
        return sinhVienRepository.findByLopHanhChinh_MaLopHcOrderByMaSvAsc(maLopHc);
    }

    public LopHocPhan layTheoMa(Long id) {
        return lopRepository.findById(id).orElseThrow();
    }

    public SinhVien laySinhVien(String maSv) {
        return sinhVienRepository.findById(maSv).orElseThrow();
    }
}
