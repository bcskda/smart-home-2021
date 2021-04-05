package ru.sbt.mipt.oop.ccadapt;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.handlers.NativeEventHandlerConfiguration;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Description("Adapts native event handlers for CoolCompany library")
public class CcAdaptConfiguration implements ApplicationContextAware {
    @Autowired private ApplicationContext applicationContext;
    @Autowired private SmartHome smartHome;

    @Bean(name = "ccAdaptedEventHandlers")
    public List<com.coolcompany.smarthome.events.EventHandler> ccAdaptedEventHandlers() {
        return applicationContext.getBean(NativeEventHandlerConfiguration.class)
                .getEventHandlers().stream()
                .map(handler -> new EventHandlerAdaptor(handler, smartHome))
                .collect(Collectors.toList());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
