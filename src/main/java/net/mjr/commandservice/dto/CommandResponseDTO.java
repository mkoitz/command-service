package net.mjr.commandservice.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;

@Data
@NoArgsConstructor
@ToString
public class CommandResponseDTO {

    private Date timestamp = new Date();
    private String status;
    private List<String> messages = new ArrayList<>();
    
}