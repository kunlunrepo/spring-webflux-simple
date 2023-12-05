package com.msb;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-05 15:20
 */
public class StandaloneTest {

    @Test
    public void checkAppRunning() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(18);
        DefaultPasswordCheckService service = new DefaultPasswordCheckService(WebClient.builder());
        StepVerifier.create(service.check("test", encoder.encode("test")))
                .expectSubscription()
                .expectComplete()
                .verify(Duration.ofSeconds(30));
    }
}
