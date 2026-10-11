package org.example.quan_ly_diem.controller;


import org.example.quan_ly_diem.service.BangDiemService;
import org.example.quan_ly_diem.service.LopHocPhanService;
import org.example.quan_ly_diem.service.VangNghiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/lop-hoc-phan")
public class LopHocPhanController {
    private final LopHocPhanService lopHocPhanService;
    private final BangDiemService bangDiemService;
    private final VangNghiService vangNghiService;

    public LopHocPhanController(LopHocPhanService lopHocPhanService,
                                BangDiemService bangDiemService,
                                VangNghiService vangNghiService) {
        this.lopHocPhanService = lopHocPhanService;
        this.bangDiemService = bangDiemService;
        this.vangNghiService = vangNghiService;
    }

    @GetMapping
    public String index(@RequestParam(required = false) Long maHk, Model model) {
        Long hocKyChon = lopHocPhanService.chonHocKyMacDinh(maHk);
        model.addAttribute("dsHocKy", lopHocPhanService.layTatCaHocKy());
        model.addAttribute("maHkChon", hocKyChon);
        model.addAttribute("dsLop", lopHocPhanService.layDanhSachLop(hocKyChon));
        return "lop-hoc-phan/index";
    }

    private void napDuLieuForm(Model model) {
        model.addAttribute("dsMon", lopHocPhanService.layTatCaMonHoc());
        model.addAttribute("dsHocKy", lopHocPhanService.layTatCaHocKy());
    }

    @GetMapping("/them")
    public String them(Model model) {
        napDuLieuForm(model);
        return "lop-hoc-phan/form";
    } @PostMapping("/them")
    public String themPost(@RequestParam String maLopHienThi,
                           @RequestParam String maMon,
                           @RequestParam(required = false) Long maHk,
                           @RequestParam(required = false, defaultValue = "false") boolean taoHocKyMoi,
                           @RequestParam(required = false) String tenHkMoi,
                           @RequestParam(required = false) Integer namBatDau,
                           @RequestParam String giangVien,
                           @RequestParam String phongHoc,
                           @RequestParam Integer tongSoBuoi,
                           RedirectAttributes redirect) {
        try {
            lopHocPhanService.them(maLopHienThi, maMon, maHk, taoHocKyMoi,
                    tenHkMoi, namBatDau, giangVien, phongHoc, tongSoBuoi);
            redirect.addFlashAttribute("success", "Mở lớp học phần thành công!");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Không thể mở lớp: " + e.getMessage());
        }
        return "redirect:/lop-hoc-phan";
    }

    @GetMapping("/sua/{id}")
    public String sua(@PathVariable Long id, Model model) {
        model.addAttribute("lop", lopHocPhanService.layTheoMa(id));
        napDuLieuForm(model);
        return "lop-hoc-phan/form";
    }

    @PostMapping("/sua/{id}")
    public String suaPost(@PathVariable Long id,
                          @RequestParam String maLopHienThi,
                          @RequestParam String maMon,
                          @RequestParam Long maHk,
                          @RequestParam String giangVien,
                          @RequestParam String phongHoc,
                          @RequestParam Integer tongSoBuoi,
                          RedirectAttributes redirect) {
        lopHocPhanService.capNhat(id, maLopHienThi, maMon, maHk, giangVien, phongHoc, tongSoBuoi);
        redirect.addFlashAttribute("success", "Cập nhật lớp thành công!");
        return "redirect:/lop-hoc-phan";
    }

    @PostMapping("/xoa/{id}")
    public String xoa(@PathVariable Long id, RedirectAttributes redirect) {
        lopHocPhanService.xoa(id);
        redirect.addFlashAttribute("success", "Đã xóa lớp học phần.");
        return "redirect:/lop-hoc-phan";
    }

    @GetMapping("/{id}")
    public String chiTiet(@PathVariable Long id, Model model) {
        model.addAttribute("lop", lopHocPhanService.layTheoMa(id));
        model.addAttribute("dsSinhVien", bangDiemService.danhSachLop(id));
        return "lop-hoc-phan/chi-tiet";
    }

