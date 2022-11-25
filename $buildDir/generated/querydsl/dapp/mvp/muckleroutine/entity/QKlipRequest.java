package dapp.mvp.muckleroutine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKlipRequest is a Querydsl query type for KlipRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKlipRequest extends EntityPathBase<KlipRequest> {

    private static final long serialVersionUID = -1234565017L;

    public static final QKlipRequest klipRequest = new QKlipRequest("klipRequest");

    public final NumberPath<Long> no = createNumber("no", Long.class);

    public final StringPath request = createString("request");

    public final NumberPath<Long> requestExpiredTime = createNumber("requestExpiredTime", Long.class);

    public final EnumPath<KlipRequestStatus> status = createEnum("status", KlipRequestStatus.class);

    public final EnumPath<KlipRequestType> type = createEnum("type", KlipRequestType.class);

    public QKlipRequest(String variable) {
        super(KlipRequest.class, forVariable(variable));
    }

    public QKlipRequest(Path<? extends KlipRequest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKlipRequest(PathMetadata metadata) {
        super(KlipRequest.class, metadata);
    }

}

