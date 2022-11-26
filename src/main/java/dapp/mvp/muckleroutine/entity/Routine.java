package dapp.mvp.muckleroutine.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;

@Entity //이 클래스는 JPA로 관리되는 엔티티 객체
@Getter //Lombok Getter Method 생성
@Builder //객체를 생성 가능
@AllArgsConstructor //컴파일 에러 처리
@NoArgsConstructor //컴파일 에러 처리
@DynamicInsert
public class Routine extends BaseEntity{

    @Id
    @Column(name = "ROUTINE_NO")
    private Long no;

    @OneToOne
    @JoinColumn(name = "USER_NO")
    private AppUser creator;

    @Column(nullable = false)
    private String title, description, category;

    @Column(nullable = false)
    private LocalDate startDate, endDate;

    private String image1;
    private String image2;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoutineTerm term;

    @Column(nullable = false)
    private Float fee;

    @Column(nullable = false)
    private Long skeleton;

    @ColumnDefault(value = "0")
    private Long participates;

}
