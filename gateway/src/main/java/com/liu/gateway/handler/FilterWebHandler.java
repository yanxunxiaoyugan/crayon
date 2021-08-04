package com.liu.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.server.reactive.AbstractServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.NettyPipeline;
import reactor.ipc.netty.http.client.HttpClient;
import reactor.ipc.netty.http.client.HttpClientRequest;
import reactor.ipc.netty.http.client.HttpClientResponse;

import java.net.URI;
import java.time.Duration;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeoutException;

@Component
public class FilterWebHandler implements WebHandler {
    @Autowired
    HttpClient httpClient;
    WebClient webClient;
    public FilterWebHandler(){

            reactor.netty.http.client.HttpClient httpClient = reactor.netty.http.client.HttpClient.create()
                    .tcpConfiguration(client ->
                            client.doOnConnected(conn ->
                                    conn.addHandlerLast(new ReadTimeoutHandler(3))
                                            .addHandlerLast(new WriteTimeoutHandler(3)))
                                    .option(ChannelOption.TCP_NODELAY, true)
                    );
            webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient))
                    .build();

    }
    @Override
    public Mono<Void> handle(ServerWebExchange exchange) {
        //直接发起请求
        ServerHttpResponse exchangeResponse = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getURI().toString();

        String transferEncoding = request.getHeaders().getFirst(HttpHeaders.TRANSFER_ENCODING);
        boolean chunkedTransfer = "chunked".equalsIgnoreCase(transferEncoding);


        if(url.startsWith("http://localhost:8081")){
            url = "http://www.baidu.com";
        }
        WebClient.RequestBodySpec requestBodySpec = webClient.method(Objects.requireNonNull(request.getMethod())).uri(url).headers((headers) -> {
            headers.addAll(request.getHeaders());
            headers.remove("Host");
        });

        WebClient.RequestHeadersSpec<?> reqHeadersSpec;
        if (false) {
            reqHeadersSpec = requestBodySpec.body(BodyInserters.fromDataBuffers(request.getBody()));
        } else {
            reqHeadersSpec = requestBodySpec;
        }
        // nio->callback->nio
        return reqHeadersSpec.exchange().timeout( Duration.ofSeconds(4000,100))
                .onErrorResume(ex -> {
                    return Mono.defer(() -> {
                        String errorResultJson = "";
                        if (ex instanceof TimeoutException) {
                            errorResultJson = "{\"code\":5001,\"message\":\"network timeout\"}";
                        } else {
                            errorResultJson = "{\"code\":5000,\"message\":\"system error\"}";
                        }
//                        return  exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                                .bufferFactory().wrap(errorResultJson.getBytes())));
                    }).then(Mono.empty());
                }).flatMap(backendResponse -> {
                    exchangeResponse.setStatusCode(backendResponse.statusCode());
                    exchangeResponse.getHeaders().putAll(backendResponse.headers().asHttpHeaders());
                    return exchangeResponse.writeWith(backendResponse.bodyToFlux(DataBuffer.class));
                });
    }

    /**
     * 写死response
     * @param response
     * @return
     */
    private Flux<DataBuffer> createResponse(ServerHttpResponse response ){

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
        DataBuffer[] nettyDataBuffers = new DataBuffer[1];

        ObjectMapper mapper = new ObjectMapper();
        try {
            nettyDataBuffers[0] =  nettyDataBufferFactory.wrap(mapper.writeValueAsBytes("asdf"));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        final Flux<DataBuffer> body = Flux.just(nettyDataBuffers);
//        final Flux<NettyDataBuffer> body = clientResponse.receive()
//                .retain() //TODO: needed?
//                .map(factory::wrap);
        response.setStatusCode(HttpStatus.OK);
        return body;
    }
}
