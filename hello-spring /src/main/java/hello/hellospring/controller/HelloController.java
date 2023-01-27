package hello.hellospring.controller;

import hello.hellospring.service.TestService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(value = "*")
@RequestMapping(value = "/api/test")
@RestController
public class HelloController {

    @Autowired
    private TestService testService;

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("data","hello!!");
        return "hello";
    }

    @GetMapping("Hello-mvc")
    public String helloMvc(@RequestParam(value="name",required = false) String name,Model model) {
        model.addAttribute("name",name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello "+name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    @GetMapping("/test")
    public ResponseEntity<Object> getTest() {
        log.info("123");
        Hello hello = new Hello();
        hello.setName("12222");
        log.info(">>" + hello.getName());
        return ResponseEntity.ok(testService.getTest());
    }

    @Data
    static class Hello {
        private String name;
    }
}
