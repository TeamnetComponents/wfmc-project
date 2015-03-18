##Metode implementate

#####1.*public void connect(WMConnectInfo connectInfo) throws WMWorkflowException*
Descriere metoda: metoda creaza o conexiune cu server-ul de ELO.

Parametrii necesari pentru apelarea metodei sunt urmatorii:
* connectInfo – este un obiect de tip WMConnectInfo ce contine urmatoarele valori:
 * userIdentification – reprezinta numele utilizatorului.
 * password – parola utilizatorului.
 * engineName – numele masinii de pe care te conectezi.
 * scope – reprezinta Elo Index server (IX).

#####2.*public void disconnect() throws WMWorkflowException*
Descriere metoda: metoda distruge conexiunea la server-ul de ELO.

#####3.*public String createProcessInstance(String procDefId, String procInstName) throws WMWorkflowException*
Descriere metoda: metoda creaza o instanta de proces in functie de un process definition si returneaza id-ul acestei instante.

Parametrii necesari pentru apelarea metodei sunt urmatorii:
* procDefId – reprezinta id-ul unui process definition in functie de care o sa fie creat aceasta instanta.
* procInstName – reprezinta numele pe care o sa il aibe instanta.

#####4.*public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException*
Descriere metoda: metoda adauga metadatele de flux.

Parametrii necesari pentru apelarea acestei metode sunt:
* procInstId – id-ul instantei de proces caruia i se va adauga metadata. Acest id este id-ul returnat de metoda createProcessInstance.
* attrName – numele atributului.
* attrValue – reprezinta id-ul sordului care va fi pus pe proces.

Putem avea cazurile in care id-ul sordului (documentului) sau masca sordului sunt trimise prin apelarea acestei metode. Un exemplu este urmatorul : 
    
    wfmcService.assignProcessInstanceAttribute(processInstanceId, ELOConstants.SORD_ID, sordId);
    wfmcService.assignProcessInstanceAttribute(processInstanceId, ELOConstants.MASK_ID, maskId);

#####5.*public String startProcess(String procInstId) throws WMWorkflowException*
Descriere metoda: metoda porneste o instanta de proces in functie de un id pe care il primim cand apelam metoda createProcessInstance si returneaza id-ul procesului pornit care e diferit de id-ul instantei de proces sau de id-ul lui process definition. Inainte de a da startProcess trebuie sa apelam si assignProcessInstanceAttribute pentru a adauga metadatele fluxului pentru acea instanta de proces ce va fi pornita.

Parametrii necesari pentru apelarea metodei sunt urmatorii:
* procInstId – reprezinta id-ul unei instante de proces. Acesta trebuie sa fie id-ul unei instante existente, altfel o sa arunce un WMWorkflowException.

#####6.*public WMWorkItemIterator listWorkItems(WMFilter filter, boolean countFlag) throws WMWorkflowException*
Descriere metoda: metoda returneaza un iterator ce contine work item-urile active pentru un filtru creat. Metoda poate aduce un numar maxim de 1000 de work item-uri active.

Parametrii necesari pentru apelarea metodei sunt urmatorii:
* filter – este un obiect de tipul WMFilter prin care se configureaza un filtru de cautare. 
* countFlag – reprezinta un boolean care atunci cand este setat "true" poti accesa numarul de inregistrari aduse de cautare. - momentan, in implementare, este ignorat acest parametru.

#####7.*public void reassignWorkItem(String sourceUser, String targetUser, String procInstId, String workItemId) throws WMWorkflowException*
Descriere metoda: metoda atribuie un work item unui utilizator.

Parametrii necesari pentru apelarea acestei metode sunt:
* sourceUser - utilizatorul care are atribuit work item-ul.
* targetUser - utilizatorul caruia i se va atribui work item-ul.
* procInstId - id-ul procesului.
* workItemId - id-ul work item-ului.

