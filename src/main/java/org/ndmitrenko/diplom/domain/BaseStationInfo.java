package org.ndmitrenko.diplom.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ndmitrenko.diplom.dto.response.BaseStationInfoDto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    @Column(name = "cell_id")
    private String CellId;
    @Column(name = "MCC")
    private String MCC;
    @Column(name = "MNC")
    private String MNC;
    @Column(name = "LAC")
    private String LAC;
    @Column(name = "BER")
    private String BER;
    @Column(name = "RSSI")
    private String RSSI;
    @Column(name = "CH")
    private String CH;
    @Column(name = "scan_date")
    private Timestamp date;
    @OneToMany
    private List<NeighborsInfo> neighborsInfo;

    public static BaseStationInfo fromDto(BaseStationInfoDto baseStationInfo){
        return BaseStationInfo.builder()
                .MCC(baseStationInfo.getMCC())
                .MNC(baseStationInfo.getMNC())
                .RSSI(baseStationInfo.getRSSI())
                .LAC(baseStationInfo.getLAC())
                .CH(baseStationInfo.getCh())
                .CellId(baseStationInfo.getCellId())
                .date(baseStationInfo.getScan_date())
                .BER(baseStationInfo.getBER())
                .build();
    }

    public static List<BaseStationInfoDto> fromDto(List<Map<String, String>> maps){
        return maps.stream().map(BaseStationInfoDto::fromHashMapsToDto).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseStationInfo that = (BaseStationInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(CellId, that.CellId) && Objects.equals(MCC, that.MCC) && Objects.equals(MNC, that.MNC) && Objects.equals(LAC, that.LAC) && Objects.equals(BER, that.BER) && Objects.equals(RSSI, that.RSSI) && Objects.equals(CH, that.CH) && Objects.equals(date, that.date) && Objects.equals(neighborsInfo, that.neighborsInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, CellId, MCC, MNC, LAC, BER, RSSI, CH, date, neighborsInfo);
    }
}
