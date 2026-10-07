package org.example.quan_ly_diem.controller;


import org.example.quan_ly_diem.service.LopHanhChinhService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/them")
    public String them(Model model) {
        model.addAttribute("dsKhoa", lopHanhChinhService.layTatCaKhoa());
        return "lop-hanh-chinh/form";
    }

    @PostMapping("/them")
    public String themPost(@RequestParam String maLopHc,
                           @RequestParam String tenLopHc,
                           @RequestParam(required = false, defaultValue = "") String maKhoa,
                           @RequestParam(required = false, defaultValue = "") String maKhoaMoi,
                           @RequestParam(required = false, defaultValue = "") String tenKhoaMoi,
                           RedirectAttributes redirect) {
        try {
            lopHanhChinhService.them(maLopHc, tenLopHc, maKhoa, maKhoaMoi, tenKhoaMoi);
            redirect.addFlashAttribute("success", "Thêm lớp hành chính thành công!");
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/lop-hanh-chinh/them";
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Mã lớp đã tồn tại hoặc dữ liệu không hợp lệ.");
        }
        return "redirect:/lop-hanh-chinh";
    }

    @GetMapping("/sua/{id}")
    public String sua(@PathVariable String id, Model model) {
        model.addAttribute("lop", lopHanhChinhService.layTheoMa(id));
        model.addAttribute("dsKhoa", lopHanhChinhService.layTatCaKhoa());
        return "lop-hanh-chinh/form";
    }

    @PostMapping("/sua/{id}")
    public String suaPost(@PathVariable String id,
                          @RequestParam String tenLopHc,
                          @RequestParam String maKhoa,
                          RedirectAttributes redirect) {
        lopHanhChinhService.capNhat(id, tenLopHc, maKhoa);
        redirect.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/lop-hanh-chinh";
    }

    @PostMapping("/xoa/{id}")
    public String xoa(@PathVariable String id, RedirectAttributes redirect) {
        try {
            lopHanhChinhService.xoa(id);
            redirect.addFlashAttribute("success", "Đã xóa lớp.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Không thể xóa: lớp đang có sinh viên.");
        }
        return "redirect:/lop-hanh-chinh";
    }

}
