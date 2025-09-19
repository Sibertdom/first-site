package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository; // Додано новий репозиторій

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("messages", messageRepository.findAll()); // Додано отримання всіх повідомлень
        return "index";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        User user = new User(username, email, password);
        userRepository.save(user);

        System.out.println("Користувача збережено!");
        return "success";
    }

    // Додано новий метод для обробки повідомлень
    @PostMapping("/messages")
    public String addMessage(@RequestParam String text) {
        Message message = new Message();
        message.setText(text);
        messageRepository.save(message);
        return "redirect:/"; // Перенаправлення на головну сторінку
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("user", user);
            return "dashboard";
        } else {
            return "index";
        }
    }
}