#####8.*public List<WMWorkItem> getNextSteps(String processInstanceId, String workItemId) throws WMWorkflowException*
Descriere metoda: metoda intoarce o lista de tranzitiile posibile pentru un work item.

Parametrii necesari pentru apelarea acestei metode sunt:
* processInstanceId - id-ul procesului ce contine work item-ul.
* workItemId - id-ul work item-ului pentru care se vrea lista de tranzitii.

#####9.*public void assignWorkItemAttribute(String procInstId, String workItemId, String attrName, Object attrValue) throws WMWorkflowException*
Descriere metoda: cu ajutorul acestei metode se dau id-urile urmatorului/urmatoarelor work item-uri(noduri) prin care va trece fluxul.

Parametrii necesari pentru apelarea acestei metode sunt:
* procInstId - id-ul procesului ce contine work item-ul.
* workItemId - id-ul work item-ului din care o sa porneasca .
* attrName - va trebui sa fie WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID pentru a specifica faptul ca urmatoarele valori sunt id-uri ale nodurilor succesoare.
* attrValue - id-ul nodurilor prin care va trece fluxul.

#####10.*public void completeWorkItem(String procInstId, String workItemId) throws WMWorkflowException*
Descriere metoda: aceasta metoda completeaza work item-ul si trimite fluxul catre nodurile care au fost setate cu metoda assignWorkItemAttribute.

Parametrii necesari pentru apelarea acestei metode sunt :
* procInstId - id-ul procesului din care face parte work item-ul.
* workItemId - id-ul work item-ului.

#####11.*public void abortProcessInstance(String procInstId) throws WMWorkflowException*
Descriere metoda: metoda sterge un proces activ.

Parametrii necesari pentru apelarea acestei metode sunt:
* procInstId – reprezinta id-ul procesului. Acest id este returnat de metoda startProcessInstance.

#####12.*public WMProcessInstance getProcessInstance(String procInstId) throws WMWorkflowException*
Descriere metoda: metoda intoarce o instanta de proces.
 
Parametrii necesari pentru apelarea acestei metode sunt:
* procInstId – reprezinta id-ul instantei de proces ce se doreste a fi adusa.

#####13.*public WMProcessInstanceIterator listProcessInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException*
Descriere metoda: metoda aduce o lista de cele mult 1000 de procese active sau terminate.

Paramentrii necesari pentru apelarea acestei metode sunt:
* filter -  este un obiect de tipul WMFilter prin care se configureaza un filtru de cautare.
* countFlag - reprezinta un boolean care atunci cand este setat "true" poti accesa numarul de inregistrari aduse de cautare. - momentan, in implementare, este ignorat acest parametru.

#####14.*public WMWorkItem getWorkItem(String procInstId, String workItemId) throws WMWorkflowException*
Descriere metoda: metoda returneaza un WMWorkItem.

Parametrii necesari pentru apelarea acestei metode sunt:
* procInstId - id-ul procesului din care face parte acel work item.
* workItemId - id-ul work item-ului ce doreste a fi returnat.

#####15.*public WorkflowProcess getWorkFlowProcess(String processDefinitionId)*
Descriere metoda: metoda returneaza un WorkflowProcess (Un process definition dupa care se creaza instantele de proces).

Parametrii necesari pentru apelarea acestei metode sunt:
* processDefinitionId - id-ul unui process definition ce se doreste a fi returnat.


##Exemple cod:
Este necesar un fisier de forma wapi-elo-renns.properties:

    #######------------------------------------------------------------------------------
    #######WAPI Service Cache configuration file type
    #######------------------------------------------------------------------------------
    
    #######------------------------------------------------------------------------------
    #######COMMON SERVICE CONFIGURATION PARAMETERS
    #######------------------------------------------------------------------------------
    #######class named used for the service implementation
    instance.name=wapi-elo-renns
    instance.class=org.wfmc.elo.WfmcServiceEloImpl
    
    #######------------------------------------------------------------------------------
    #######SPECIFIC SERVICE CONFIGURATION PARAMETERS
    #######------------------------------------------------------------------------------
    service.cache.instance.configuration=wapi-cache.properties
    
    workflow.sord.location.template.path=/workflows/${UAT}

