package org.example.quan_ly_diem.controller;


import org.example.quan_ly_diem.service.BaoCaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bao-cao")
public class BaoCaoController {
    private final BaoCaoService baoCaoService;

    public BaoCaoController(BaoCaoService baoCaoService) {
        this.baoCaoService = baoCaoService;
    }

    @GetMapping
    public String index(@RequestParam(defaultValue = "") String diemChu, Model model) {
        var phoDiem = baoCaoService.phoDiem();
        model.addAttribute("phoDiem", phoDiem);
        model.addAttribute("chartLabels", baoCaoService.nhanBieuDo(phoDiem));
        model.addAttribute("chartData", baoCaoService.duLieuBieuDo(phoDiem));
        model.addAttribute("soTruot", baoCaoService.demSoTruot());
        model.addAttribute("soXuatSac", baoCaoService.demSoXuatSac());
        model.addAttribute("soLop", baoCaoService.demSoLop());
        model.addAttribute("ranges", baoCaoService.layKhoangDiem());
        model.addAttribute("labels", baoCaoService.layNhanXepLoai());
        model.addAttribute("diemChon", diemChu);
        model.addAttribute("dsGpa", baoCaoService.locTheoMucDiem(diemChu));
        return "bao-cao/index";
    }

    @GetMapping("/truot-mon")
    public String truotMon(Model model) {
        model.addAttribute("ds", baoCaoService.truotMon());
        return "bao-cao/truot-mon";
    }

    @GetMapping("/hoc-bong")
    public String hocBong(@RequestParam(defaultValue = "XuatSac") String loai, Model model) {
        model.addAttribute("loai", loai);
        model.addAttribute("ds", baoCaoService.hocBong(loai));
        return "bao-cao/hoc-bong";
    }

    @GetMapping("/theo-lop")
    public String theoLop(@RequestParam(defaultValue = "") String maLopHc, Model model) {
        model.addAttribute("dsLop", baoCaoService.layDanhSachLop());
        model.addAttribute("lopChon", maLopHc);
        model.addAttribute("ds", baoCaoService.theoLopNeuCo(maLopHc));
        return "bao-cao/theo-lop";
    }
}