package dapp.mvp.muckleroutine.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity //이 클래스는 JPA로 관리되는 엔티티 객체
@Getter //Lombok Getter Method 생성
@Builder //객체를 생성 가능
@AllArgsConstructor //컴파일 에러 처리
@NoArgsConstructor //컴파일 에러 처리
@DynamicInsert
public class Certification extends BaseEntity{

    @Id
    @Column(name = "CERTIFICATION_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String certification;
    private String image;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'PROCESS'")
    private CertificationStatus status;
    public void setStatus(CertificationStatus status){
        this.status = status;
    }

    @Column(nullable = false)
    private LocalDateTime certificationDate;

    @ColumnDefault(value = "0")
    private Long voteCount;
    private void addVoteCount(){
        this.voteCount++;
    }
    @ColumnDefault(value = "0")
    private Long failCount;
    public void addFailCount(){
        this.failCount++;
        addVoteCount();
    }
    @ColumnDefault(value = "0")
    private Long successCount;
    public void addSuccessCount(){
        this.successCount++;
        addVoteCount();
    }

    @ManyToMany
    @JoinTable(name = "JOIN_FAIL_REASON",
       joinColumns = @JoinColumn(name="CERTIFICATION_NO"),
       inverseJoinColumns = @JoinColumn(name="BOARD_NO")
       )
    private List<Board> failReasons = new ArrayList<>();
    public void addFailReason(Board reason){
        failReasons.add(reason);
    }

    @OneToOne
    @JoinColumn(name = "ROUTINE_NO")
    private Routine routine = new Routine();
}
