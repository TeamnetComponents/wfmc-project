package ro.teamnet.wfmc.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.teamnet.wfmc.audit.domain.WMEventAudit;

/**
 * Created by Florin.Cojocaru on 3/27/2015.
 */
public interface EventAuditRepository extends JpaRepository<WMEventAudit, Long> {
}
