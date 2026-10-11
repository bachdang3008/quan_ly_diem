package org.example.quan_ly_diem.controller;

import jakarta.servlet.http.HttpSession;
import org.example.quan_ly_diem.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String hienThiLogin(HttpSession session) {
        if (session.getAttribute("admin_logged_in") != null) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String dangNhap(@RequestParam String username,
                           @RequestParam String password,
                           HttpSession session,
                           Model model) {
        var admin = authService.dangNhap(username, password);

        if (admin.isEmpty()) {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            return "login";
        }

        session.setAttribute("admin_logged_in", true);
        session.setAttribute("admin_name", admin.get().getHoTen());
        session.setAttribute("admin_id", admin.get().getMaAdmin());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String dangXuat(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}