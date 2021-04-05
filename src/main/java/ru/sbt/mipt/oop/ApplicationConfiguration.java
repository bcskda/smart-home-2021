package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.ccadapt.CcAdaptConfiguration;

import java.io.IOException;
import java.nio.file.Paths;

@Configuration
@ComponentScan
public class ApplicationConfiguration implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Bean
    public SmartHome smartHome() {
        // считываем состояние дома из файла
        String filename = "smart-home-1.json";
        try {
            return new JsonConfigurationReader(Paths.get(filename)).readSmartHome();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SensorEventsManager sensorEventsManager() {
        SensorEventsManager eventsManager = new SensorEventsManager();
        applicationContext.getBean(CcAdaptConfiguration.class)
                .ccAdaptedEventHandlers()
                .forEach(eventsManager::registerEventHandler);
        return eventsManager;
    }

    @Override
    @Required
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
