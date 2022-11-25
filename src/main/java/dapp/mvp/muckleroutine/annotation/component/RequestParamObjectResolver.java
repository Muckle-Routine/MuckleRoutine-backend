package dapp.mvp.muckleroutine.annotation.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import dapp.mvp.muckleroutine.annotation.RequestParamObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@Component
public class RequestParamObjectResolver implements HandlerMethodArgumentResolver {
 
    @Autowired
    private ObjectMapper mapper;
 
 
    @Override
    public boolean supportsParameter(final MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(RequestParamObject.class) != null;
    }
 
 
    @Override
    public Object resolveArgument(final MethodParameter methodParameter,
                                  final ModelAndViewContainer modelAndViewContainer,
                                  final NativeWebRequest nativeWebRequest,
                                  final WebDataBinderFactory webDataBinderFactory) throws Exception {
    	
        final RequestParamObject attr = methodParameter.getParameterAnnotation(RequestParamObject.class);
        String json = nativeWebRequest.getParameter(attr.value());
        
        if(methodParameter.getParameterType().toString().equals("interface java.util.List")) {
        	final List<Object> objects = (List<Object>) mapper.readValue(json, methodParameter.getParameterType());
        	final List<Object> result = new ArrayList<>();
        	
        	for(Object obejct : objects) {
        		json = mapper.writeValueAsString(obejct);
        		ParameterizedType stringListType = (ParameterizedType) methodParameter.getGenericParameterType();
                Class<?> elementClass = (Class<?>) stringListType.getActualTypeArguments()[0];
                result.add(mapper.readValue(json, elementClass));
        	}
        	return result;
        }else {
        	final Object result = mapper.readValue(json, methodParameter.getParameterType());
        	return result;
        }
    }
    
}