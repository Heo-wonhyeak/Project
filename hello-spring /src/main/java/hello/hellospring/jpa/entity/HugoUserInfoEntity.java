package hello.hellospring.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * DB 테이블과 직접적으로 연관되어지는 클래스, 꼭 테이블과 연관되어 사용 해야함
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "HUGO_USER_INFO")
public class HugoUserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "id")
    private String id;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "name")
    private String name;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "email")
    private String email;

    @Column(name = "birth_day")
    private Date birthDay;

    @Column(name = "gender")
    private String gender;

    @Column(name = "call_num")
    private String callNum;

    @Column(name = "interest")
    private String interest;

    @Column(name = "join_date")
    private Date joinDate;

}
