package org.example.quan_ly_diem.controller;

import org.example.quan_ly_diem.service.MonHocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @PostMapping("/them")
    public String themPost(@RequestParam String maMon,
                           @RequestParam String tenMon,
                           @RequestParam Integer soTinChi,
                           RedirectAttributes redirect) {
        try {
            monHocService.them(maMon, tenMon, soTinChi);
            redirect.addFlashAttribute("success", "Thêm môn học thành công!");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Mã môn học đã tồn tại hoặc dữ liệu không hợp lệ.");
        }
        return "redirect:/mon-hoc";
    }

    @GetMapping("/sua/{id}")
    public String sua(@PathVariable String id, Model model) {
        model.addAttribute("mon", monHocService.layTheoMa(id));
        return "mon-hoc/form";
    }

    @PostMapping("/sua/{id}")
    public String suaPost(@PathVariable String id,
                          @RequestParam String tenMon,
                          @RequestParam Integer soTinChi,
                          RedirectAttributes redirect) {
        monHocService.capNhat(id, tenMon, soTinChi);
        redirect.addFlashAttribute("success", "Cập nhật môn học thành công!");
        return "redirect:/mon-hoc";
    }

    @PostMapping("/xoa/{id}")
    public String xoa(@PathVariable String id, RedirectAttributes redirect) {
        try {
            monHocService.xoa(id);
            redirect.addFlashAttribute("success", "Đã xóa môn học.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Không thể xóa vì môn học đang được dùng trong lớp học phần.");
        }
        return "redirect:/mon-hoc";
    }
}