package ro.teamnet.wfmc.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.teamnet.wfmc.audit.domain.WMEventAudit;

/**
 * Created by Florin.Cojocaru on 3/27/2015.
 */

@Repository
public interface EventAuditRepository extends JpaRepository<WMEventAudit, Long> {

    public WMEventAudit[] findByUsername(String username);

    public WMEventAudit findByEventCode(int eventCode);
}

