package joa.tech11.log;


import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

public class Logger {

    @Produces
    public org.jboss.logging.Logger log(InjectionPoint injectionPoint){
        return org.jboss.logging.Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getSimpleName());
    }
}
