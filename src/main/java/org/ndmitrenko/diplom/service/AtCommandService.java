package org.ndmitrenko.diplom.service;

import com.google.common.base.Splitter;
import org.ndmitrenko.diplom.domain.BaseStationInfo;
import org.ndmitrenko.diplom.domain.NeighborsInfo;
import org.ndmitrenko.diplom.dto.response.BaseStationInfoDto;
import org.ndmitrenko.diplom.repository.BaseStationInfoRepository;
import org.ndmitrenko.diplom.repository.NeighborsInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AtCommandService {

    @Autowired
    private BaseStationInfoRepository baseStationInfoRepository;

    @Autowired
    private NeighborsInfoRepository neighborsInfoRepository;

    public BaseStationInfoDto getMainInfo(String fileName) {
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
        return parseAnswer(results);
    }

    public List<BaseStationInfoDto> getNeighborsInfo(String fileName) {
        ProcessBuilder processBuilder = new ProcessBuilder("python", resolvePythonScriptPath(fileName));
        processBuilder.redirectErrorStream(true);
        List<String> results = new ArrayList<>();
        Process process;
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
        return parseNeighborsInfo(results);
    }

    private BaseStationInfoDto parseAnswer(List<String> atString) {
        Map<String, String> map = new HashMap<>();
        String refactoredString = "";
        String berString = "";
        for (String string : atString) {
            System.out.println(string);
            if(string.contains("C1")) {
                String str = string.replace("+MONI: ", "").replace("-", "");
                int index = str.indexOf(",C1");
                refactoredString = str.substring(0, index) + ",BER: " + atString.get(3).substring(9, 11);
                System.out.println("STRING " + refactoredString);
                if (refactoredString.contains("Nc")) {
                    map = Splitter.on(",").withKeyValueSeparator(":").split(refactoredString);
                }
            }
        }
        System.out.println(map);
        BaseStationInfoDto baseStationInfoDto = BaseStationInfoDto.fromHashMapsToDto(map);
        BaseStationInfo baseStationInfoEntity = BaseStationInfo.fromDto(baseStationInfoDto);
        baseStationInfoEntity.setNeighborsInfo(parseNeighborsInfoForListeningWS(atString));
        baseStationInfoRepository.save(baseStationInfoEntity);
        return baseStationInfoDto;
    }

    private List<BaseStationInfoDto> parseNeighborsInfo(List<String> atString){
        List<Map<String, String>> hashMaps = new ArrayList<>();
        String refactoredString;
        Map<String, String> map;
        StringBuilder stringBuilder;
        String parsedString;
        for (String string : atString) {
            if(string.contains("Cell")) {
                String str = string.replace("+MONI: ", "").replace("Adj", "").replace("-", "")
                        .replace("[", "").replace("]", "");
                int index = str.indexOf(",C1");
                refactoredString = str.substring(0, index);
                stringBuilder = new StringBuilder(refactoredString);
                stringBuilder.insert(0, "CellName:");
                parsedString = stringBuilder.toString();
                System.out.println("STRING 2 " + parsedString);
                map = Splitter.on(",").withKeyValueSeparator(":").split(parsedString);
                hashMaps.add(map);
            }
        }
        System.out.println(BaseStationInfoDto.fromHashMapsToDto(hashMaps));
        List<NeighborsInfo> neighbors = NeighborsInfo.fromDto(BaseStationInfoDto.fromHashMapsToDto(hashMaps));
        neighbors.forEach(x-> neighborsInfoRepository.save(x));
        return BaseStationInfoDto.fromHashMapsToDto(hashMaps);
    }

    private List<NeighborsInfo> parseNeighborsInfoForListeningWS(List<String> atString){
        List<Map<String, String>> hashMaps = new ArrayList<>();
        String refactoredString;
        Map<String, String> map;
        StringBuilder stringBuilder;
        String parsedString;
        for (String string : atString) {
            if(string.contains("Cell")) {
                String str = string.replace("+MONI: ", "").replace("Adj", "").replace("-", "")
                        .replace("[", "").replace("]", "");
                int index = str.indexOf(",C1");
                refactoredString = str.substring(0, index);
                stringBuilder = new StringBuilder(refactoredString);
                stringBuilder.insert(0, "CellName:");
                parsedString = stringBuilder.toString();
                System.out.println("STRING 2 " + parsedString);
                map = Splitter.on(",").withKeyValueSeparator(":").split(parsedString);
                hashMaps.add(map);
            }
        }
        System.out.println(BaseStationInfoDto.fromHashMapsToDto(hashMaps));
        List<NeighborsInfo> neighbors = NeighborsInfo.fromDto(BaseStationInfoDto.fromHashMapsToDto(hashMaps));
        neighbors.forEach(x-> neighborsInfoRepository.save(x));
        return neighbors;
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
}
