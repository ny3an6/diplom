package org.ndmitrenko.diplom.repository;

import org.ndmitrenko.diplom.domain.NeighborsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeighborsInfoRepository extends JpaRepository<NeighborsInfo, Long> {
}
