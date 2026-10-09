package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.entity.ChiTietVangNghi;
import org.example.quan_ly_diem.repository.ChiTietVangNghiRepository;
import org.example.quan_ly_diem.repository.LopHocPhanRepository;
import org.example.quan_ly_diem.repository.SinhVienRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Transactional
    public void them(Long lop, String sv, LocalDate ngay, String lyDo) {
        ChiTietVangNghi v = new ChiTietVangNghi();
        v.setLopHocPhan(lopRepo.findById(lop).orElseThrow());
        v.setSinhVien(svRepo.findById(sv).orElseThrow());
        v.setNgayVang(ngay);
        v.setLyDo(lyDo);
        repo.save(v);
        sync(lop, sv);
    }

    @Transactional
    public void xoa(Long id, Long lop, String sv) {
        repo.deleteById(id);
        repo.flush();
        sync(lop, sv);
    }

    private void sync(Long lop, String sv) {
        bangDiemService.capNhatSoBuoiVang(lop, sv, (int) repo.countByLopHocPhan_MaLopHpAndSinhVien_MaSv(lop, sv));
    }
}
