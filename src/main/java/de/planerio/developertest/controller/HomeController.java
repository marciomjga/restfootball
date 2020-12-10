package de.planerio.developertest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class HomeController {

  @GetMapping
  public String index() {
    return "redirect:swagger-ui.html";
  }

  @GetMapping("/manage/health")
  public ResponseEntity health() {
    return ResponseEntity.ok("UP");
  }
}
