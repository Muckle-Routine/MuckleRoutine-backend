package dapp.mvp.muckleroutine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;

@EnableAsync
@SpringBootApplication
@EnableJpaAuditing //BaseEntity를 상속받기 위해서 추가
public class MuckleRoutineApplication{
    public static void main(String[] args) {
        SpringApplication.run(MuckleRoutineApplication.class, args);
    }

}
