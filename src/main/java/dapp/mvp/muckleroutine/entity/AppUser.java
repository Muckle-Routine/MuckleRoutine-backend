package dapp.mvp.muckleroutine.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity //이 클래스는 JPA로 관리되는 엔티티 객체
@Getter //Lombok Getter Method 생성
@Builder //객체를 생성 가능
@AllArgsConstructor //컴파일 에러 처리
@NoArgsConstructor //컴파일 에러 처리
@DynamicInsert
public class AppUser extends BaseEntity{
    @Id
    private String walletAddress;

    @OneToOne
    @JoinColumn(name = "REQUEST_NO")
    private KlipRequest request = new KlipRequest();

    @ManyToMany
    @JoinTable(name = "JOIN_PARTI_ROUTINE",
       joinColumns = @JoinColumn(name="USER_NO"),
       inverseJoinColumns = @JoinColumn(name="ROUTINE_NO")
       )
    private List<Routine> participatedRoutine = new ArrayList<>();
}
