package soongsil.kidbean.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootTest(classes = ServerApplicationTests.class)
class ServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
