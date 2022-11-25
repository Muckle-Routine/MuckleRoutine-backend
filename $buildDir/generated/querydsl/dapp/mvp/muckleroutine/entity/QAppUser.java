package dapp.mvp.muckleroutine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAppUser is a Querydsl query type for AppUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAppUser extends EntityPathBase<AppUser> {

    private static final long serialVersionUID = 1462593228L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppUser appUser = new QAppUser("appUser");

    public final ListPath<Routine, QRoutine> participatedRoutine = this.<Routine, QRoutine>createList("participatedRoutine", Routine.class, QRoutine.class, PathInits.DIRECT2);

    public final QKlipRequest request;

    public final StringPath walletAddress = createString("walletAddress");

    public QAppUser(String variable) {
        this(AppUser.class, forVariable(variable), INITS);
    }

    public QAppUser(Path<? extends AppUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAppUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAppUser(PathMetadata metadata, PathInits inits) {
        this(AppUser.class, metadata, inits);
    }

    public QAppUser(Class<? extends AppUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.request = inits.isInitialized("request") ? new QKlipRequest(forProperty("request")) : null;
    }

}

