package com.msb;

import com.msb.bean.PasswordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * description : 不使用spring的启动
 *
 * @author kunlunrepo
 * date :  2023-12-05 13:56
 */
public class StandaloneApp {

    private static final Logger log = LoggerFactory.getLogger(StandaloneApp.class);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        HttpHandler handler = RouterFunctions.toHttpHandler(
                routes(
                        // 这里耗时
                        new BCryptPasswordEncoder(18)
                )
        );

        // 内置HttpHandler适配器
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);

        // 创建HTTPServer实例，它是ReactorNettyAPI一部分
        DisposableServer server = HttpServer.create()
                .host("localhost")
                .port(8080)
                .handle(adapter)
                .bindNow();// 启动服务

        log.info("耗时： {}", System.currentTimeMillis() - start);

        // 使应用保持活动状态，监听服务器处理
        server.onDispose().block();

    }

    /**
     * 将 RouterFunction 转化为 HttpHandler
     * @param passwordEncoder
     * @return
     */
    private static RouterFunction<?> routes(BCryptPasswordEncoder passwordEncoder) {

        return
                route(POST("password"),
                        req -> req.bodyToMono(PasswordDTO.class)
                                .doOnNext(System.out::println)
                                // 检查密码
                                .map(p -> passwordEncoder.matches(p.getRaw(), p.getSecured()))
                                // 密码匹配成功返回成功，反之则反
                                .flatMap(im -> im? ServerResponse.ok().build():
                                        ServerResponse.status(HttpStatus.EXPECTATION_FAILED).build()
                                        )
                );

    }

}