Acest fisier este folosit la instantierea service-ului de WfMC. Exemplu:

        String serviceProperties = "D:\\projects\\wfmc-project\\wfmc-test\\src\\main\\resources\\wapi-elo-renns.properties";
    
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(serviceProperties);
        WfmcService wfmcService = wfmcServiceFactory.getInstance();

#####1.*public void connect(WMConnectInfo connectInfo) throws WMWorkflowException*
Avem nevoie de un obiect WMConnectInfo.

        WMConnectInfo wmConnectInfo = new WMConnectInfo("userName@Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix")
        wfmcService.connect(wmConnectInfo);

#####2.*public void disconnect() throws WMWorkflowException*
Se apeleaza ca in exemplu urmator:

        wfmcService.disconnect();

#####3.*public String createProcessInstance(String procDefId, String procInstName) throws WMWorkflowException*

Avem nevoie de id-ul sau numele unui process definition existent si numele pe care vrem noi sa il aiba instanta creata. Exemplu:

        wfmcService.createProcessInstance("Template test", "New process instance");

sau 

        wfmcService.createProcessInstance("1", "New process instance");

#####4.*public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException*
Putem avea cazurile in care id-ul sordului (documentului) sau masca sordului sunt trimise prin apelarea acestei metode. In exemplul urmator prima data e dat un id de sord iar apoi id-ul unei masti. In practica doar unul din ele trebuie dat: 
    
        wfmcService.assignProcessInstanceAttribute(processInstanceId, ELOConstants.SORD_ID, sordId);
        wfmcService.assignProcessInstanceAttribute(processInstanceId, ELOConstants.MASK_ID, maskId);

sau putem pune un atribut pe sord-ul(documentul) de pe proces. Exemplu:

        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "UAT", 1);

#####5.*public String startProcess(String procInstId) throws WMWorkflowException*
Pentru apelarea metodei avem nevoie de procInstId care este id-ul returnat de createProcessInstance si este un id temporar. Acest id nu reprezinta id-ul unui proces activ.

        String procInstIdTemp = wfmcService.createProcessInstance("Template test", "New process instance");
        String processInstanceId = wfmcService.startProcess(procInstIdTemp);

#####6.*public WMWorkItemIterator listWorkItems(WMFilter filter, boolean countFlag) throws WMWorkflowException*
Construirea unui filtru este prezentata in ???. Exemplu pentru apelarea acestei metode:

        WMFilter wmFilter = WMFilterBuilder.createWMFilterWorkItem()
                .addWorkItemParticipantName("Administrator")
                .addWorkItemName(FluxAprobareOperatiuniNodes.REDACTARE_RASPUNS);
        WMWorkItemIterator wmWorkItemIterator = wfmcService.listWorkItems(wmFilter, true);

#####7.*public void reassignWorkItem(String sourceUser, String targetUser, String procInstId, String workItemId) throws WMWorkflowException*
Prin aceasta metoda vom aloca un work item care apartine, in cazul nostru, utilizatorului ELO Service, catre utilizatorul "Administrator". Datele necesare pentru a identifica work item-ul sunt procInstId, id-ul procesului, si currentWorkItemId, id-ul work item-ului. Exemplu:

        wfmcService.reassignWorkItem("ELO Service", "Administrator", procInstId, curentWorkItemId);

#####8.*public List<WMWorkItem> getNextSteps(String processInstanceId, String workItemId) throws WMWorkflowException*
Acesta metoda o sa aduca o lista de workItem-uri succesoare pentru un work item dat. Pentru a apela aceasta metoda avem nevoie de id-ul unui proces activ returnat de metoda startProcessInstance si id-ul work item-ului.

        List<WMWorkItem> nextSteps = wfmcService.getNextSteps(processInstanceId, currentWorkItemId);

