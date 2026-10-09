package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.entity.ChiTietVangNghi;
import org.example.quan_ly_diem.repository.ChiTietVangNghiRepository;
import org.example.quan_ly_diem.repository.LopHocPhanRepository;
import org.example.quan_ly_diem.repository.SinhVienRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VangNghiService {
    private final ChiTietVangNghiRepository repo;
    private final LopHocPhanRepository lopRepo;
    private final SinhVienRepository svRepo;
    private final BangDiemService bangDiemService;

    public VangNghiService(ChiTietVangNghiRepository r, LopHocPhanRepository l, SinhVienRepository s, BangDiemService b) {
        repo = r;
        lopRepo = l;
        svRepo = s;
        bangDiemService = b;
    }

    public List<ChiTietVangNghi> danhSach(Long lop, String sv) {
        return repo.findByLopHocPhan_MaLopHpAndSinhVien_MaSvOrderByNgayVangDesc(lop, sv);
    }
}
