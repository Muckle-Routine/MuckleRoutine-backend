package dapp.mvp.muckleroutine.annotation;

import java.lang.annotation.*;

/*
 * 왜 필요할까?
 * REST API를 호출 할 때,
 * [기존]
 *  GET/POST method -> params -> Controller에서 @RequestParam
 *  POST/DELETE/PUT method 	-> formdata -> @RequestParam
 *  						-> (객체로 명시해둔 경우,) body의 json -> @RequestBody -> 객체 클래스
 *  
 *  그러던 어느날, 저는 front developer로 부터 
 *  각각의 method를 호출할 때마다 param, formdata, body(json)이
 *  규칙이 없다는 요청이 있었습니다.
 *  
 *  카카오의 API를 잘 살펴보았습니다.
 *  'x-www-form-urlencoded'로 객체를 받더군요.
 *  
 *  @RequestParamObject를 만들게 되었습니다.
 *  [현재]
 *  GET method -> params -> Controller에서 @RequestParam
 *  POST/DELETE/PUT method 	-> formdata -> @RequestParamObject
 *  						-> (객체로 명시해둔 경우,) formdata(value is JsonType) -> @RequestParamObject -> 객체 클래스
 *  
 *  차이가 보이시나요?
 *  method와 객체인지에 관계없이 'x-www-form-urlencoded'로 
 *  받을 수 있게 되었습니다.
 *  
 *  더 좋은 방법이 있을 수도 있겠지만,!
 *  제가할 수 있는 건
 *  이것이 최선이라고 생각합니다:)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface RequestParamObject {
    String value();
}