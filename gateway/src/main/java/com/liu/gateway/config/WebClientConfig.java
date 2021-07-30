package com.liu.gateway.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import reactor.ipc.netty.http.client.HttpClient;
import reactor.ipc.netty.http.client.HttpClientOptions;
import reactor.ipc.netty.options.ClientProxyOptions;
import reactor.ipc.netty.resources.PoolResources;

import java.util.function.Consumer;
import static com.liu.gateway.config.HttpClientProperties.Pool.PoolType.DISABLED;
import static com.liu.gateway.config.HttpClientProperties.Pool.PoolType.FIXED;
@Configuration
public class WebClientConfig {
    @Bean
    @ConditionalOnMissingBean
    public HttpClient httpClient(@Qualifier("nettyClientOptions") Consumer<? super HttpClientOptions.Builder> options) {
        return HttpClient.create(options);
    }
    @Bean
    public HttpClientProperties httpClientProperties() {
        return new HttpClientProperties();
    }

    @Bean
    public Consumer<? super HttpClientOptions.Builder> nettyClientOptions(HttpClientProperties properties) {
        return opts -> {

            if (properties.getConnectTimeout() != null) {
                opts.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getConnectTimeout());
            }

            // configure ssl
            HttpClientProperties.Ssl ssl = properties.getSsl();

            if (ssl.isUseInsecureTrustManager()) {
                opts.sslSupport(sslContextBuilder -> {
                    sslContextBuilder.trustManager(InsecureTrustManagerFactory.INSTANCE);
                });
            }

            // configure pool resources
            HttpClientProperties.Pool pool = properties.getPool();

            if (pool.getType() == DISABLED) {
                opts.disablePool();
            } else if (pool.getType() == FIXED) {
                PoolResources poolResources = PoolResources.fixed(pool.getName(),
                        pool.getMaxConnections(), pool.getAcquireTimeout());
                opts.poolResources(poolResources);
            } else {
                PoolResources poolResources = PoolResources.elastic(pool.getName());
                opts.poolResources(poolResources);
            }


            // configure proxy if proxy host is set.
            HttpClientProperties.Proxy proxy = properties.getProxy();
            if (StringUtils.hasText(proxy.getHost())) {
                opts.proxy(typeSpec -> {
                    ClientProxyOptions.Builder builder = typeSpec
                            .type(ClientProxyOptions.Proxy.HTTP)
                            .host(proxy.getHost());

                    PropertyMapper map = PropertyMapper.get();

                    map.from(proxy::getPort)
                            .whenNonNull()
                            .to(builder::port);
                    map.from(proxy::getUsername)
                            .whenHasText()
                            .to(builder::username);
                    map.from(proxy::getPassword)
                            .whenHasText()
                            .to(password -> builder.password(s -> password));
                    map.from(proxy::getNonProxyHostsPattern)
                            .whenHasText()
                            .to(builder::nonProxyHosts);

                    return builder;
                });
            }
        };
    }
}
