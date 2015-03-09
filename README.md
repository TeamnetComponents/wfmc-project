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
Descriere metoda: metoda returneaza un iterator ce contine work item-urile active pentru un filtru creat. Metoda poate aduce un numar maxim de 1000 de work item-uri active

Parametrii necesari pentru apelarea metodei sunt urmatorii:
* filter – este un obiect de tipul WMFilter prin care se configureaza un filtru de cautare. 
* countFlag – reprezinta un boolean care atunci cand este setat "true" poti accesa numarul de inregistrari aduse de cautare. - momentan, in implementare, este ignorat acest parametru

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
* processInstanceId - id-ul procesului ce contine work item-ul
* workItemId - id-ul work item-ului pentru care se vrea lista de tranzitii.

#####9.*public void setTransition(String processInstanceId, String currentWorkItemId, String[] nextWorkItemIds) throws WMWorkflowException*
Descriere metoda: metoda trece de la un work item al procesului catre una sau mai multe noduri. O lista cu noduri succesoare este returnata de metoda getNextSteps.

Parametrii necesari pentru apelarea acestei metode sunt:
* processInstanceId - id-ul procesului ce contine work item-ul.
* workItemId - id-ul work item-ului din care o sa porneasca 
* nextWorkItemsIds - este un array ce contine id-urile nodurilor in care se doreste sa se doreste sa se faca tranzitia.

#####10.*public void abortProcessInstance(String procInstId) throws WMWorkflowException*
Descriere metoda: metoda sterge un proces activ.

Parametrii necesari pentru apelarea acestei metode sunt:
* procInstId – reprezinta id-ul procesului. Acest id este returnat de metoda startProcessInstance.

#####11.*public WMProcessInstance getProcessInstance(String procInstId) throws WMWorkflowException*
Descriere metoda: metoda intoarce o instanta de proces.
 
Parametrii necesari pentru apelarea acestei metode sunt:
* procInstId – reprezinta id-ul instantei de proces ce se doreste a fi adusa

##Exemple cod:

#####1.*Cum pornim o instanta de flux pe un process definition*

    // Pas 1. Create process instance
    String procInstIdTemp = wfmcService.createProcessInstance("5", processInstanceName);
    
    //Pas 2. Assign process instance attributes
    wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "UAT", 1);
    wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "String", "test");
    wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "Double", 1.0);
    
    //Pas 3. Start process instance
    String processInstanceId = wfmcService.startProcess(procInstIdTemp);

Intr-un prim pas ne cream o instanta de proces cu metoda createProcessInstance avand ca parametrii id-ul unui process definition si numele dorit de noi pentru procesul activ, dupa care, optional, va trebui sa setam parametrii procesului. Ultimul pas este pornirea instantei creata mai devreme prin apelarea metodei startProcess cu id-ul returnat de metoda createProcessInstance.  Metoda startProcess o sa intoarca un id diferit de id-ul lui process definition sau de id-ul returnat de createProcessInstance. Mai departe cand vom dori sa accesam procesul pornit, o sa facem prin id-ul returnat de startProcess.

#####2.*Cum setam atributele/param de flux (medatadele fluxului)*

    wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "UAT", 1);
    wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "String", "test");
    wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "Double", 1.0);

Parametrii fluxului o sa ii setam prin apeluri repetate a metodei assignProcessInstanceAttribute care o sa primeasca. Acesta metoda o sa fie apelata folosind processInstanceId returnat de metoda createProcessInstance, NU startProcessInstance, numele parametrului si valoarea acestuia.

#####3.*Cum obtinem “next steps”, adica cum obtinem lista de rezolutii (“tranzitii”) posibile pe care le putem utiliza la un pas in flux*

    List<WMWorkItem> nextSteps = wfmcService.getNextSteps(processInstanceId, currentWorkItemId);

Acesta metoda o sa aduca o lista de workItem-uri succesoare pentru un work item dat. Pentru a apela aceasta metoda avem nevoie de id-ul unui proces activ returnat de metoda startProcessInstance si id-ul work item-ului.

#####4.*Cum atribuim o rezolutie pe un pas din flux, adica cum trecem de la nod la altul (de la un pas la altul)*

    wfmcService.setTransition(processInstanceId, currentWorkItemId, new String[]{nextWorkItem.getId()});

Metoda o sa trimita procesul reprezentat de processInstanceId dintr-un item, currentWorkItemId, catre unul sau mai multe workItem-uri. Pentru urmatoarele work item-uri o sa se construiasca un array de stringuri cu id-urile lor.

