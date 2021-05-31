package org.ndmitrenko.diplom.domain;

import javax.persistence.*;

@Entity
@Table(name = "base_station_info")
public class BaseStationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "cell_id", unique = true)
    private Integer CellId;
    @Column(name = "MCC")
    private Integer MCC;
    @Column(name = "MNC")
    private Integer MNC;
    @Column(name = "LAC")
    private Integer LAC;
    @Column(name = "BER")
    private Integer BER;
    @Column(name = "SNR")
    private Integer SNR;
    @Column(name = "RSSI")
    private Integer RSSI;
    @Column(name = "CH")
    private Integer CH;
    @Column(name = "neighbour")
    private String Neighbour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCellId() {
        return CellId;
    }

    public void setCellId(Integer cellId) {
        CellId = cellId;
    }

    public Integer getMCC() {
        return MCC;
    }

    public void setMCC(Integer MCC) {
        this.MCC = MCC;
    }

    public Integer getMNC() {
        return MNC;
    }

    public void setMNC(Integer MNC) {
        this.MNC = MNC;
    }

    public Integer getLAC() {
        return LAC;
    }

    public void setLAC(Integer LAC) {
        this.LAC = LAC;
    }

    public Integer getBER() {
        return BER;
    }

    public void setBER(Integer BER) {
        this.BER = BER;
    }

    public Integer getSNR() {
        return SNR;
    }

    public void setSNR(Integer SNR) {
        this.SNR = SNR;
    }

    public Integer getRSSI() {
        return RSSI;
    }

    public void setRSSI(Integer RSSI) {
        this.RSSI = RSSI;
    }

    public Integer getCH() {
        return CH;
    }

    public void setCH(Integer CH) {
        this.CH = CH;
    }

    public String getNeighbour() {
        return Neighbour;
    }

    public void setNeighbour(String neighbour) {
        Neighbour = neighbour;
    }
}
