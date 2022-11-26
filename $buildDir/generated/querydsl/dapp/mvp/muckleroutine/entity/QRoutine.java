package dapp.mvp.muckleroutine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoutine is a Querydsl query type for Routine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoutine extends EntityPathBase<Routine> {

    private static final long serialVersionUID = -652810748L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoutine routine = new QRoutine("routine");

    public final StringPath category = createString("category");

    public final QAppUser creator;

    public final StringPath description = createString("description");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Float> fee = createNumber("fee", Float.class);

    public final StringPath image1 = createString("image1");

    public final StringPath image2 = createString("image2");

    public final NumberPath<Long> no = createNumber("no", Long.class);

    public final NumberPath<Long> participates = createNumber("participates", Long.class);

    public final NumberPath<Long> skeleton = createNumber("skeleton", Long.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final EnumPath<RoutineTerm> term = createEnum("term", RoutineTerm.class);

    public final StringPath title = createString("title");

    public QRoutine(String variable) {
        this(Routine.class, forVariable(variable), INITS);
    }

    public QRoutine(Path<? extends Routine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoutine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoutine(PathMetadata metadata, PathInits inits) {
        this(Routine.class, metadata, inits);
    }

    public QRoutine(Class<? extends Routine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new QAppUser(forProperty("creator"), inits.get("creator")) : null;
    }

}

