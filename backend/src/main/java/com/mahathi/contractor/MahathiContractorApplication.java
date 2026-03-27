package com.mahathi.contractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MahathiContractorApplication {
    
    public static void main(String[] args) {
        // Check if running on Railway (production)
        String profile = System.getenv("RAILWAY_ENVIRONMENT") != null ? "prod" : "default";
        
        SpringApplication app = new SpringApplication(MahathiContractorApplication.class);
        app.setAdditionalProfiles(profile);
        app.run(args);
    }
}
