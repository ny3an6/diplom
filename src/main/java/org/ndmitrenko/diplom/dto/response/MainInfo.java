package org.ndmitrenko.diplom.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Data
public class MainInfo {
    private String MCC;
    private String MNC;
    private String Ch;
    private String LAC;
    private String RSSI;
    private String CellId;
    private String CellName;

    public static MainInfo fromHashMapsToDto(Map<String, String> map){
        return MainInfo.builder()
                .MCC(map.get("Cc") != null ? map.get("Cc").trim() : null)
                .MNC(map.get("Nc") != null ? map.get("Nc").trim() : null)
                .RSSI(map.get("PWR").trim())
                .LAC(map.get("LAC").trim())
                .Ch(map.get("ARFCN").trim())
                .CellId(map.get("Id").trim())
                .CellName(map.get("CellName") != null ? map.get("CellName").trim(): null)
                .build();
    }

    public static List<MainInfo> fromHashMapsToDto(List<Map<String, String>> maps){
        return maps.stream().map(MainInfo::fromHashMapsToDto).collect(Collectors.toList());
    }
}
