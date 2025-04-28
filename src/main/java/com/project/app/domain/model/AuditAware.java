package com.project.app.domain.model;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditAware  implements AuditorAware<String>{

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(" system"); // Replace with actual user retrieval logic
    }
    
}
