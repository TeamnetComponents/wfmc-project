package ro.teamnet.wfmc.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.teamnet.wfmc.audit.domain.WMErrorAudit;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;

/**
 * Spring Data JPA repository for the WMErrorAudit.
 */
@Repository
public interface ErrorAuditRepository extends JpaRepository<WMErrorAudit, Long> {

    WMErrorAudit findByWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit);
}
