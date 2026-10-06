package org.example.quan_ly_diem.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Khoa")
public class Khoa {
    @Id
    @Column(name = "ma_khoa", length = 20)
    private String maKhoa;

    @Column(name = "ten_khoa", nullable = false)
    private String tenKhoa;
}
