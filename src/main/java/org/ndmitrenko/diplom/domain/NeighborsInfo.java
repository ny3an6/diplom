package org.ndmitrenko.diplom.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ndmitrenko.diplom.dto.response.BaseStationInfoDto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NeighborsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Ch;
    private String LAC;
    private String RSSI;
    private String CellId;
    private String CellName;
    private Timestamp scan_date;

    public static NeighborsInfo fromDto(BaseStationInfoDto baseStationInfo){
        return NeighborsInfo.builder()
                .RSSI(baseStationInfo.getRSSI())
                .LAC(baseStationInfo.getLAC())
                .Ch(baseStationInfo.getCh())
                .CellId(baseStationInfo.getCellId())
                .CellName(baseStationInfo.getCellName())
                .scan_date(baseStationInfo.getScan_date())
                .build();
    }

    public static List<NeighborsInfo> fromDto(List<BaseStationInfoDto> baseStationInfoList){
        return baseStationInfoList.stream().map(NeighborsInfo::fromDto).collect(Collectors.toList());
    }
}
