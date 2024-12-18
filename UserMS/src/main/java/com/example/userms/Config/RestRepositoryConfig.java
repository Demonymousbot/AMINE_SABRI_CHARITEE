package com.example.userms.Config;


import com.example.userms.Entity.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
@Configuration
public interface RestRepositoryConfig extends RepositoryRestConfigurer {
   // @Override
    //public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        // Exposer l'ID de l'entité User
       // config.exposeIdsFor(User.class);
 //  }
}
