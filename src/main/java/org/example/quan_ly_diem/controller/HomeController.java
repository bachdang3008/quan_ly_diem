package org.example.quan_ly_diem.controller;

import org.example.quan_ly_diem.service.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping({"/", "/dashboard"})
    public String index(Model model) {
        var thongKe = homeService.layThongKe();
        model.addAttribute("soSv", thongKe.soSinhVien());
        model.addAttribute("soMon", thongKe.soMonHoc());
        model.addAttribute("soLop", thongKe.soLopHocPhan());
        model.addAttribute("soKhoa", thongKe.soKhoa());
        return "dashboard";
    }
}
