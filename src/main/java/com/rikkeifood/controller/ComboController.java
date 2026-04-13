package com.rikkeifood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rikkeifood.model.Combo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/combo")
public class ComboController {

    // Bộ nhớ tạm lưu danh sách Combo
    private static final List<Combo> comboList = new ArrayList<>();

    // Đường dẫn lưu file vật lý (Giống bên Food)
    private final String UPLOAD_DIR = "C:/RikkeiFood_Temp/";

    // ==========================================
    // 1. API MỞ GIAO DIỆN FORM TẠO COMBO
    // ==========================================
    @GetMapping("/add")
    public String showAddComboForm(Model model) {
        // Gửi object rỗng sang để form HTML map dữ liệu
        model.addAttribute("combo", new Combo());
        return "combo-add";
    }

    // ==========================================
    // 2. API XỬ LÝ LƯU COMBO VÀ BANNER
    // ==========================================
    @PostMapping("/add")
    public String addCombo(
            @ModelAttribute Combo combo,
            @RequestParam("banner") MultipartFile banner,
            Model model) {

        // Validate: Combo phải có ít nhất 2 món ăn được tích chọn
        if (combo.getItemList() == null || combo.getItemList().size() < 2) {
            model.addAttribute("error", "Lỗi: Một Siêu Combo phải bao gồm ít nhất 2 món ăn!");
            return "combo-add";
        }

        try {
            // Xử lý lưu file Banner (nếu người dùng có tải lên)
            if (!banner.isEmpty()) {
                File dir = new File(UPLOAD_DIR);
                if (!dir.exists()) dir.mkdirs();

                String uniqueFileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + "_" + banner.getOriginalFilename();
                File targetFile = new File(UPLOAD_DIR + uniqueFileName);

                banner.transferTo(targetFile);
                combo.setBannerPath(targetFile.getAbsolutePath());
            }

            // Lưu vào list tĩnh
            comboList.add(combo);

            // In ra console dữ liệu Combo dưới dạng JSON (Dùng thư viện Jackson đã thêm ở build.gradle)
            ObjectMapper mapper = new ObjectMapper();
            String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(combo);
            System.out.println(">>> [BÀI 5] DỮ LIỆU SIÊU COMBO MỚI TẠO (JSON): \n" + jsonResult);

            // Báo thành công và cho load lại trang form
            model.addAttribute("success", "Tạo Siêu Combo thành công! (Mở console IntelliJ để xem chuỗi JSON)");
            return "combo-add";

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi trong quá trình xử lý file banner: " + e.getMessage());
            return "combo-add";
        }
    }
}