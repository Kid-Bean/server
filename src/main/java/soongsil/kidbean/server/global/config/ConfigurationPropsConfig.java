package soongsil.kidbean.server.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import soongsil.kidbean.server.auth.application.jwt.JwtProperties;

@Configuration
@EnableConfigurationProperties(value = JwtProperties.class)
public class ConfigurationPropsConfig {
}
