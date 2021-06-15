package org.ndmitrenko;

import com.google.common.base.Splitter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ndmitrenko.dto.response.MainInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



//@RunWith(SpringRunner.class)
//@SpringBootTest
public class diplom {

//    @Autowired
//    private BaseStationInfoRepository baseStationInfoRepository;
//
//    @Autowired
//    private NeighborsInfoRepository neighborsInfoRepository;

    @Test
    public void testRepo(){
        String str = "+CSQ: 25,99".replace("+CSQ: ", "");
        //str.replace("+CSQ: ", "");
        StringBuilder build = new StringBuilder(str);
        System.out.println("Pre Builder : " + build);
        build.deleteCharAt(1);  // Shift the positions front.
        //build.deleteCharAt(8-1);
//        build.deleteCharAt(15-2);
        System.out.println("Post Builder : " + build);
    }

    @Test
    public void getATAnswer() {
        ProcessBuilder processBuilder = new ProcessBuilder("python", resolvePythonScriptPath("MainInfoAndNeighbors.py"));
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

        // +CCINFO= [SCELL], ARFCN= 1020, MCC= 250, MNC= 01, LAC= 332, ID= 25071, BSIC= 11, RXLev= -64dBm, C1= 42, C2= 42, TA= 0, TXPWR= 0
        System.out.println(parseAnswerMainInfo(results));
        System.out.println("");
        System.out.println(parseNeighborsInfo(results));
    }

    private org.ndmitrenko.dto.response.MainInfo parseAnswerMainInfo(List<String> atString) {
        Map<String, String> map = new HashMap<>();
        String refactoredString = "";
        for (String string : atString) {
            if(string.contains("C1")) {
                String str = string.replace("+MONI: ", "").replace("-", "");
                int index = str.indexOf(",C1");
                refactoredString = str.substring(0, index);
                System.out.println("STRING " + refactoredString);
                if (refactoredString.contains("Nc")) {
                    map = Splitter.on(",").withKeyValueSeparator(":").split(refactoredString);
                }
            }
        }
        return MainInfo.fromHashMapsToDto(map);
    }

    private List<MainInfo> parseNeighborsInfo(List<String> atString){
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


        //System.out.println(hashMaps);
        System.out.println(MainInfo.fromHashMapsToDto(hashMaps));
        return null;
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
