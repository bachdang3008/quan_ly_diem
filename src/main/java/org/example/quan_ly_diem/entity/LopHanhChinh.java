package org.example.quan_ly_diem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LopHanhChinh")
public class LopHanhChinh {
    @Id
    @Column(name = "ma_lop_hc", length = 30)
    private String maLopHc;

    @Column(name = "ten_lop_hc", nullable = false)
    private String tenLopHc;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ma_khoa", nullable = false)
    private Khoa khoa;

}
