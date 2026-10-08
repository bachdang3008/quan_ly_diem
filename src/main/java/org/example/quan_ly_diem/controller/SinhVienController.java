package org.example.quan_ly_diem.controller;

import org.example.quan_ly_diem.service.SinhVienService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

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
    @PostMapping("/them")
    public String themPost(@RequestParam String maSv,
                           @RequestParam String hoTen,
                           @RequestParam LocalDate ngaySinh,
                           @RequestParam String gioiTinh,
                           @RequestParam String maLopHc,
                           RedirectAttributes redirect) {
        try {
            sinhVienService.them(maSv, hoTen, ngaySinh, gioiTinh, maLopHc);
            redirect.addFlashAttribute("success", "Thêm sinh viên thành công!");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Mã sinh viên đã tồn tại hoặc dữ liệu không hợp lệ.");
        }
        return "redirect:/sinh-vien";
    }

    @GetMapping("/sua/{id}")
    public String sua(@PathVariable String id, Model model) {
        model.addAttribute("sv", sinhVienService.layTheoMa(id));
        model.addAttribute("dsLop", sinhVienService.layDanhSachLop());
        return "sinh-vien/form";
    }

    @PostMapping("/sua/{id}")
    public String suaPost(@PathVariable String id,
                          @RequestParam String hoTen,
                          @RequestParam LocalDate ngaySinh,
                          @RequestParam String gioiTinh,
                          @RequestParam String maLopHc,
                          RedirectAttributes redirect) {
        sinhVienService.capNhat(id, hoTen, ngaySinh, gioiTinh, maLopHc);
        redirect.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/sinh-vien";
    }

    @PostMapping("/xoa/{id}")
    public String xoa(@PathVariable String id, RedirectAttributes redirect) {
        try {
            sinhVienService.xoa(id);
            redirect.addFlashAttribute("success", "Đã xóa sinh viên.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Không thể xóa vì sinh viên đang có dữ liệu điểm/lớp học phần.");
        }
        return "redirect:/sinh-vien";
    }

}
