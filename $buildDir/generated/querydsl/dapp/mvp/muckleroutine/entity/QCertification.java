package dapp.mvp.muckleroutine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCertification is a Querydsl query type for Certification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCertification extends EntityPathBase<Certification> {

    private static final long serialVersionUID = 455862778L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCertification certification1 = new QCertification("certification1");

    public final StringPath certification = createString("certification");

    public final DateTimePath<java.time.LocalDateTime> certificationDate = createDateTime("certificationDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> failCount = createNumber("failCount", Long.class);

    public final ListPath<Board, QBoard> failReasons = this.<Board, QBoard>createList("failReasons", Board.class, QBoard.class, PathInits.DIRECT2);

    public final StringPath image = createString("image");

    public final NumberPath<Long> no = createNumber("no", Long.class);

    public final QRoutine routine;

    public final EnumPath<CertificationStatus> status = createEnum("status", CertificationStatus.class);

    public final NumberPath<Long> successCount = createNumber("successCount", Long.class);

    public final NumberPath<Long> voteCount = createNumber("voteCount", Long.class);

    public QCertification(String variable) {
        this(Certification.class, forVariable(variable), INITS);
    }

    public QCertification(Path<? extends Certification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCertification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCertification(PathMetadata metadata, PathInits inits) {
        this(Certification.class, metadata, inits);
    }

    public QCertification(Class<? extends Certification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.routine = inits.isInitialized("routine") ? new QRoutine(forProperty("routine")) : null;
    }

}

