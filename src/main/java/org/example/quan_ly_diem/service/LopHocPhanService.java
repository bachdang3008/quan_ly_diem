package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.entity.*;
import org.example.quan_ly_diem.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
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
    @Transactional
    public void them(String maLopHienThi,
                     String maMon,
                     Long maHk,
                     boolean taoHocKyMoi,
                     String tenHkMoi,
                     Integer namBatDau,
                     String giangVien,
                     String phongHoc,
                     Integer tongSoBuoi) {
        HocKy hocKy = layHoacTaoHocKy(maHk, taoHocKyMoi, tenHkMoi, namBatDau);

        LopHocPhan lop = new LopHocPhan();
        lop.setMaLopHienThi(maLopHienThi);
        lop.setMonHoc(monHocRepository.findById(maMon).orElseThrow());
        lop.setHocKy(hocKy);
        lop.setGiangVienPhuTrach(giangVien);
        lop.setPhongHoc(phongHoc);
        lop.setTongSoBuoiHoc(tongSoBuoi);
        lopRepository.save(lop);
    }

    private HocKy layHoacTaoHocKy(Long maHk,
                                  boolean taoHocKyMoi,
                                  String tenHkMoi,
                                  Integer namBatDau) {
        if (!taoHocKyMoi) {
            if (maHk == null) throw new IllegalArgumentException("Vui lòng chọn học kỳ.");
            return hocKyRepository.findById(maHk).orElseThrow();
        }

        if (tenHkMoi == null || tenHkMoi.isBlank()) {
            throw new IllegalArgumentException("Vui lòng nhập tên học kỳ.");
        }

        int nam = namBatDau == null ? Year.now().getValue() : namBatDau;
        if (nam > Year.now().getValue()) {
            throw new IllegalArgumentException("Năm học không được lớn hơn năm hiện tại.");
        }

        String namHoc = nam + "-" + (nam + 1);
        return hocKyRepository.findByTenHkAndNamHoc(tenHkMoi, namHoc)
                .orElseGet(() -> {
                    HocKy hocKy = new HocKy();
                    hocKy.setTenHk(tenHkMoi);
                    hocKy.setNamHoc(namHoc);
                    hocKy.setTrangThai(true);
                    return hocKyRepository.save(hocKy);
                });
    }


    @Transactional
    public void capNhat(Long id,
                        String maLopHienThi,
                        String maMon,
                        Long maHk,
                        String giangVien,
                        String phongHoc,
                        Integer tongSoBuoi) {
        LopHocPhan lop = layTheoMa(id);
        lop.setMaLopHienThi(maLopHienThi);
        lop.setMonHoc(monHocRepository.findById(maMon).orElseThrow());
        lop.setHocKy(hocKyRepository.findById(maHk).orElseThrow());
        lop.setGiangVienPhuTrach(giangVien);
        lop.setPhongHoc(phongHoc);
        lop.setTongSoBuoiHoc(tongSoBuoi);
        lopRepository.save(lop);
    }

    @Transactional
    public void xoa(Long id) {
        vangNghiRepository.deleteByLopHocPhan_MaLopHp(id);
        bangDiemRepository.deleteByLopHocPhan_MaLopHp(id);
        lopRepository.deleteById(id);
    }
}
