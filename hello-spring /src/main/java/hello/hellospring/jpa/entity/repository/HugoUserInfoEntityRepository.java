package hello.hellospring.jpa.entity.repository;

import hello.hellospring.jpa.entity.HugoUserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DB 맵핑되어있는 Entity 클래스를 interface로 사용할 수 있는 클래스
 * ex) JpaRepository 상속받아 Entity, 인덱스값(Entity 클래스안에 @Id로 사용하는 인덱스 값을 무조건 선언 해야함)
 */
public interface HugoUserInfoEntityRepository extends JpaRepository<HugoUserInfoEntity, Long> {
}
