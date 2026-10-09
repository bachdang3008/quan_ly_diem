package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.repository.BangDiemRepository;
import org.example.quan_ly_diem.repository.LopHocPhanRepository;
import org.example.quan_ly_diem.repository.SinhVienRepository;
import org.springframework.stereotype.Service;

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
}
