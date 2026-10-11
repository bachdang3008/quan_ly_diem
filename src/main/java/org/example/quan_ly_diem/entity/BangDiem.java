package org.example.quan_ly_diem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BangDiem", uniqueConstraints = @UniqueConstraint(columnNames = {"ma_lop_hp", "ma_sv"}))
public class BangDiem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ma_lop_hp", nullable = false)
    private LopHocPhan lopHocPhan;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ma_sv", nullable = false)
    private SinhVien sinhVien;

    @Column(name = "diem_chuyen_can")
    private Double diemChuyenCan = 0.0;

    @Column(name = "diem_giua_ky")
    private Double diemGiuaKy = 0.0;

    @Column(name = "diem_cuoi_ky")
    private Double diemCuoiKy = 0.0;

    @Column(name = "diem_tong_ket_he_10")
    private Double diemTongKetHe10;

    @Column(name = "diem_tong_ket_he_4")
    private Double diemTongKetHe4;

    @Column(name = "diem_chu", length = 5)
    private String diemChu;

    @Column(name = "so_buoi_vang")
    private Integer soBuoiVang = 0;

}
