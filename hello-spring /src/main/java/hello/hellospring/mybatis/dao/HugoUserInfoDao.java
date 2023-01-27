package hello.hellospring.mybatis.dao;

import hello.hellospring.mybatis.model.HugoUserInfoModel;
import hello.hellospring.req.model.HugoLoginReqModel;
import hello.hellospring.req.model.HugoUserModifyReqModel;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface HugoUserInfoDao {

    @Select("SELECT * FROM HUGO_USER_INFO")
    List<HugoUserInfoModel> getHugoUserInfoList();

    @Select("SELECT count(*) FROM HUGO_USER_INFO WHERE id = #{id}")
    int getCountHugoUserInfoById(@Param("id") String id);

    @Insert("INSERT INTO HUGO_USER_INFO " +
            " (id, pwd, name, nick_name, email, birth_day, gender, call_num, interest) " +
            " VALUES " +
            "( #{id}, #{pwd}, #{name}, #{nickName}, #{email}, #{birthDay}, #{gender}, #{callNum}, #{interest} )" )
    @Options(useGeneratedKeys = true, keyProperty = "idx", keyColumn = "idx")
    void saveHugoUserInfo(HugoUserInfoModel hugoUserInfoModel);

    @Select("select * from hugo_user_info where id = #{id} and pwd= #{pwd} ")
    HugoUserInfoModel loginById(HugoLoginReqModel hugoUserInfoModel);

    @Select("select * from hugo_user_info where id = #{id} and pwd= #{pwd} ")
    HugoUserInfoModel loginByIdAndPassword(@Param("id") String id, @Param("pwd") String pwd);

    @Select("select * from hugo_user_info where id = #{id}")
    HugoUserInfoModel findUserById(@Param("id") String id);

    @Select("select * from hugo_user_info where id = #{id}")
    HugoUserInfoModel findHugoMemberInfoById(@PathVariable("id") String id);

    @Update("UPDATE HUGO_USER_INFO\n" +
            "SET\n" +
            "pwd = #{pwd },\n" +
            "name = #{name },\n" +
            "nick_name = #{nickName },\n" +
            "email = #{email },\n" +
            "birth_day = #{birthDay },\n" +
            "gender = #{gender },\n" +
            "call_num = #{callNum },\n" +
            "interest = #{interest,jdbcType=VARCHAR } \n" +
            "WHERE id = #{id }")
    void updateHugoUserInfo(HugoUserModifyReqModel hugoUserModifyReqModel);

    @Delete("DELETE FROM HUGO_USER_INFO WHERE id=#{id} and pwd=#{pwd}")
    void deleteHugoUserInfo(@Param(value = "id") String id ,@Param(value = "pwd") String pwd);

    //아이디로 비밀번호 가져와서 입력 비밀번호와 같은지 확인
    @Select("select pwd from hugo_user_info where id = #{id }")
    String pwdCheckById(@Param("id") String id);

    /**
     * 닉네임 유효성 검증
     * @param nickName
     * @return
     */
    @Select("select count(*) from hugo_user_info where nick_name = #{nickName }")
    int getHugoUserInfoByNickName(String nickName);
}
