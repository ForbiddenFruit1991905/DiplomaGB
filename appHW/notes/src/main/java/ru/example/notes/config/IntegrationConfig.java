package ru.example.notes.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class IntegrationConfig {

    private MeterRegistry meterRegistry;

    public IntegrationConfig(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    /**
     * Счётчик заметок
     * @param meterRegistry
     * @return
     */
    @Bean
    public Counter counter(MeterRegistry meterRegistry){
        return Counter.builder("note_counter")
                .description("Counter of notes in the repository")
                .register(meterRegistry);
    }

//    /**
//     * Таймер для замера пользовательских запросов к сайту
//     * @param meterRegistry
//     * @return
//     */
//    @Bean
//    public Timer timer(MeterRegistry meterRegistry){
//        return Timer.builder("user_process_timer")
//                .description("Timer for processing user requests")
//                .register(meterRegistry);
//    }

    @Bean
    public MessageChannel textInputChanel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel fileWriterChanel() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "textInputChanel", outputChannel = "fileWriterChanel")
    public GenericTransformer<String, String> mainTransformer() {
        return text -> {
            String cleanedText = text.replaceAll("[{}]", "") 
                    .replaceFirst("Note", "");
            return cleanedText;
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChanel")
    public FileWritingMessageHandler messageHandler() {
        FileWritingMessageHandler handler =
                new FileWritingMessageHandler(new File(
                        "D:/GB/2_РАЗРАБОТЧИК/ПРОГРАММИСТ/II четверть/4. Spring/Spring/DiplomaGB/hw/appHW/notes/src"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);

        return handler;
    }
}