#####9.*public void assignWorkItemAttribute(String procInstId, String workItemId, String attrName, Object attrValue) throws WMWorkflowException*
Prin aceasta metoda specificam doar prin ce noduri trece procesul iar pentru apelarea ei este necesar ca attrName sa fie egal cu WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(). Exemplu:

        wfmcService.assignWorkItemAttribute(processInstanceId, currentWorkItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(), nextWorkItemId);

#####10.*public void completeWorkItem(String procInstId, String workItemId) throws WMWorkflowException*
Incheie activitatea pe un work item si trimite procesul spre nodurile specificate de noi cu metoda assignWorkItemAttribute. Exemplu:

        wfmcService.completeWorkItem(processInstanceId, currentWorkItemId);

#####11.*public void abortProcessInstance(String procInstId) throws WMWorkflowException*
Sterge un proces activ. Exemplu:

        wfmcService.abortProcessInstance(processInstanceId);

#####12.*public WMProcessInstance getProcessInstance(String procInstId) throws WMWorkflowException*
Aduce o instanta de proces activ sau daca nu exista una activa, o sa caute dupa o instanta de proces terminat. Exemplu:

        WMProcessInstance processInstance = wfmcService.getProcessInstance(procInstId);


**ATENTIE! Dupa terminarea unui proces, id-ul procesului terminat nu o sa fie acelasi cu id-ul pe care l-a avut cat timp a fost activ.**

#####13.*public WMProcessInstanceIterator listProcessInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException*
Detalii despre construirea filtrului gasiti in ??? . Exemplu:

        WMFilter wmFilter = WMFilterBuilder.createWMFilterProcessInstance().
            isActive(true).
            addProcessDefinitionId(String.valueOf(processDefinitionId));
        WMProcessInstanceIterator wmProcessInstanceIterator = wfmcService.listProcessInstances(wmFilter, true);

#####14.*public WMWorkItem getWorkItem(String procInstId, String workItemId) throws WMWorkflowException*
Returneaza un WMWorkItem in functe de id-ul procesului din care face parte si id-ul work item-ului. Exemplu:

        wfmcService.getWorkItem(processInstanceId, workItemId);

#####15.*public WorkflowProcess getWorkFlowProcess(String processDefinitionId)*
Returneaza process definition cu id-ul processDefinitionId. Exemplu:

        wfmcService.getWorkFlowProcess(processDefinitionId);

