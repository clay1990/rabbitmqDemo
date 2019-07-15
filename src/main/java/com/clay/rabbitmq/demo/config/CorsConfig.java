package com.clay.rabbitmq.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: yuyao
 * @Date: 2019/7/9 15:27
 * @Description:  跨域配置
 */
@Configuration
public class CorsConfig {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private CorsConfiguration buildConfig(){
        Executors.newFixedThreadPool(1000);
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**" , buildConfig());
        return new CorsFilter(source);
    }

    @PostConstruct
    public void init(){
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    Thread.currentThread().sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("执行 ================= " );
            });
        }
    }


    @PreDestroy
    public void destory(){
        System.err.println("对象销毁....");
        executorService.shutdown();
    }

}