package com.uutic.uusale.config;

import com.uutic.uusale.exceptions.InvalidTokenException;
import com.uutic.uusale.exceptions.TokenNotFoundException;
import com.uutic.uusale.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    private Logger log = LoggerFactory.getLogger(WebConfig.class);

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/pic/**").addResourceLocations("file:/Users/Rick/Downloads/temp_images/");
//    }

    //DEV only
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**").allowedOrigins("http://localhost:8090");
//    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String authorization = request.getHeader("Authorization");
                if (authorization == null || authorization.isEmpty()) {
                    throw new TokenNotFoundException("Unauthorized");
                }
                if (!authorization.startsWith("Bearer ")) {
                    throw new InvalidTokenException("Invalid authorization header");
                }
                String token = authorization.substring(7);
                Map<String, String> claims = JwtUtil.decode(token);
                if (!claims.containsKey("user_id") || !claims.containsKey("user_type"))
                    throw new InvalidTokenException("Cannot find user information in Token");
                request.setAttribute("user_id", claims.get("user_id"));
                request.setAttribute("user_type", claims.get("user_type"));

                return super.preHandle(request, response, handler);
            }
        }).addPathPatterns("/api/**").excludePathPatterns("/api/user/**");
    }
}
