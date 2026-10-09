package org.example.quan_ly_diem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "HocKy", uniqueConstraints = @UniqueConstraint(columnNames = {"ten_hk", "nam_hoc"}))
public class HocKy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hk")
    private Long maHk;

    @Column(name = "ten_hk", nullable = false)
    private String tenHk;

    @Column(name = "nam_hoc", nullable = false)
    private String namHoc;

    @Column(name = "trang_thai")
    private Boolean trangThai = true;

}
