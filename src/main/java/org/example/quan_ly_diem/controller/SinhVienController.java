package org.example.quan_ly_diem.controller;

import org.example.quan_ly_diem.service.SinhVienService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sinh-vien")
public class SinhVienController {
    private final SinhVienService sinhVienService;

    public SinhVienController(SinhVienService sinhVienService) {
        this.sinhVienService = sinhVienService;
    }

    @GetMapping
    public String index(@RequestParam(defaultValue = "") String khoa,
                        @RequestParam(defaultValue = "") String lop,
                        @RequestParam(name = "tu_khoa", defaultValue = "") String tuKhoa,
                        Model model) {
        model.addAttribute("dsKhoa", sinhVienService.layDanhSachKhoaHoc());
        model.addAttribute("dsLop", sinhVienService.layDanhSachLopTheoKhoa(khoa));
        model.addAttribute("dsSinhVien", sinhVienService.timKiem(khoa, lop, tuKhoa));
        model.addAttribute("khoaChon", khoa);
        model.addAttribute("lopChon", lop);
        model.addAttribute("tuKhoa", tuKhoa);
        return "sinh-vien/index";
    }

    @GetMapping("/them")
    public String them(Model model) {
        model.addAttribute("dsLop", sinhVienService.layDanhSachLop());
        return "sinh-vien/form";
    }
}
