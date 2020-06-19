package net.mjr.commandservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import net.mjr.commandservice.servcice.CommandService;
import javax.validation.constraints.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/commands")
public class CommandResource {

    private final CommandService commandService;

    @Autowired
    public CommandResource(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping(value = "/{commandKey}", produces = "application/json")
    public @ResponseBody ResponseEntity<String> executeCommand(@PathVariable @NotNull final String commandKey) {
        Optional<String> commandReturn = this.commandService.executeCommand(commandKey);
        return ResponseEntity.ok().body(commandReturn.orElse("Empty"));
    }
}