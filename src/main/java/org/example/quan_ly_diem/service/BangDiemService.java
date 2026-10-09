package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.entity.BangDiem;
import org.example.quan_ly_diem.entity.SinhVien;
import org.example.quan_ly_diem.repository.BangDiemRepository;
import org.example.quan_ly_diem.repository.LopHocPhanRepository;
import org.example.quan_ly_diem.repository.SinhVienRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BangDiemService {
    private final BangDiemRepository bangDiemRepository;
    private final LopHocPhanRepository lopHocPhanRepository;
    private final SinhVienRepository sinhVienRepository;

    public BangDiemService(BangDiemRepository b, LopHocPhanRepository l, SinhVienRepository s) {
        this.bangDiemRepository = b;
        this.lopHocPhanRepository = l;
        this.sinhVienRepository = s;
    }
    public List<BangDiem> danhSachLop(Long maLopHp) {
        return bangDiemRepository.findByLopHocPhan_MaLopHpOrderBySinhVien_MaSvAsc(maLopHp);
    }

    public List<BangDiem> bangDiemCaNhan(String maSv) {
        return bangDiemRepository.findBySinhVien_MaSvOrderByLopHocPhan_HocKy_MaHkAsc(maSv);
    }

    public boolean daTonTai(Long maLopHp, String maSv) {
        return bangDiemRepository.existsByLopHocPhan_MaLopHpAndSinhVien_MaSv(maLopHp, maSv);
    }
    public List<String> danhSachMaSinhVienDaCo(Long maLopHp, List<SinhVien> dsSinhVien) {
        return dsSinhVien.stream()
                .filter(sv -> daTonTai(maLopHp, sv.getMaSv()))
                .map(SinhVien::getMaSv)
                .toList();
    }

    @Transactional
    public boolean themSinhVien(Long maLopHp, String maSv) {
        if (daTonTai(maLopHp, maSv)) return false;
        BangDiem bd = new BangDiem();
        bd.setLopHocPhan(lopHocPhanRepository.findById(maLopHp).orElseThrow());
        bd.setSinhVien(sinhVienRepository.findById(maSv).orElseThrow());
        bd.setDiemChuyenCan(0.0);
        bd.setDiemGiuaKy(0.0);
        bd.setDiemCuoiKy(0.0);
        bd.setSoBuoiVang(0);
        bangDiemRepository.save(bd);
        return true;
    }

    @Transactional
    public void themNhieuSinhVien(Long maLopHp, List<String> dsMaSv) {
        if (dsMaSv == null) return;
        for (String maSv : dsMaSv) {
            themSinhVien(maLopHp, maSv);
        }
    }

    @Transactional
    public void xoaKhoiLop(Long id) {
        bangDiemRepository.deleteById(id);
    }
}
