package poc.exemplo.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/status")
public class APIController {

    @GetMapping("/version")
    public ResponseEntity<String> status(HttpServletRequest request) {
        return ResponseEntity.ok("Versão 1.0.0");
    }

}
