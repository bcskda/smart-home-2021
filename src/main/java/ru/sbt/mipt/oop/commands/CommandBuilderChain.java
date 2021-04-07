package ru.sbt.mipt.oop.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class CommandBuilderChain {
    @Autowired BaseCommandBuilder[] commandBuilders;

    @Bean
    public CommandBuilder commandBuilder() {
        CommandBuilder builderChain = new BaseCommandBuilder();
        for (BaseCommandBuilder commandBuilder : commandBuilders) {
            builderChain = builderChain.setNext(commandBuilder);
        }
        return builderChain;
    }
}
