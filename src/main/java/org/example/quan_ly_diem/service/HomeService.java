package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.repository.KhoaRepository;
import org.example.quan_ly_diem.repository.LopHocPhanRepository;
import org.example.quan_ly_diem.repository.MonHocRepository;
import org.example.quan_ly_diem.repository.SinhVienRepository;
import org.springframework.stereotype.Service;

@Service
public class HomeService {
    private final SinhVienRepository sinhVienRepository;
    private final MonHocRepository monHocRepository;
    private final LopHocPhanRepository lopHocPhanRepository;
    private final KhoaRepository khoaRepository;

    public HomeService(SinhVienRepository sinhVienRepository,
                       MonHocRepository monHocRepository,
                       LopHocPhanRepository lopHocPhanRepository,
                       KhoaRepository khoaRepository) {
        this.sinhVienRepository = sinhVienRepository;
        this.monHocRepository = monHocRepository;
        this.lopHocPhanRepository = lopHocPhanRepository;
        this.khoaRepository = khoaRepository;
    }

    public DashboardStats layThongKe() {
        return new DashboardStats(
                sinhVienRepository.count(),
                monHocRepository.count(),
                lopHocPhanRepository.count(),
                khoaRepository.count()
        );
    }

    public record DashboardStats(long soSinhVien, long soMonHoc, long soLopHocPhan, long soKhoa) {
    }
}
