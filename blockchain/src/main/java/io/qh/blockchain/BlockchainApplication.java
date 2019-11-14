package io.qh.blockchain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@MapperScan("io.qh.blockchains.dao")
//@EnableScheduling
@EnableAsync
public class BlockchainApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainApplication.class, args);
    }

}