#####5.*Cum obtinem lista myTasks pentru un USER sau ROL*

    WMFilter wmFilter = WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipant("Administrator").addWorkItemName("Automatizare asteptare");
    WMWorkItemIterator wmWorkItemIterator = wfmcService.listWorkItems(wmFilter, true);

Prima data ne construim un wmFilter cu ajutorul unui WMFilterBuilder dupa care apelam metoda listWorkItems avand ca parametrii filtrul creat mai devreme si o valoare booleana care atunci cand e true putem accesa numarul de inregistrari returnat. Aceasta metoda returneaza un iterator de work item-uri.

#####6.*Cum facem claim Task*

    wfmcService.reassignWorkItem("ELO Service", "Administrator", procInstId, curentWorkItemId);

Prin aceasta metoda vom aloca un work item care apartine, in cazul nostru, utilizatorului ELO Service, catre utilizatorul "Administrator". Datele necesare pentru a identifica work item-ul sunt procInstId, id-ul procesului, si currentWorkItemId, id-ul work item-ului.

#####7.*Abort process instance*

    wfmcService.abortProcessInstance(processInstanceId);

Prin aceasta metoda intrerupem procesul reprezentat de processInstanceId.

###8.Flux complet

Este necesar un fisier de forma wapi-elo-renns.properties:

    #######------------------------------------------------------------------------------
    #######WAPI Service Cache configuration file type
    #######------------------------------------------------------------------------------
    
    #######------------------------------------------------------------------------------
    #######COMMON SERVICE CONFIGURATION PARAMETERS
    #######------------------------------------------------------------------------------
    #######class named used for the service implementation
    instance.name=wapi-elo-renns
    instance.class=org.wfmc.elo.WfmcServiceImpl_Elo
    
    #######------------------------------------------------------------------------------
    #######SPECIFIC SERVICE CONFIGURATION PARAMETERS
    #######------------------------------------------------------------------------------
    service.cache.instance.configuration=wapi-cache.properties
    
    workflow.sord.location.template.path=/workflows/${UAT}

Acest fisier este folosit la instantierea service-ului de WfMC.

Clasa [DemoFluxHotarareConsiliuLocalAprobat](http://git-components.teamnet.ro/blob/components%2Fjava%2Fwfmc-project.git/master/wfmc-test%2Fsrc%2Fmain%2Fjava%2Forg%2Fwfmc%2FDemoFluxAprobareOperatiuniAprobat.java) din wfmc-test :

        public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        String serviceProperties = "D:\\projects\\wfmc-project\\wfmc-test\\src\\main\\resources\\wapi-elo-renns.properties";
        String processInstanceName = "Instanta flux hotarare consiliu local 2";

        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(serviceProperties);
        WfmcService wfmcService = wfmcServiceFactory.getInstance();

        wfmcService.connect(new WMConnectInfo("Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));
        // Pas 1. Create process instance
        String procInstIdTemp = wfmcService.createProcessInstance("5", processInstanceName);

        //Pas 2. Assign process instance attributes
        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "UAT", 1);
        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "String", "test");
        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "Double", 1.0);

        //Pas 3. Start process instance
        String processInstanceId = wfmcService.startProcess(procInstIdTemp);

        //Pas 4. Get avaible task for Automatizare Asteptare and user = ELO Service.
        WMFilter wmFilter = WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipant("ELO Service").
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
        WMFilter wmFilterForAndra = WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipant("Andra").
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
                wfmcService.setTransition(processInstanceId, currentWorkItemId, new String[]{wmWorkItem.getId()});
            }
        }

        //Pas 9. Check if workflow was finished
        WMFilter wmFilter2 = WMFilterBuilder.createWMFilterProcessInstance().addProcessInstanceName(processInstanceName);
        WMProcessInstanceIterator wmProcessInstanceIterator = wfmcService.listProcessInstances(wmFilter2, true);
        while (wmProcessInstanceIterator.hasNext()) {
            WMProcessInstance wmProcessInstanceTemp = wmProcessInstanceIterator.tsNext();
            System.out.println(wmProcessInstanceTemp == null ? "null process instance" : "Process instance id = " + wmProcessInstanceTemp.getId());
            System.out.println(wmProcessInstanceTemp == null ? "null process instance" : "Process instance name = " + wmProcessInstanceTemp.getName());
            System.out.println(wmProcessInstanceTemp == null || wmProcessInstance.getState() == null ? "Process instance state = " + "null": "Process instance state = " + wmProcessInstanceTemp.getState().stringValue() );
        }
    }