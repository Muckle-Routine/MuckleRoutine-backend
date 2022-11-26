package dapp.mvp.muckleroutine.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity //이 클래스는 JPA로 관리되는 엔티티 객체
@Getter //Lombok Getter Method 생성
@Builder //객체를 생성 가능
@AllArgsConstructor //컴파일 에러 처리
@NoArgsConstructor //컴파일 에러 처리
@DynamicInsert
public class Board extends BaseEntity{

    @Id
    @Column(name = "BOARD_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false)
    private String contents;

    @OneToOne
    @JoinColumn(name = "USER_NO")
    private AppUser writer;
}
