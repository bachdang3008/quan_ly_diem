package org.example.quan_ly_diem.controller;


import org.example.quan_ly_diem.service.BangDiemService;
import org.example.quan_ly_diem.service.LopHocPhanService;
import org.example.quan_ly_diem.service.VangNghiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    }
}