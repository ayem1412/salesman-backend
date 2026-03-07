package ma.ismagi.apigateway.config;

import ma.ismagi.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter
    extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
  @Autowired private RouteValidator validator;
  @Autowired private JwtUtil jwtUtil;

  public AuthenticationFilter() {
    super(Config.class);
  }

  public static class Config {}

  @Override
  public GatewayFilter apply(Config config) {
    return ((exchange, chain) -> {
      if (validator.isSecured.test(exchange.getRequest())) {
        if (!exchange.getRequest().getHeaders().containsHeader(HttpHeaders.AUTHORIZATION))
          throw new RuntimeException("Missing Authorization Header");

        String authHeader =
            exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        if (authHeader != null && authHeader.startsWith("Bearer "))
          authHeader = authHeader.substring(7);

        try {
          jwtUtil.validateToken(authHeader);
        } catch (Exception e) {
          throw new RuntimeException("Unauthorized access.");
        }
      }

      return chain.filter(exchange);
    });
  }
}
