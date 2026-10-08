package org.example.quan_ly_diem.controller;

import org.example.quan_ly_diem.service.MonHocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mon-hoc")
public class MonHocController {
    private final MonHocService monHocService;

    public MonHocController(MonHocService monHocService) {
        this.monHocService = monHocService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("dsMon", monHocService.layTatCa());
        return "mon-hoc/index";
    }

    @GetMapping("/them")
    public String them() {
        return "mon-hoc/form";
    }
}