package dapp.mvp.muckleroutine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoutine is a Querydsl query type for Routine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoutine extends EntityPathBase<Routine> {

    private static final long serialVersionUID = -652810748L;

    public static final QRoutine routine = new QRoutine("routine");

    public final StringPath category = createString("category");

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
        super(Routine.class, forVariable(variable));
    }

    public QRoutine(Path<? extends Routine> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoutine(PathMetadata metadata) {
        super(Routine.class, metadata);
    }

}

