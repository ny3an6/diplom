package org.ndmitrenko.diplom.dto.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
                .RSSI(map.get("PWR") != null ? map.get("PWR").trim() : null)
                .LAC(map.get("LAC") != null ? map.get("LAC").trim() : null)
                .Ch(map.get("ARFCN") != null ? map.get("ARFCN").trim() : null)
                .CellId(map.get("Id") != null ? map.get("Id").trim() : null)
                .CellName(map.get("CellName") != null ? map.get("CellName").trim(): null)
                .scan_date(new Timestamp(new Date().getTime()))
                .BER(map.get("BER") !=null ? map.get("BER").trim() : null)
                .build();
    }

    public static List<BaseStationInfoDto> fromHashMapsToDto(List<Map<String, String>> maps){
        return maps.stream().map(BaseStationInfoDto::fromHashMapsToDto).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseStationInfoDto that = (BaseStationInfoDto) o;
        return Objects.equals(MCC, that.MCC) && Objects.equals(MNC, that.MNC) && Objects.equals(Ch, that.Ch) && Objects.equals(BER, that.BER) && Objects.equals(LAC, that.LAC) && Objects.equals(RSSI, that.RSSI) && Objects.equals(CellId, that.CellId) && Objects.equals(CellName, that.CellName) && Objects.equals(scan_date, that.scan_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MCC, MNC, Ch, BER, LAC, RSSI, CellId, CellName, scan_date);
    }
}
