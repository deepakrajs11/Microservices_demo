package com.deepakraj.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {


	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	// static imports from GatewayFilters and RoutePredicates
	@Bean
	public RouteLocator sampleBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/samplebank/accounts/**")
						.filters( f -> f.rewritePath("/samplebank/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.circuitBreaker(config -> config.setName("accountsCircuitBreaker").setFallbackUri("/accounts/fallback")))
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/samplebank/loans/**")
						.filters( f -> f.rewritePath("/samplebank/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET).setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/samplebank/cards/**")
						.filters( f -> f.rewritePath("/samplebank/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS")).build();


	}

}
