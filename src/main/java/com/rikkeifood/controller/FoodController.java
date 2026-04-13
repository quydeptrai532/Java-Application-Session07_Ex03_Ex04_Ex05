package com.rikkeifood.controller;

import com.rikkeifood.model.Food;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/food")
public class FoodController {

    // Bộ nhớ tạm In-Memory để lưu danh sách món ăn
    private static final List<Food> foodList = new ArrayList<>();

    // Đường dẫn lưu file vật lý trên máy của m (Nhớ tạo thư mục này ở ổ C)
    private final String UPLOAD_DIR = "C:/RikkeiFood_Temp/";
    // 1. API MỞ GIAO DIỆN FORM (Bị thiếu ở code cũ)
    @GetMapping("/add")
    public String showAddFoodForm(Model model) {
        // Gửi một object rỗng sang để Thymeleaf bind data vào form
        model.addAttribute("food", new Food());
        return "food-add";
    }
    // 2. API XỬ LÝ LƯU TRỮ KHI ẤN SUBMIT
    @PostMapping("/add")
    public String addFood(
            @ModelAttribute Food food,
            @RequestParam("image") MultipartFile file,
            RedirectAttributes redirectAttributes,
            Model model) {

        // Validate 1: Quên đính kèm ảnh
        if (file.isEmpty()) {
            model.addAttribute("error", "Vui lòng chọn ảnh cho món ăn!");
            return "food-add";
        }

        // Validate 2: Sai định dạng file
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            String lowerCaseName = originalFilename.toLowerCase();
            if (!lowerCaseName.endsWith(".jpg") && !lowerCaseName.endsWith(".png") && !lowerCaseName.endsWith(".jpeg")) {
                model.addAttribute("error", "Chỉ chấp nhận file ảnh định dạng .jpg, .png, .jpeg!");
                return "food-add";
            }
        }

        // Validate 3: Giá tiền âm
        if (food.getPrice() < 0) {
            model.addAttribute("error", "Giá tiền không được nhỏ hơn 0!");
            return "food-add";
        }

        try {
            // Tạo thư mục nếu chưa tồn tại
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            // Đổi tên file tránh ghi đè (Dùng UUID + Timestamp)
            String uniqueFileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + "_" + originalFilename;
            File targetFile = new File(UPLOAD_DIR + uniqueFileName);

            // Lưu file vật lý
            file.transferTo(targetFile);

            // Cập nhật thông tin và lưu vào List tĩnh
            food.setId(UUID.randomUUID().toString());
            food.setPhysicalImagePath(targetFile.getAbsolutePath());
            foodList.add(food);

            // In log ra console
            System.out.println(">>> Đã thêm món: " + food.getName());
            System.out.println(">>> Tổng số món hiện tại: " + foodList.size());

            // Truyền thông báo thành công và ID sang trang chi tiết
            redirectAttributes.addFlashAttribute("successMessage", "Thêm món ăn thành công!");
            redirectAttributes.addAttribute("id", food.getId());

            return "redirect:/food/detail";

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi hệ thống khi lưu file: " + e.getMessage());
            return "food-add";
        }
    }

    // 3. API HIỂN THỊ CHI TIẾT MÓN ĂN
    @GetMapping("/detail")
    public String showDetail(@RequestParam("id") String id, Model model) {
        // Tìm món ăn trong List tĩnh dựa vào ID
        Food foundFood = foodList.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);

        model.addAttribute("food", foundFood);
        return "food-detail";
    }


}