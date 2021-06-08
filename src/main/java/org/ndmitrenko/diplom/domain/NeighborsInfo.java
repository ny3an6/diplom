package org.ndmitrenko.diplom.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ndmitrenko.diplom.dto.response.MainInfo;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NeighborsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id", unique = true, nullable = false)
    private Long id;
    private String Ch;
    private String LAC;
    private String RSSI;
    private String CellId;
    private String CellName;

    public static NeighborsInfo fromDto(MainInfo mainInfo){
        return NeighborsInfo.builder()
                .RSSI(mainInfo.getRSSI())
                .LAC(mainInfo.getLAC())
                .Ch(mainInfo.getCh())
                .CellId(mainInfo.getCellId())
                .CellName(mainInfo.getCellName())
                .build();
    }

    public static List<NeighborsInfo> fromDto(List<MainInfo> mainInfoList){
        return mainInfoList.stream().map(NeighborsInfo::fromDto).collect(Collectors.toList());
    }
}
