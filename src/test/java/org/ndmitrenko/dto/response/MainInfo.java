package org.ndmitrenko.dto.response;

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

    public static org.ndmitrenko.dto.response.MainInfo fromHashMapsToDto(Map<String, String> map){
        return org.ndmitrenko.dto.response.MainInfo.builder()
                .MCC(map.get("Cc"))
                .MNC(map.get("Nc"))
                .RSSI(map.get("PWR"))
                .LAC(map.get("LAC"))
                .Ch(map.get("ARFCN"))
                .CellId(map.get("Id"))
                .CellName(map.get("CellName"))
                .build();
    }

    public static List<org.ndmitrenko.dto.response.MainInfo> fromHashMapsToDto(List<Map<String, String>> maps){
        return maps.stream().map(org.ndmitrenko.dto.response.MainInfo::fromHashMapsToDto).collect(Collectors.toList());
    }
}
