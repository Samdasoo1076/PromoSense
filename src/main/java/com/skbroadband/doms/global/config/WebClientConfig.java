package com.skbroadband.doms.global.config;

import com.skbroadband.doms.global.utils.ThrowingConsumer;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.config
 * @File : WebClientConfig
 * @Program :
 * @Date : 2022-11-24
 * @Comment :
 */
@Slf4j
//@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer ->
                        clientCodecConfigurer.defaultCodecs()
                                .maxInMemorySize(1024*1024*3))
                .build();

        exchangeStrategies.messageWriters().stream()
                .filter(LoggingCodecSupport.class::isInstance)
                .forEach(httpMessageWriter ->
                    ((LoggingCodecSupport) httpMessageWriter).setEnableLoggingRequestDetails(true)
                );

        ReactorClientHttpConnector reactorClientHttpConnector = new ReactorClientHttpConnector(
                HttpClient.create()
                        .secure(
                            ThrowingConsumer.unchecked(
                                    sslContextSpec ->
                                            sslContextSpec.sslContext(SslContextBuilder.forClient()
                                                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build())
                            )
                        )
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5_000)
                        .responseTimeout(Duration.ofMillis(5_000))
                        .doOnConnected(connection ->
                            connection.addHandlerLast(new ReadTimeoutHandler(5_000, TimeUnit.MILLISECONDS))
                                    .addHandlerLast(new WriteTimeoutHandler(5_000, TimeUnit.MILLISECONDS))
                        )
        );

        return WebClient.builder()
                .clientConnector(reactorClientHttpConnector)
                .exchangeStrategies(exchangeStrategies)
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                        clientRequest -> {
                            log.debug("request: {} {}", clientRequest.method(), clientRequest.url());
                            return Mono.just(clientRequest);
                        }
                ))
                .filter(ExchangeFilterFunction.ofResponseProcessor(
                        clientResponse -> {
                            clientResponse.headers().asHttpHeaders()
                                    .forEach((name, values) -> {
                                        values.forEach(value ->
                                            log.debug("{}: {}", name, value)
                                        );
                                    });
                            return Mono.just(clientResponse);
                        }
                ))
                .defaultHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.3")
                .build();
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer(){
        return customizer -> customizer.addConnectorCustomizers(connector -> {
            connector.setAllowTrace(true);
        });
    }
}
