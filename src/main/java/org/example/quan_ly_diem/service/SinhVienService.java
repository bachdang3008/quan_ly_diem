package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.entity.LopHanhChinh;
import org.example.quan_ly_diem.entity.SinhVien;
import org.example.quan_ly_diem.repository.LopHanhChinhRepository;
import org.example.quan_ly_diem.repository.SinhVienRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class SinhVienService {
    private final SinhVienRepository sinhVienRepository;
    private final LopHanhChinhRepository lopHanhChinhRepository;

    public SinhVienService(SinhVienRepository sinhVienRepository,
                           LopHanhChinhRepository lopHanhChinhRepository) {
        this.sinhVienRepository = sinhVienRepository;
        this.lopHanhChinhRepository = lopHanhChinhRepository;
    }

    public List<SinhVien> timKiem(String khoa, String lop, String tuKhoa) {
        return sinhVienRepository.filter(khoa, lop, tuKhoa);
    }

    public SinhVien layTheoMa(String maSv) {
        return sinhVienRepository.findById(maSv).orElseThrow();
    }

    public List<LopHanhChinh> layDanhSachLop() {
        return lopHanhChinhRepository.findAllByOrderByMaLopHcDesc();
    }

    public List<LopHanhChinh> layDanhSachLopTheoKhoa(String khoa) {
        List<LopHanhChinh> tatCa = layDanhSachLop();
        if (khoa == null || khoa.isBlank()) {
            return tatCa;
        }
        return tatCa.stream()
                .filter(lop -> lop.getMaLopHc() != null && lop.getMaLopHc().startsWith(khoa))
                .toList();
    }

    public Set<String> layDanhSachKhoaHoc() {
        Set<String> dsKhoa = new TreeSet<>(Comparator.reverseOrder());
        for (LopHanhChinh lop : layDanhSachLop()) {
            String maLop = lop.getMaLopHc();
            if (maLop != null && maLop.length() >= 2) {
                String khoa = maLop.substring(0, 2);
                if (khoa.matches("\\d+")) {
                    dsKhoa.add(khoa);
                }
            }
        }
        return dsKhoa;
    }

    public List<SinhVien> layTheoLop(String maLopHc) {
        return sinhVienRepository.findByLopHanhChinh_MaLopHcOrderByMaSvAsc(maLopHc);
    }
    @Transactional
    public void them(String maSv, String hoTen, LocalDate ngaySinh, String gioiTinh, String maLopHc) {
        SinhVien sinhVien = new SinhVien();
        sinhVien.setMaSv(maSv);
        sinhVien.setHoTen(hoTen);
        sinhVien.setNgaySinh(ngaySinh);
        sinhVien.setGioiTinh(gioiTinh);
        sinhVien.setLopHanhChinh(lopHanhChinhRepository.findById(maLopHc).orElseThrow());
        sinhVienRepository.save(sinhVien);
    }

    @Transactional
    public void capNhat(String maSv, String hoTen, LocalDate ngaySinh, String gioiTinh, String maLopHc) {
        SinhVien sinhVien = layTheoMa(maSv);
        sinhVien.setHoTen(hoTen);
        sinhVien.setNgaySinh(ngaySinh);
        sinhVien.setGioiTinh(gioiTinh);
        sinhVien.setLopHanhChinh(lopHanhChinhRepository.findById(maLopHc).orElseThrow());
        sinhVienRepository.save(sinhVien);
    }

    @Transactional
    public void xoa(String maSv) {
        sinhVienRepository.deleteById(maSv);
    }
}
