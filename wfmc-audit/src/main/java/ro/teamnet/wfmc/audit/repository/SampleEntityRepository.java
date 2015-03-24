package ro.teamnet.wfmc.audit.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.teamnet.wfmc.audit.domain.SampleEntity;

/**
 * Spring Data JPA repository for the SampleEntity entity.
 */
@Repository
public interface SampleEntityRepository extends JpaRepository<SampleEntity, Long> {
}