##Flux complet
Clasa [DemoFluxHotarareConsiliuLocalAprobat](http://git-components.teamnet.ro/blob/components%2Fjava%2Fwfmc-project.git/master/wfmc-test%2Fsrc%2Fmain%2Fjava%2Forg%2Fwfmc%2FDemoFluxAprobareOperatiuniAprobat.java) din wfmc-test :

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        String serviceProperties = "D:\\projects\\wfmc-project\\wfmc-test\\src\\main\\resources\\wapi-elo-renns.properties";
        String processInstanceName = "Instanta flux hotarare consiliu local 2";

        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(serviceProperties);
        WfmcService wfmcService = wfmcServiceFactory.getInstance();
        //naming convention for user: {ApplicationUser}@{ImpersonatedUser}   ; ImpersonatedUser = Userul de login in ELO(licenta); Administrator pt DEV
        wfmcService.connect(new WMConnectInfo("Andra@Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));
        // Pas 1. Create process instance
        String procInstIdTemp = wfmcService.createProcessInstance("5", processInstanceName);

        //Pas 2. Assign process instance attributes
        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "UAT", 1);
        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "String", "test");
        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "Double", 1.0);

        //Pas 3. Start process instance
        String processInstanceId = wfmcService.startProcess(procInstIdTemp);

        //Pas 4. Get avaible task for Automatizare Asteptare and user = ELO Service.
        WMFilter wmFilter = WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipantName("ELO Service").
                addWorkItemName(FluxHotarareConsiliuLocalNodes.AUTOMATIZARE_ASTEPTARE);

        WMWorkItemIterator wmWorkItemIterator = wfmcService.listWorkItems(wmFilter, true);
        List<WMWorkItem> wmWorkItemListForEloService = new ArrayList<>();
        while (wmWorkItemIterator.hasNext()){
            WMWorkItem wmWorkItem = wmWorkItemIterator.tsNext();
            wmWorkItemListForEloService.add(wmWorkItem);
            System.out.println("Task for user " + wmWorkItem.getParticipant().getName() + " are : " + wmWorkItem.getName());
        }

        //Pas 5. Claim task Automatizare Asteptare
        String currentWorkItemId = null;
        for (WMWorkItem wmWorkItem : wmWorkItemListForEloService) {
            if (wmWorkItem.getProcessInstanceId().equals(processInstanceId)){
                currentWorkItemId = wmWorkItem.getId();
                wfmcService.reassignWorkItem("ELO Service", "Andra", processInstanceId, currentWorkItemId);
            }
        }

        //Pas 6. Check if the work item was assigned to Andra by getting avaible task for Automatizare Asteptare and user = Andra.
        WMFilter wmFilterForAndra = WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipantName("Andra").
                addWorkItemName(FluxHotarareConsiliuLocalNodes.AUTOMATIZARE_ASTEPTARE);
        WMWorkItemIterator wmWorkItemIteratorForAndra = wfmcService.listWorkItems(wmFilterForAndra, true);
        while (wmWorkItemIteratorForAndra.hasNext()){
            WMWorkItem wmWorkItem = wmWorkItemIteratorForAndra.tsNext();
            currentWorkItemId = wmWorkItem.getId();
            System.out.println("Task for user " + wmWorkItem.getParticipant().getName() + " are : " + wmWorkItem.getName() );
        }

        // 6.1 print workflow
        WMProcessInstance wmProcessInstance = wfmcService.getProcessInstance(processInstanceId);
        System.out.println(wmProcessInstance == null ? "null process instance" : "Process instance id = " + wmProcessInstance.getId());
        System.out.println(wmProcessInstance == null ? "null process instance" : "Process instance name = " + wmProcessInstance.getName());
        System.out.println(wmProcessInstance == null || wmProcessInstance.getState() == null ? "Process instance state = " + "null": "Process instance state = " + wmProcessInstance.getState().stringValue() );
        //Pas 7. List next steps for Automatizare Asteptare.
        List<WMWorkItem> nextSteps = wfmcService.getNextSteps(processInstanceId, currentWorkItemId);

        //Pas 8. Forward task to Aprobat
        for (WMWorkItem wmWorkItem : nextSteps) {
            if(wmWorkItem.getName().equals(FluxHotarareConsiliuLocalNodes.APROBAT)) {
                wfmcService.assignWorkItemAttribute(processInstanceId, currentWorkItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(), wmWorkItem.getId());
            }
        }
        wfmcService.completeWorkItem(processInstanceId, currentWorkItemId);

        //Pas 9. Check if workflow was finished
        WMFilter wmFilter2 = WMFilterBuilder.createWMFilterProcessInstance().isActive(false).addProcessInstanceName(processInstanceName);
        WMProcessInstanceIterator wmProcessInstanceIterator = wfmcService.listProcessInstances(wmFilter2, true);
        while (wmProcessInstanceIterator.hasNext()) {
            WMProcessInstance wmProcessInstanceTemp = wmProcessInstanceIterator.tsNext();
            System.out.println(wmProcessInstanceTemp == null ? "null process instance" : "Process instance id = " + wmProcessInstanceTemp.getId());
            System.out.println(wmProcessInstanceTemp == null ? "null process instance" : "Process instance name = " + wmProcessInstanceTemp.getName());
            System.out.println(wmProcessInstanceTemp == null || wmProcessInstance.getState() == null ? "Process instance state = " + "null": "Process instance state = " + wmProcessInstanceTemp.getState().stringValue() );
        }
    }
}
