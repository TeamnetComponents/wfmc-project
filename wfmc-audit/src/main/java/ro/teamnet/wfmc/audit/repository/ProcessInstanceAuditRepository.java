package ro.teamnet.wfmc.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;

/**
 * Created by Ioan.Ivan on 3/25/2015.
 */

@Repository
public interface ProcessInstanceAuditRepository extends JpaRepository<WMProcessInstanceAudit, Long> {

    WMProcessInstanceAudit findByProcessDefinitionBusinessName(String processDefinitionBusinessName);

    WMProcessInstanceAudit findByProcessInstanceId(String processInstanceId);
}
