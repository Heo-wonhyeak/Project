package hello.hellospring.service;

import hello.hellospring.mybatis.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestDao testDao;

    public List<String> getTest() {
        return testDao.getTest();
    }
}
