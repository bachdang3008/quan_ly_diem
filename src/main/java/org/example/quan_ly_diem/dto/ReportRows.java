package org.example.quan_ly_diem.dto;

import java.time.LocalDate;

public final class ReportRows {
    private ReportRows() {
    }

    public record GradeDistribution(String diemChu, long soLuong) {

    }

    public record FailedRow(String maSv, String hoTen, String maLopHc, String tenMon, String maLopHienThi,
                            Double diemHe10, String diemChu) {
    }

    public record ScholarshipRow(String maSv, String hoTen, String maLopHc, Double gpa, long soMon) {
    }

    public record ClassResultRow(String maSv, String hoTen, Double gpa, long soMonNo) {
    }

    public record GpaRow(String maSv, String hoTen, LocalDate ngaySinh, String tenLopHc, String maKhoa,
                         Double gpaTichLuy) {
    }
}