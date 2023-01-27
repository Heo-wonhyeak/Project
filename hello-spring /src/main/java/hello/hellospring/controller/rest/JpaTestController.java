package hello.hellospring.controller.rest;

import hello.hellospring.jpa.entity.HugoUserInfoEntity;
import hello.hellospring.res.model.ApiResultObjectDto;
import hello.hellospring.service.JpaTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin(value = "*")
@RequestMapping(value = "/api/jpa")
@RestController
public class JpaTestController {

    @Autowired
    private JpaTestService jpaTestService;

    @GetMapping(value = "/list/all")
    public ResponseEntity<Object> getAllMemberList() {
        List<HugoUserInfoEntity> userList = jpaTestService.findUserInfoList();
        return ResponseEntity.ok(userList);
    }
}
