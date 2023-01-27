package hello.hellospring.service;

import hello.hellospring.jpa.entity.HugoUserInfoEntity;
import hello.hellospring.jpa.entity.repository.HugoUserInfoEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JpaTestService {

    @Autowired
    private HugoUserInfoEntityRepository hugoUserInfoEntityRepository;

    public List<HugoUserInfoEntity> findUserInfoList() {
        return hugoUserInfoEntityRepository.findAll();
    }
}
