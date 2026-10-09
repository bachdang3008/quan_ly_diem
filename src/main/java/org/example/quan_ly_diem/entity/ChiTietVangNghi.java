package org.example.quan_ly_diem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ChiTietVangNghi")
public class ChiTietVangNghi {
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

    @Column(name = "ngay_vang", nullable = false)
    private LocalDate ngayVang;

    @Column(name = "ly_do")
    private String lyDo;

}

