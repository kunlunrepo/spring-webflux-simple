package com.msb;

import com.msb.handerl.OrderHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
@Slf4j
public class SpringWebfluxSimpleApp {

    public static void main(String[] args) {
        log.info("【WebfluxApp】启动中...");
        SpringApplication.run(SpringWebfluxSimpleApp.class);
        log.info("【WebfluxApp】启动成功");
    }

    @Bean
    public RouterFunction<ServerResponse> routes(OrderHandler orderHandler) {
        /**
         * 参数1.断言
         * 参数2.断言成功后跳转的路由
         */
        return RouterFunctions.nest(
                // 判断请求路径是否包含指定的前缀
                RequestPredicates.path("/orders"),

                RouterFunctions.nest(
                        // 判断请求报文头字段是否匹配
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),

                        RouterFunctions.route(
                                // 如果匹配到{id}, 则路由到get
                                RequestPredicates.GET("/{id}"),
                                        orderHandler::get)

                                .andRoute(
                                        // 如果get请求 /orders, 则路由到list
                                        RequestPredicates.method(HttpMethod.GET),
                                        orderHandler::list)

                                .andNest(
                                        // 如果contentType匹配，并路径匹配orders,则路由到这个函数
                                        RequestPredicates.contentType(MediaType.APPLICATION_JSON),
                                        RouterFunctions.route(RequestPredicates.POST("/"), orderHandler::create))
                )
        );
    }

}