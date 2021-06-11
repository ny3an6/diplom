package org.ndmitrenko.diplom.dto.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Data
public class BaseStationInfoDto {
    private String MCC;
    private String MNC;
    private String Ch;
    private String BER;
    private String LAC;
    private String RSSI;
    private String CellId;
    private String CellName;
    private Timestamp scan_date;

    public static BaseStationInfoDto fromHashMapsToDto(Map<String, String> map){
        return BaseStationInfoDto.builder()
                .MCC(map.get("Cc") != null ? map.get("Cc").trim() : null)
                .MNC(map.get("Nc") != null ? map.get("Nc").trim() : null)
                .RSSI(map.get("PWR").trim())
                .LAC(map.get("LAC").trim())
                .Ch(map.get("ARFCN").trim())
                .CellId(map.get("Id").trim())
                .CellName(map.get("CellName") != null ? map.get("CellName").trim(): null)
                .scan_date(new Timestamp(new Date().getTime()))
                .BER(map.get("BER") !=null ? map.get("BER").trim() : null)
                .build();
    }

    public static List<BaseStationInfoDto> fromHashMapsToDto(List<Map<String, String>> maps){
        return maps.stream().map(BaseStationInfoDto::fromHashMapsToDto).collect(Collectors.toList());
    }
}
