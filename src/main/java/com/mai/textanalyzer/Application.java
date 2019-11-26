package com.mai.textanalyzer;

import com.mai.textanalyzer.web.vaadin.pages.classification.LoadingComponents;
import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ServletComponentScan
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer{   
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        startH2Server();    
        return application.sources(Application.class);
    }  
    
    public static void main(String[] args) {
        startH2Server();     
        LoadingComponents.getInstance();
        SpringApplication.run(Application.class, args);
    }
    
    private static void startH2Server() {          
        try {
            Server.createTcpServer().stop(); 
            
            Server h2Server = Server.createTcpServer().start();
            if (h2Server.isRunning(true)) {
                System.out.println("H2 server was started and is running.");
            } else {
                throw new RuntimeException("Could not start H2 server.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to start H2 server: ", e);
        }
    }    
}
