package dapp.mvp.muckleroutine.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
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
public class KlipRequest extends BaseEntity{

    @Id
    @Column(name = "REQUEST_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private KlipRequestType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private KlipRequestStatus status;
    public void setStatus(KlipRequestStatus status){
        this.status = status;
    }

    private String request;
    public void setRequest(String request){
        this.request = request;
    }

    private Long requestExpiredTime;
    public void setRequestExpiredTime(Long requestExpiredTime){
        this.requestExpiredTime = requestExpiredTime;
    }
}
