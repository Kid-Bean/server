package soongsil.kidbean.server.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = {"soongsil.kidbean.server"})
@Configuration
public class OpenFeignConfig {

}
