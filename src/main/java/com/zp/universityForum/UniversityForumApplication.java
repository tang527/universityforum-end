package com.zp.universityForum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


//@SpringBootApplication(scanBasePackages = {"com.zp.universityForum"})
@SpringBootApplication
public class UniversityForumApplication {

    public static void main(String[] args) {
//        System.setProperty("es.set.netty.runtime.available.processors","false");
        ConfigurableApplicationContext context = SpringApplication.run(UniversityForumApplication.class, args);
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(context);
        ac.close();
//        context.close();
    }

}
