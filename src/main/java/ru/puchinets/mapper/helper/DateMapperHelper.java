package ru.puchinets.mapper.helper;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateMapperHelper {

    public String fromDto(LocalDateTime dateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        return dateTime.format(dtf);
    }
}
