package org.ndmitrenko.diplom.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.ndmitrenko.diplom.dto.response.BaseStationInfoDto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "base_station_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
                .build();
    }

    public static List<BaseStationInfoDto> fromDto(List<Map<String, String>> maps){
        return maps.stream().map(BaseStationInfoDto::fromHashMapsToDto).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCellId() {
        return CellId;
    }

    public void setCellId(String cellId) {
        CellId = cellId;
    }

    public String getMCC() {
        return MCC;
    }

    public void setMCC(String MCC) {
        this.MCC = MCC;
    }

    public String getMNC() {
        return MNC;
    }

    public void setMNC(String MNC) {
        this.MNC = MNC;
    }

    public String getLAC() {
        return LAC;
    }

    public void setLAC(String LAC) {
        this.LAC = LAC;
    }

    public String getBER() {
        return BER;
    }

    public void setBER(String BER) {
        this.BER = BER;
    }

    public String getSNR() {
        return SNR;
    }

    public void setSNR(String SNR) {
        this.SNR = SNR;
    }

    public String getRSSI() {
        return RSSI;
    }

    public void setRSSI(String RSSI) {
        this.RSSI = RSSI;
    }

    public String getCH() {
        return CH;
    }

    public void setCH(String CH) {
        this.CH = CH;
    }

    public List<NeighborsInfo> getNeighborsInfo() {
        return neighborsInfo;
    }

    public void setNeighborsInfo(List<NeighborsInfo> neighborsInfo) {
        this.neighborsInfo = neighborsInfo;
    }
}
