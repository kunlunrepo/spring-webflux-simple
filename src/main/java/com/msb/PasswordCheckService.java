package com.msb;

import reactor.core.publisher.Mono;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-05 14:24
 */
public interface PasswordCheckService {

    Mono<Void> check(String raw, String secured);

}
