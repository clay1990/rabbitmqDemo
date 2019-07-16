package com.clay.rabbitmq.demo;

import com.alibaba.fastjson.JSON;
import com.clay.rabbitmq.demo.config.RabbitConfig;
import com.clay.rabbitmq.demo.producer.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * @Auther: yuyao
 * @Date: 2019/7/3 16:14
 * @Description:
 */

@RestController
@SpringBootApplication
@EnableConfigurationProperties(RabbitConfig.class)
public class RabbitMqApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RabbitMqApplication.class, args);
//        run.stop();
//        run.close();



    }


    @Autowired
    private Environment env;

    @Bean
    public CommandLineRunner init(){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                System.out.println(env.getProperty("spring.rabbitmq.host"));
            }
        };
    }

    @Bean
    public ApplicationRunner runner(){
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {

                System.out.println("ApplicationRunner : " + JSON.toJSONString(args));
            }
        };
    }


    @Autowired
    MsgProducer msgProducer;

//    @CrossOrigin
    @GetMapping("/a")
    public void produceA(HttpServletRequest request, HttpServletResponse response, String msg) throws IOException {
        Enumeration<Servlet> servlets = request.getServletContext().getServlets();
        System.out.println("come in ...");
        response.addHeader("Cache-Control","600");
        response.addHeader("Location","http://www.baidu.com");
        PrintWriter writer = response.getWriter();
        writer.write("server say: ok !");
        writer.flush();
        writer.close();
    }
    @GetMapping("/produce/b")
    public String produceB(String msg){
        msgProducer.sendMsgToB(msg);
        return "ok";
    }

    @GetMapping("/produce/c")
    public String produceC(String msg){
        msgProducer.sendMsgToC(msg);
        return "ok";
    }

    @GetMapping("/produce/sendAll")
    public String sendAll(String msg){
        msgProducer.sendAll(msg);
        return "ok";
    }


}