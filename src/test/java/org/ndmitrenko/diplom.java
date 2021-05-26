package org.ndmitrenko;

import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class diplom {

    @Test
    public void givenPythonScript_whenPythonProcessInvoked_thenSuccess() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("python3", resolvePythonScriptPath("Hello.py"));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        List<String> results = readProcessOutput(process.getInputStream());
        System.out.println(results);
        assertThat("Results should not be empty", results, is(not(empty())));
        assertThat("Results should contain output of script: ", results, hasItem(
                containsString("Hello Baeldung Readers!!")));

        int exitCode = process.waitFor();
        assertEquals("No errors should be detected", 0, exitCode);
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
