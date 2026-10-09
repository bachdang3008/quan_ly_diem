package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.entity.BangDiem;
import org.example.quan_ly_diem.entity.SinhVien;
import org.example.quan_ly_diem.repository.BangDiemRepository;
import org.example.quan_ly_diem.repository.LopHocPhanRepository;
import org.example.quan_ly_diem.repository.SinhVienRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Transactional
    public void capNhatSoBuoiVang(Long maLopHp, String maSv, int soBuoi) {
        BangDiem bd = bangDiemRepository.findByLopHocPhan_MaLopHpAndSinhVien_MaSv(maLopHp, maSv).orElseThrow();
        bd.setSoBuoiVang(soBuoi);
        bangDiemRepository.save(bd);
    }
    @Transactional
    public void luuDiem(Long maLopHp, String maSv, double gk, double ck) {
        BangDiem bd = bangDiemRepository.findByLopHocPhan_MaLopHpAndSinhVien_MaSv(maLopHp, maSv).orElseThrow();
        int soVang = Optional.ofNullable(bd.getSoBuoiVang()).orElse(0);
        double cc;
        if (soVang > 3) {
            cc = 0;
            ck = 0;
        } else cc = Math.max(0, 10 - soVang);
        double tk10 = Math.round((cc * 0.1 + gk * 0.3 + ck * 0.6) * 10.0) / 10.0;
        String chu = "F";
        double he4 = 0;
        if (tk10 >= 8.5) {
            chu = "A";
            he4 = 4.0;
        } else if (tk10 >= 8.0) {
            chu = "B+";
            he4 = 3.5;
        } else if (tk10 >= 7.0) {
            chu = "B";
            he4 = 3.0;
        } else if (tk10 >= 6.5) {
            chu = "C+";
            he4 = 2.5;
        } else if (tk10 >= 5.5) {
            chu = "C";
            he4 = 2.0;
        } else if (tk10 >= 5.0) {
            chu = "D+";
            he4 = 1.5;
        } else if (tk10 >= 4.0) {
            chu = "D";
            he4 = 1.0;
        }
        bd.setDiemChuyenCan(cc);
        bd.setDiemGiuaKy(gk);
        bd.setDiemCuoiKy(ck);
        bd.setDiemTongKetHe10(tk10);
        bd.setDiemTongKetHe4(he4);
        bd.setDiemChu(chu);
        bangDiemRepository.save(bd);
    }


    @Transactional
    public void luuBangDiem(Long maLopHp, Map<String, String> duLieuForm) {
        for (BangDiem bd : danhSachLop(maLopHp)) {
            String maSv = bd.getSinhVien().getMaSv();
            double gk = chuyenDiem(duLieuForm.get("gk_" + maSv));
            double ck = chuyenDiem(duLieuForm.get("ck_" + maSv));
            luuDiem(maLopHp, maSv, gk, ck);
        }
    }

    private double chuyenDiem(String giaTri) {
        try {
            double diem = Double.parseDouble(giaTri);
            return Math.max(0, Math.min(10, diem));
        } catch (Exception e) {
            return 0;
        }
    }

    public double tinhGpaTichLuy(String maSv) {
        double tong = 0;
        int tc = 0;
        for (BangDiem bd : bangDiemCaNhan(maSv))
            if (bd.getDiemTongKetHe4() != null) {
                int soTc = bd.getLopHocPhan().getMonHoc().getSoTinChi();
                tong += bd.getDiemTongKetHe4() * soTc;
                tc += soTc;
            }
        return tc == 0 ? 0 : Math.round((tong / tc) * 100.0) / 100.0;
    }

    public int tongTinChi(String maSv) {
        int tc = 0;
        for (BangDiem bd : bangDiemCaNhan(maSv))
            if (bd.getDiemTongKetHe4() != null) tc += bd.getLopHocPhan().getMonHoc().getSoTinChi();
        return tc;
    }

    public String xepLoai(double gpa) {
        if (gpa >= 3.6) return "Xuất sắc";
        if (gpa >= 3.2) return "Giỏi";
        if (gpa >= 2.5) return "Khá";
        if (gpa >= 2.0) return "Trung bình";
        return "Yếu";
    }
}
