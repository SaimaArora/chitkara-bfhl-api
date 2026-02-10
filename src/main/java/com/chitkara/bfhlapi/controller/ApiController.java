package com.chitkara.bfhlapi.controller;

import com.chitkara.bfhlapi.service.AiService;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ApiController {

    private final AiService aiService;

    public ApiController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "is_success", true,
                "official_email", "saima1117.be23@chitkara.edu.in"
        );
    }
    @PostMapping("/bfhl")
    public Map<String, Object> bfhl(@RequestBody Map<String, Object> body) {

        if (body == null || body.size() != 1) {
            return Map.of(
                    "is_success", false,
                    "official_email", "saima1117.be23@chitkara.edu.in",
                    "message", "Request must contain exactly one key"
            );
        }

        String key = body.keySet().iterator().next();
        Object value = body.get(key);

        // ✅ Fibonacci
        if (key.equals("fibonacci")) {
            if (!(value instanceof Number)) {
                return Map.of(
                        "is_success", false,
                        "official_email", "saima1117.be23@chitkara.edu.in",
                        "message","fibonacci must be a number");
            }

            int n = ((Number) value).intValue();
            java.util.List<Integer> fib = new java.util.ArrayList<>();

            if (n > 0) {
                fib.add(0);
                if (n > 1) {
                    fib.add(1);
                    for (int i = 2; i < n; i++) {
                        fib.add(fib.get(i - 1) + fib.get(i - 2));
                    }
                }
            }

            return Map.of(
                    "is_success", true,
                    "official_email", "saima1117.be23@chitkara.edu.in",
                    "data", fib
            );
        }

        // ✅ Prime
        if (key.equals("prime")) {
            if (!(value instanceof java.util.List<?>)) {
                return Map.of(
                        "is_success", false,
                        "official_email", "saima1117.be23@chitkara.edu.in",
                        "message", "prime must be an array");
            }

            java.util.List<?> input = (java.util.List<?>) value;
            java.util.List<Integer> primes = new java.util.ArrayList<>();

            for (Object o : input) {
                if (o instanceof Number) {
                    int num = ((Number) o).intValue();
                    if (isPrime(num)) {
                        primes.add(num);
                    }
                }
            }

            return Map.of(
                    "is_success", true,
                    "official_email", "saima1117.be23@chitkara.edu.in",
                    "data", primes
            );
        }

        // ✅ HCF
        if (key.equals("hcf")) {
            if (!(value instanceof java.util.List<?>)) {
                return Map.of(
                        "is_success", false,
                        "official_email", "saima1117.be23@chitkara.edu.in",
                        "message", "hcf must be an array");
            }

            java.util.List<?> input = (java.util.List<?>) value;
            java.util.List<Integer> nums = new java.util.ArrayList<>();

            for (Object o : input) {
                if (o instanceof Number) {
                    nums.add(((Number) o).intValue());
                }
            }

            int result = nums.get(0);
            for (int i = 1; i < nums.size(); i++) {
                result = gcd(result, nums.get(i));
            }

            return Map.of(
                    "is_success", true,
                    "official_email", "saima1117.be23@chitkara.edu.in",
                    "data", result
            );
        }

        // ✅ LCM
        if (key.equals("lcm")) {
            if (!(value instanceof java.util.List<?>)) {
                return Map.of(
                        "is_success", false,
                        "official_email", "saima1117.be23@chitkara.edu.in",
                        "message","lcm must be an array");
            }

            java.util.List<?> input = (java.util.List<?>) value;
            java.util.List<Integer> nums = new java.util.ArrayList<>();

            for (Object o : input) {
                if (o instanceof Number) {
                    nums.add(((Number) o).intValue());
                }
            }

            int result = nums.get(0);
            for (int i = 1; i < nums.size(); i++) {
                result = lcm(result, nums.get(i));
            }

            return Map.of(
                    "is_success", true,
                    "official_email", "saima1117.be23@chitkara.edu.in",
                    "data", result
            );
        }

        // ✅ AI
        if (key.equals("AI")) {
            if (!(value instanceof String)) {
                return Map.of(
                        "is_success", false,
                        "official_email", "saima1117.be23@chitkara.edu.in",
                        "message","AI must be a string");
            }

            String answer = aiService.askAi((String) value);

            return Map.of(
                    "is_success", true,
                    "official_email", "saima1117.be23@chitkara.edu.in",
                    "data", answer
            );
        }

        // ❌ Invalid key
        return Map.of(
                "is_success", false,
                "official_email", "saima1117.be23@chitkara.edu.in",
                "message", "Invalid key"
        );

    }

// Helper methods

    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return Math.abs(a);
    }

    private int lcm(int a, int b) {
        return Math.abs(a * b) / gcd(a, b);
    }

}
