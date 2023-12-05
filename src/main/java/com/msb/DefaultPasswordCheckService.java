package com.msb;

import com.msb.bean.PasswordDTO;
import com.sun.deploy.security.BadCertificateDialog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-05 14:25
 */
public class DefaultPasswordCheckService implements PasswordCheckService{

    WebClient webClient;

    public DefaultPasswordCheckService(WebClient.Builder webBuilder) {
        this.webClient = webBuilder.baseUrl("http://localhost:8080")
                .build();
    }

    @Override
    public Mono<Void> check(String raw, String secured) {
//        return webClient.post();

        return WebClient.create("http://localhost:8080")
                .post()
                .uri("/password")
                .body(
                        BodyInserters.fromPublisher(
                                Mono.just(new PasswordDTO(raw, secured)), PasswordDTO.class)
                        )
                        // 指定结果的处理方式
                        .retrieve()
                        .toEntityFlux(ResponseEntity.class)
                .flatMap( res -> {
                    if (res.getStatusCode().is2xxSuccessful()) {
                        return Mono.empty();
                    } else if (res.getStatusCode() == HttpStatus.EXPECTATION_FAILED) {
                        return Mono.error(new RuntimeException("无效的凭证"));
                    }
                    return Mono.error(new IllegalAccessException("非法参数"));
                });
    }
}
