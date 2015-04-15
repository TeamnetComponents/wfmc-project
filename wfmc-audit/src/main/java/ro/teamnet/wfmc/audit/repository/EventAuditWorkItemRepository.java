package ro.teamnet.wfmc.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.teamnet.wfmc.audit.domain.WMEventAuditWorkItem;
import ro.teamnet.wfmc.audit.domain.WMWorkItemAudit;

/**
 * Created by Ioan.Ivan on 3/25/2015.
 */

@Repository
public interface EventAuditWorkItemRepository extends JpaRepository<WMEventAuditWorkItem, Long> {

    WMEventAuditWorkItem findByWorkItemState(String workItemState);
    WMEventAuditWorkItem findByWmWorkItemAudit(WMWorkItemAudit wmWorkItemAudit);
}
