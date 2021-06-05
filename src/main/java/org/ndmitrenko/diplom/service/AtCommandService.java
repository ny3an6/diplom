package org.ndmitrenko.diplom.service;

import com.google.common.base.Splitter;
import org.ndmitrenko.diplom.domain.BaseStationInfo;
import org.ndmitrenko.diplom.dto.response.MainInfo;
import org.ndmitrenko.diplom.repository.BaseStationInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AtCommandService {

    @Autowired
    private BaseStationInfoRepository baseStationInfoRepository;

    public MainInfo getATAnswer(String fileName) {
        ProcessBuilder processBuilder = new ProcessBuilder("python", resolvePythonScriptPath(fileName));
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
        System.out.println(results.size());
        // +CCINFO= [SCELL], ARFCN= 1020, MCC= 250, MNC= 01, LAC= 332, ID= 25071, BSIC= 11, RXLev= -64dBm, C1= 42, C2= 42, TA= 0, TXPWR= 0
        return parseAnswer(results);
    }

    private MainInfo parseAnswer(List<String> atString) {
        Map<String, String> map = new HashMap<>();
        for (String string : atString) {
            if (string.length() >= 80 && (string.contains("ARFCN") || string.contains("MCC") || string.contains("MNC") || string.contains("LAC") ||
                    string.contains("ID") || string.contains("RXLev"))) {
                map = Splitter.on(",").withKeyValueSeparator(":").split(string);
            }
        }
        System.out.println(map);
        return MainInfo.builder()
                .MCC(map.get("MCC"))
                .MNC(map.get("MNC"))
                .RSSI(map.get("RXLev"))
                .LAC(map.get("LAC"))
                .Ch(map.get("ARFCN"))
                .CellId(map.get("ID"))
                .build();
    }

    private String resolvePythonScriptPath(String filename) {
        File file = new File("src/main/resources/ATCommandsScripts/" + filename);
        return file.getAbsolutePath();
    }

    private List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }

    public void createTestData() {
        BaseStationInfo bs = new BaseStationInfo();
        bs.setBER(1);
        bs.setCellId(3);

        baseStationInfoRepository.save(bs);
    }
}
