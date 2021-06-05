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
                .MCC(map.get("MCC"))
                .MNC(map.get("MNC"))
                .RSSI(map.get("RXLev"))
                .LAC(map.get("LAC"))
                .Ch(map.get("ARFCN"))
                .CellId(map.get("ID"))
                .CellName(map.get("CellName"))
                .build();
    }

    public static List<MainInfo> fromHashMapsToDto(List<Map<String, String>> maps){
        return maps.stream().map(MainInfo::fromHashMapsToDto).collect(Collectors.toList());
    }
}
