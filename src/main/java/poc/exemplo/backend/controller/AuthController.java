package poc.exemplo.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthController {

    private final MemcachedClient memcachedClient;

    public AuthController(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password,
            HttpServletRequest request) {
        // Apenas POC
        if ("administrator".equals(username) && "12345".equals(password)) {
            String sessionId = request.getSession().getId();
            memcachedClient.set(sessionId, 3600, username);
            return ResponseEntity.ok(sessionId);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status(HttpServletRequest request) {
        String sessionId = request.getHeader("X-Session-Id");
        String username = (String) memcachedClient.get(sessionId);

        Map<String, Object> response = new HashMap<>();
        response.put("loggedIn", username != null);
        response.put("username", username);

        // Pega IP real do cliente
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        log.info("IP real do cliente => " + ip);
        response.put("ip", ip);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String sessionId = request.getHeader("X-Session-Id");
        memcachedClient.delete(sessionId);
        return ResponseEntity.ok("Logged out");
    }


     @PostMapping("/cadastro")
    public ResponseEntity<String> cadastro(HttpServletRequest request) {
        String sessionId = request.getHeader("X-Session-Id");
        memcachedClient.delete(sessionId);
        return ResponseEntity.ok("Cadastro de Usuário");
    }

}
