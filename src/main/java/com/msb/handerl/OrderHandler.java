package com.msb.handerl;

import com.msb.bean.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-01 19:35
 */
@Service
public class OrderHandler {

    private Map<String, Order> orderMap = new HashMap<>();

    /**
     * 创建订单
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Order.class)
                .doOnNext(order -> {
                    System.out.println("=============");
                    orderMap.put(order.getId(), order);
                    System.out.println(orderMap);
                })
                .flatMap(order ->
                    ServerResponse.created(
                            URI.create("/order/"+order.getId())
                    ).build()
                );
    }

    /**
     * 获取订单信息
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Order order1 = orderMap.get(id);
        return Mono.just(order1)
                .flatMap(order ->
                        ServerResponse.ok().syncBody(order))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * 获取订单列表
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        return Mono.just(orderMap.values().stream().collect(Collectors.toList()))
                .flatMap(order -> ServerResponse.ok().syncBody(order))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
