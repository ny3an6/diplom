package org.ndmitrenko.diplom.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SendAtCommand {

    public List<String> getATAnswer(String fileName) {
        ProcessBuilder processBuilder = new ProcessBuilder("python3", resolvePythonScriptPath(fileName));
        processBuilder.redirectErrorStream(true);
        List<String> results = new ArrayList<>();
        Process process = null;
        int exitCode;
        try {
            process = processBuilder.start();
            results = readProcessOutput(process.getInputStream());
            exitCode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(results);

        return results;
    }

    private String resolvePythonScriptPath(String filename) {
        File file = new File("src/main/resources/ATCommandsScripts/" + filename);
        System.out.println(file);
        System.out.println(file);
        return file.getAbsolutePath();
    }

    private List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }
}
