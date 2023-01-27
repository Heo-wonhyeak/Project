package hello.hellospring.mybatis.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestDao {

    @Select("SELECT variable FROM sys.sys_config")
    List<String> getTest();


}
