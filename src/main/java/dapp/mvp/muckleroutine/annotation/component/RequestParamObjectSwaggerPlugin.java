package dapp.mvp.muckleroutine.annotation.component;

import dapp.mvp.muckleroutine.annotation.RequestParamObject;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.Optional;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class RequestParamObjectSwaggerPlugin implements ParameterBuilderPlugin {

  @Override
  public void apply(ParameterContext parameterContext) {
    ResolvedMethodParameter parameter = parameterContext.resolvedMethodParameter();
    Optional<RequestParamObject> annotation = parameter.findAnnotation(RequestParamObject.class);

    if (annotation.isPresent()) {
      parameterContext
        .requestParameterBuilder()
        .in(ParameterType.FORM)
        .name(annotation.get().value())
        .required(Boolean.TRUE)
        .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)));
    }
  }

  @Override
  public boolean supports(DocumentationType delimiter) {
    return true;
  }
}