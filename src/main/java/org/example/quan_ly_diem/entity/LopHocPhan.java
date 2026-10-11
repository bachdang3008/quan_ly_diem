package org.example.quan_ly_diem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LopHocPhan", uniqueConstraints = @UniqueConstraint(columnNames = "ma_lop_hien_thi"))
public class LopHocPhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_lop_hp")
    private Long maLopHp;

    @Column(name = "ma_lop_hien_thi", nullable = false)
    private String maLopHienThi;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ma_mon", nullable = false)
    private MonHoc monHoc;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ma_hk", nullable = false)
    private HocKy hocKy;

    @Column(name = "giang_vien_phu_trach")
    private String giangVienPhuTrach;

    @Column(name = "phong_hoc")
    private String phongHoc;

    @Column(name = "tong_so_buoi_hoc")
    private Integer tongSoBuoiHoc;

}
