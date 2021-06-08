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
@Table(name = "base_station_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseStationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cell_id", unique = false)
    private String CellId;
    @Column(name = "MCC")
    private String MCC;
    @Column(name = "MNC")
    private String MNC;
    @Column(name = "LAC")
    private String LAC;
    @Column(name = "BER")
    private String BER;
    @Column(name = "SNR")
    private String SNR;
    @Column(name = "RSSI")
    private String RSSI;
    @Column(name = "CH")
    private String CH;
    @OneToMany
    private List<NeighborsInfo> neighborsInfo;

    public static BaseStationInfo fromDto(MainInfo mainInfo){
        return BaseStationInfo.builder()
                .MCC(mainInfo.getMCC())
                .MNC(mainInfo.getMNC())
                .RSSI(mainInfo.getRSSI())
                .LAC(mainInfo.getLAC())
                .CH(mainInfo.getCh())
                .CellId(mainInfo.getCellId())
                .build();
    }

    public static List<MainInfo> fromDto(List<Map<String, String>> maps){
        return maps.stream().map(MainInfo::fromHashMapsToDto).collect(Collectors.toList());
    }

}
