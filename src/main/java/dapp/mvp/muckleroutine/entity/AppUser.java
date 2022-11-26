package dapp.mvp.muckleroutine.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@DynamicInsert
public class AppUser extends BaseEntity{
    @Id
    private String walletAddress;

    @OneToOne
    @JoinColumn(name = "REQUEST_NO")
    private KlipRequest request;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "JOIN_PARTI_ROUTINE",
       joinColumns = @JoinColumn(name="USER_NO"),
       inverseJoinColumns = @JoinColumn(name="ROUTINE_NO")
       )
    private List<Routine> participatedRoutine;
    public void addParticipatedRoutine(Routine routine){
        if(routine != null && !this.participatedRoutine.contains(routine)){
            this.participatedRoutine.add(routine);
        }
    }
}
