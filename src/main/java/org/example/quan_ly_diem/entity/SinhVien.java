package org.example.quan_ly_diem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name = "SinhVien")
public class SinhVien {
    @Id
    @Column(name = "ma_sv", length = 30)
    private String maSv;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "gioi_tinh", length = 20)
    private String gioiTinh;

    @ManyToOne
    @JoinColumn(name = "ma_lop_hc")
    private LopHanhChinh lopHanhChinh;

    @Column(name = "trang_thai")
    private String trangThai = "Đang học";
}
