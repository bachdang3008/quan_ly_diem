package org.example.quan_ly_diem.service;

import org.example.quan_ly_diem.dto.ReportRows;
import org.example.quan_ly_diem.entity.BangDiem;
import org.example.quan_ly_diem.entity.LopHanhChinh;
import org.example.quan_ly_diem.entity.SinhVien;
import org.example.quan_ly_diem.repository.BangDiemRepository;
import org.example.quan_ly_diem.repository.LopHanhChinhRepository;
import org.example.quan_ly_diem.repository.SinhVienRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BaoCaoService {
    private final BangDiemRepository bangDiemRepository;
    private final SinhVienRepository sinhVienRepository;
    private final LopHanhChinhRepository lopHanhChinhRepository;

    private static final Map<String, double[]> RANGE = new LinkedHashMap<>();
    private static final Map<String, String> LABEL = new LinkedHashMap<>();

    static {
        RANGE.put("A", new double[]{3.60, 4.00});
        RANGE.put("B+", new double[]{3.20, 3.59});
        RANGE.put("B", new double[]{2.50, 3.19});
        RANGE.put("C", new double[]{2.00, 2.49});
        RANGE.put("D", new double[]{1.00, 1.99});
        RANGE.put("F", new double[]{0.00, 0.99});

        LABEL.put("A", "Xuất sắc");
        LABEL.put("B+", "Giỏi");
        LABEL.put("B", "Khá");
        LABEL.put("C", "Trung bình");
        LABEL.put("D", "Yếu");
        LABEL.put("F", "Kém");
    }

    public BaoCaoService(BangDiemRepository bangDiemRepository,
                         SinhVienRepository sinhVienRepository,
                         LopHanhChinhRepository lopHanhChinhRepository) {
        this.bangDiemRepository = bangDiemRepository;
        this.sinhVienRepository = sinhVienRepository;
        this.lopHanhChinhRepository = lopHanhChinhRepository;
    }

    public Map<String, double[]> layKhoangDiem() {
        return RANGE;
    }

    public Map<String, String> layNhanXepLoai() {
        return LABEL;
    }

    public List<LopHanhChinh> layDanhSachLop() {
        return lopHanhChinhRepository.findAllByOrderByMaLopHcDesc();
    }

    public long demSoLop() {
        return lopHanhChinhRepository.count();
    }

    public List<ReportRows.GradeDistribution> phoDiem() {
        return bangDiemRepository.findAll().stream()
                .filter(x -> x.getDiemChu() != null)
                .collect(Collectors.groupingBy(BangDiem::getDiemChu, TreeMap::new, Collectors.counting()))
                .entrySet().stream()
                .map(e -> new ReportRows.GradeDistribution(e.getKey(), e.getValue()))
                .toList();
    }

    public List<String> nhanBieuDo(List<ReportRows.GradeDistribution> phoDiem) {
        return phoDiem.stream().map(x -> "Điểm " + x.diemChu()).toList();
    }

    public List<Long> duLieuBieuDo(List<ReportRows.GradeDistribution> phoDiem) {
        return phoDiem.stream().map(ReportRows.GradeDistribution::soLuong).toList();
    }

    public List<ReportRows.GpaRow> locTheoMucDiem(String diemChu) {
        double[] range = RANGE.get(diemChu);
        if (diemChu == null || diemChu.isBlank() || range == null) return List.of();
        return theoKhoang(range[0], range[1]);
    }

    public long demSoTruot() {
        return truotMon().size();
    }

    public long demSoXuatSac() {
        return hocBong("XuatSac").size();
    }

    public List<ReportRows.FailedRow> truotMon() {
        return bangDiemRepository.findAll().stream()
                .filter(x -> "F".equals(x.getDiemChu()))
                .map(x -> new ReportRows.FailedRow(
                        x.getSinhVien().getMaSv(),
                        x.getSinhVien().getHoTen(),
                        x.getSinhVien().getLopHanhChinh() == null ? "" : x.getSinhVien().getLopHanhChinh().getMaLopHc(),
                        x.getLopHocPhan().getMonHoc().getTenMon(),
                        x.getLopHocPhan().getMaLopHienThi(),
                        x.getDiemTongKetHe10(),
                        x.getDiemChu()))
                .toList();
    }

    public List<ReportRows.ScholarshipRow> hocBong(String loai) {
        double min = "XuatSac".equals(loai) ? 3.6 : 3.2;
        Map<String, List<BangDiem>> nhom = bangDiemRepository.findAll().stream()
                .filter(x -> x.getDiemTongKetHe4() != null)
                .collect(Collectors.groupingBy(x -> x.getSinhVien().getMaSv()));

        return nhom.values().stream()
                .map(list -> {
                    SinhVien s = list.get(0).getSinhVien();
                    double gpa = list.stream().mapToDouble(BangDiem::getDiemTongKetHe4).average().orElse(0);
                    return new ReportRows.ScholarshipRow(
                            s.getMaSv(),
                            s.getHoTen(),
                            s.getLopHanhChinh() == null ? "" : s.getLopHanhChinh().getMaLopHc(),
                            lamTron2So(gpa),
                            list.size());
                })
                .filter(x -> x.gpa() >= min)
                .sorted(Comparator.comparing(ReportRows.ScholarshipRow::gpa).reversed())
                .toList();
    }

    public List<ReportRows.ClassResultRow> theoLopNeuCo(String maLop) {
        if (maLop == null || maLop.isBlank()) return List.of();
        return theoLop(maLop);
    }

    public List<ReportRows.ClassResultRow> theoLop(String maLop) {
        return sinhVienRepository.findByLopHanhChinh_MaLopHcOrderByMaSvAsc(maLop).stream()
                .map(s -> {
                    List<BangDiem> ds = bangDiemRepository.findBySinhVien_MaSvOrderByLopHocPhan_HocKy_MaHkAsc(s.getMaSv());
                    double gpa = ds.stream()
                            .filter(x -> x.getDiemTongKetHe4() != null)
                            .mapToDouble(BangDiem::getDiemTongKetHe4)
                            .average()
                            .orElse(0);
                    long soMonNo = ds.stream().filter(x -> "F".equals(x.getDiemChu())).count();
                    return new ReportRows.ClassResultRow(s.getMaSv(), s.getHoTen(), lamTron2So(gpa), soMonNo);
                })
                .toList();
    }

    public List<ReportRows.GpaRow> theoKhoang(double min, double max) {
        List<ReportRows.GpaRow> ketQua = new ArrayList<>();

        for (SinhVien s : sinhVienRepository.findAll()) {
            List<BangDiem> ds = bangDiemRepository.findBySinhVien_MaSvOrderByLopHocPhan_HocKy_MaHkAsc(s.getMaSv());
            double tongDiemTheoTinChi = 0;
            int tongTinChi = 0;

            for (BangDiem b : ds) {
                if (b.getDiemTongKetHe4() != null) {
                    int soTinChi = b.getLopHocPhan().getMonHoc().getSoTinChi();
                    tongDiemTheoTinChi += b.getDiemTongKetHe4() * soTinChi;
                    tongTinChi += soTinChi;
                }
            }

            if (tongTinChi == 0) continue;

            double gpa = tongDiemTheoTinChi / tongTinChi;
            if (gpa >= min && gpa <= max) {
                ketQua.add(new ReportRows.GpaRow(
                        s.getMaSv(),
                        s.getHoTen(),
                        s.getNgaySinh(),
                        s.getLopHanhChinh() == null ? "" : s.getLopHanhChinh().getTenLopHc(),
                        s.getLopHanhChinh() == null ? "" : s.getLopHanhChinh().getKhoa().getMaKhoa(),
                        lamTron2So(gpa)
                ));
            }
        }

        ketQua.sort(Comparator.comparing(ReportRows.GpaRow::gpaTichLuy).reversed());
        return ketQua;
    }

    private double lamTron2So(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

