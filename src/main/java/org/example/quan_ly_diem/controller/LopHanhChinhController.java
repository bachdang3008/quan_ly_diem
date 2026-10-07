package org.example.quan_ly_diem.controller;


import org.example.quan_ly_diem.service.LopHanhChinhService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lop-hanh-chinh")
public class LopHanhChinhController {
    private final LopHanhChinhService lopHanhChinhService;

    public LopHanhChinhController(LopHanhChinhService lopHanhChinhService) {
        this.lopHanhChinhService = lopHanhChinhService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("dsLop", lopHanhChinhService.layTatCa());
        return "lop-hanh-chinh/index";
    }
}
