//package com.corsair.sparrow.pirate.gateway.filter;
//
//import io.netty.buffer.ByteBufAllocator;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.core.io.buffer.NettyDataBufferFactory;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.nio.CharBuffer;
//import java.nio.charset.StandardCharsets;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicReference;
//
///**
// * @author jack
// */
//@Slf4j
//@Component
//public class GatewayAuthFilter implements GlobalFilter, Ordered {
//
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest serverHttpRequest = exchange.getRequest();
//        HttpMethod httpMethod = serverHttpRequest.getMethod();
//        log.info("RequestURI Path: {}",serverHttpRequest.getURI().getPath());
//        switch (httpMethod){
//            case GET:
//                Map requestQueryParams = serverHttpRequest.getQueryParams();
//                log.info("GET 请求中的参数集合:{}",requestQueryParams);
//                // todo 完善get请求中的后续操作
//                return chain.filter(exchange);
//            case POST:
//                String requestBodyStr = resolveRequestBody(serverHttpRequest);
//                log.info("POST 请求参数body:{}",requestBodyStr);
//                URI uri = serverHttpRequest.getURI();
//                ServerHttpRequest request = serverHttpRequest.mutate().uri(uri).build();
//                DataBuffer bodyDataBuffer = stringBodyBuffer(requestBodyStr);
//
//                Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
//
//                request = new ServerHttpRequestDecorator(request){
//                    @Override
//                    public Flux<DataBuffer> getBody(){
//                     return bodyFlux;
//                    }
//                };
//                return chain.filter(exchange.mutate().request(request).build());
//            default:
//                log.info("{} 方式请求",httpMethod);
//                // todo 针对其他rest请求做操作
//                return chain.filter(exchange);
//        }
//    }
//
//    private DataBuffer stringBodyBuffer(String requestBodyStr) {
//        byte[] bytes = requestBodyStr.getBytes(StandardCharsets.UTF_8);
//        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
//        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
//        buffer.write(bytes);
//        return buffer;
//    }
//
//    /**
//     * 从Flux<DataBuffer>中获取参数字符串
//     * @param serverHttpRequest
//     * @return
//     */
//    private String resolveRequestBody(ServerHttpRequest serverHttpRequest) {
//        Flux<DataBuffer> body = serverHttpRequest.getBody();
//        AtomicReference<String> stringAtomicReference = new AtomicReference<>();
//        body.subscribe(dataBuffer -> {
//            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
//            DataBufferUtils.release(dataBuffer);
//            stringAtomicReference.set(charBuffer.toString());
//        });
//        return stringAtomicReference.get();
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
