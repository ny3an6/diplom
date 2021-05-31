package org.ndmitrenko.diplom.repository;

import org.ndmitrenko.diplom.domain.BaseStationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseStationInfoRepository extends JpaRepository<BaseStationInfo, Long> {
}
