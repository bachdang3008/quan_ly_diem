package org.example.quan_ly_diem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MonHoc")
public class MonHoc {
    @Id
    @Column(name = "ma_mon", length = 30)
    private String maMon;

    @Column(name = "ten_mon", nullable = false)
    private String tenMon;

    @Column(name = "so_tin_chi", nullable = false)
    private Integer soTinChi;
}