package org.example.menaandfeena_finalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiResponse;
import org.example.menaandfeena_finalproject.DTO.In.ContactRequestDto;
import org.example.menaandfeena_finalproject.DTO.In.UserRegisterRequestDto;
import org.example.menaandfeena_finalproject.DTO.Out.ResidentResponseDto;
import org.example.menaandfeena_finalproject.DTO.Out.UserActivityResponseDto;
import org.example.menaandfeena_finalproject.DTO.Out.UserProfileResponseDto;
import org.example.menaandfeena_finalproject.DTO.Out.UserRegisterResponseDto;
import org.example.menaandfeena_finalproject.Model.User;
import org.example.menaandfeena_finalproject.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ================= CRUD الأساسي =================

    @GetMapping("/get-all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.status(200).body(userService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@RequestBody @Valid User user) {
        userService.add(user);
        return ResponseEntity.status(201).body(new ApiResponse("User added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer id, @RequestBody @Valid User user) {
        userService.update(id, user);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }


    //Reenad
    @GetMapping("/welcome")
    public ResponseEntity<ApiResponse> getWelcomeScreen() {
        String welcomeString = "العنوان: منا وفينا\n"
                + "العنوان الفرعي: مجتمع واحد .. هدف واحد\n"
                + "الوصف: نُسهم معًا في بناء مجتمع متكافل ومستدام من خلال مبادرات نوعية وشراكات مجتمعية فاعلة داخل الحي، مدعومة بتقنيات الذكاء الاصطناعي لتعزيز المشاركة المجتمعية ورفع جودة الحياة";

        return ResponseEntity.status(200).body(new ApiResponse(welcomeString));
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse> getAboutInfo() {
        String infoString = "من نحن: منصة مجتمعية ولدت من قلب الحي، تسعى لتعزيز الروابط الإنسانية وبناء بيئة تكافلية ومستدامة بين الجيران.\n"
                + "الرؤية: أن نكون النموذج الرائد عالمياً في تحويل الأحياء السكنية إلى مجتمعات حيوية، مترابطة، وذكية تدعم جودة الحياة رفاهية الجميع.\n"
                + "الرسالة: تفعيل دور الأفراد من خلال مبادرات نوعية وشراكات فاعلة تساهم في تبادل الخبرات، الدعم المتبادل، وحماية البيئة المحيطة بنا.\n"
                + "القيم: المبادرة، الاستدامة، التكافل";

        return ResponseEntity.status(200).body(new ApiResponse(infoString));
    }

    @PostMapping("/contact")
    public ResponseEntity<ApiResponse> sendContactMessage(@RequestBody @Valid ContactRequestDto dto) {
        System.out.println("📩 [رسالة جديدة من تواصل معنا عبر الـ Body]:");
        System.out.println("الاسم: " + dto.getName() + " | البريد: " + dto.getEmail());
        System.out.println("الرسالة: " + dto.getMessage());

        return ResponseEntity.status(200).body(new ApiResponse("تم إرسال رسالتك بنجاح، وسيتواصل معك فريق الدعم قريباً"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserRegisterRequestDto dto) {
        UserRegisterResponseDto response = userService.registerUser(dto);

        String welcomeShortString = "هلا والله " + response.getFullName() + " في حي " + response.getDetectedNeighborhoodName();

        return ResponseEntity.status(201).body(new ApiResponse(welcomeShortString));
    }


    /*@PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestParam String email, @RequestParam String password) {
        UserProfileResponseDto response = userService.loginUser(email, password);

        String loginString = "تم تسجيل الدخول بنجاح\n"
                + "المعرف: " + response.getId() + "\n"
                + "الاسم الكامل: " + response.getFullName() + "\n"
                + "الحي الحالي: " + response.getNeighborhoodName();

        return ResponseEntity.status(200).body(new ApiResponse("تم تسجيل الدخول ينجاح"));
    }*/

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse> getProfile(@PathVariable Integer userId) {
        String content = userService.getUserProfile(userId);
        return ResponseEntity.status(200).body(new ApiResponse(content));
    }

    @GetMapping("/residents/{userId}")
    public ResponseEntity<ApiResponse> getResidents(@PathVariable Integer userId) {
        String content = userService.getNeighborhoodResidents(userId);
        return ResponseEntity.status(200).body(new ApiResponse(content));
    }

    @GetMapping("/activity/{userId}")
    public ResponseEntity<ApiResponse> getActivityLog(@PathVariable Integer userId) {
        String content = userService.getUserActivityLog(userId);
        return ResponseEntity.status(200).body(new ApiResponse(content));
    }

}



