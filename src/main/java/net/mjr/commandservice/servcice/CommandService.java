package net.mjr.commandservice.servcice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import net.mjr.commandservice.config.CommandsConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;

@Service
public class CommandService {

    private final CommandsConfig config;

    @Autowired
    public CommandService(CommandsConfig config) {
        this.config = config;
    }

    public Optional<String> executeCommand(final String commandKey) {
        final String os = System.getProperty("os.name");
        System.out.println("OS: "+ os +", commandKey: "+ commandKey);
        boolean isWindows = os.toLowerCase().startsWith("windows");

        String response = "";
        for (Map.Entry<String, String> entry : this.config.getCommands().entrySet()) {
            final String key = entry.getKey();
            final String command = entry.getValue();

            if (commandKey.equals(key)) {
                System.out.println("Found: "+ key +"="+ command);
                try {
                    ProcessBuilder builder = new ProcessBuilder();
                    if (isWindows) {
                        builder.command("cmd.exe", "/c", command);
                    } else {
                        builder.command("sh", "-c", command);
                    }

                    builder.directory(new File(System.getProperty("user.home")));

                    Process process = builder.start();
                    List<String> responseLines = new ArrayList<>();
                    StreamBuffer streamBuffer = new StreamBuffer(process.getInputStream(), responseLines);
                    Executors.newSingleThreadExecutor().submit(streamBuffer);
                    StreamBuffer errorStreamBuffer = new StreamBuffer(process.getErrorStream(), responseLines);
                    Executors.newSingleThreadExecutor().submit(errorStreamBuffer);
                    int exitCode = process.waitFor();

                    for (String line : responseLines) {
                        response += line +"\n";
                    }
                    response += "ExitCode: "+ exitCode;

                    System.out.println("Response:");
                    System.out.println(response);
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
                } 
            }
        }

        if(StringUtils.isEmpty(response)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Command not found!");
        }

        return Optional.of(response);
    }

    private static class StreamBuffer implements Runnable {
        private InputStream inputStream;
        private List<String> list;
     
        public StreamBuffer(InputStream inputStream, List<String> list) {
            this.inputStream = inputStream;
            this.list = list;
        }
     
        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(list::add);
        }
    }
    
}