    @PostMapping("/{id}/them-sinh-vien")
    public String themSinhVien(@PathVariable Long id,
                               @RequestParam String maSv,
                               RedirectAttributes redirect) {
        try {
            boolean daThem = bangDiemService.themSinhVien(id, maSv);
            redirect.addFlashAttribute("success", daThem ? "Đã thêm sinh viên." : "Sinh viên đã có trong lớp.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Không tìm thấy sinh viên.");
        }
        return "redirect:/lop-hoc-phan/" + id;
    }

    @PostMapping("/{id}/xoa-sinh-vien/{bangDiemId}")
    public String xoaSinhVien(@PathVariable Long id, @PathVariable Long bangDiemId) {
        bangDiemService.xoaKhoiLop(bangDiemId);
        return "redirect:/lop-hoc-phan/" + id;
    }

    @GetMapping("/{id}/them-nhieu")
    public String themNhieu(@PathVariable Long id,
                            @RequestParam(defaultValue = "") String maLopHc,
                            Model model) {
        var dsSinhVien = lopHocPhanService.laySinhVienTheoLopHanhChinh(maLopHc);
        model.addAttribute("lop", lopHocPhanService.layTheoMa(id));
        model.addAttribute("dsLopHC", lopHocPhanService.layTatCaLopHanhChinh());
        model.addAttribute("maLopHcChon", maLopHc);
        model.addAttribute("dsSinhVien", dsSinhVien);
        model.addAttribute("daCo", bangDiemService.danhSachMaSinhVienDaCo(id, dsSinhVien));
        return "lop-hoc-phan/them-nhieu";
    }

    @PostMapping("/{id}/them-nhieu")
    public String themNhieuPost(@PathVariable Long id,
                                @RequestParam(required = false, name = "maSv") List<String> maSv) {
        bangDiemService.themNhieuSinhVien(id, maSv);
        return "redirect:/lop-hoc-phan/" + id;
    }
    @GetMapping("/{id}/nhap-diem")
    public String nhapDiem(@PathVariable Long id, Model model) {
        model.addAttribute("lop", lopHocPhanService.layTheoMa(id));
        model.addAttribute("dsSinhVien", bangDiemService.danhSachLop(id));
        return "lop-hoc-phan/nhap-diem";
    }

    @PostMapping("/{id}/nhap-diem")
    public String luuDiem(@PathVariable Long id,
                          @RequestParam Map<String, String> duLieuForm,
                          RedirectAttributes redirect) {
        bangDiemService.luuBangDiem(id, duLieuForm);
        redirect.addFlashAttribute("success", "Đã lưu bảng điểm.");
        return "redirect:/lop-hoc-phan/" + id + "/nhap-diem";
    }
    @GetMapping("/{id}/vang/{maSv}")
    public String vang(@PathVariable Long id,
                       @PathVariable String maSv,
                       @RequestParam(defaultValue = "") String from,
                       Model model) {
        model.addAttribute("lop", lopHocPhanService.layTheoMa(id));
        model.addAttribute("sv", lopHocPhanService.laySinhVien(maSv));
        model.addAttribute("dsVang", vangNghiService.danhSach(id, maSv));
        model.addAttribute("from", from);
        return "lop-hoc-phan/vang-nghi";
    }

    @PostMapping("/{id}/vang/{maSv}")
    public String themVang(@PathVariable Long id,
                           @PathVariable String maSv,
                           @RequestParam LocalDate ngayVang,
                           @RequestParam String lyDo,
                           @RequestParam(defaultValue = "") String from) {
        vangNghiService.them(id, maSv, ngayVang, lyDo);
        return "redirect:/lop-hoc-phan/" + id + "/vang/" + maSv + thamSoFrom(from);
    }

    @PostMapping("/{id}/vang/{maSv}/xoa/{vangId}")
    public String xoaVang(@PathVariable Long id,
                          @PathVariable String maSv,
                          @PathVariable Long vangId,
                          @RequestParam(defaultValue = "") String from) {
        vangNghiService.xoa(vangId, id, maSv);
        return "redirect:/lop-hoc-phan/" + id + "/vang/" + maSv + thamSoFrom(from);
    }

    private String thamSoFrom(String from) {
        return from == null || from.isBlank() ? "" : "?from=" + from;
    }
}
