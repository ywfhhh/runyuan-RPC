package com.ywf;

import com.ywf.annotations.EnableRpc;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableRpc(needServer = false)
public class Consumer {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
