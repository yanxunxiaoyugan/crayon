package com.liu.gateway.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.handler.AbstractHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class RoutePredicateHandlerMapping  extends AbstractHandlerMapping {
    @Autowired
    private FilterWebHandler filterWebHandler;
    public RoutePredicateHandlerMapping(){
        setOrder(1);
    }

    @Override
    protected Mono<?> getHandlerInternal(ServerWebExchange serverWebExchange) {
        return Mono.just(filterWebHandler);
    }
}
