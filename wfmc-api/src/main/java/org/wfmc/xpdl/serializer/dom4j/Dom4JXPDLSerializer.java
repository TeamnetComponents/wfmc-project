///*--
//
// Copyright (C) 2002-2005 Adrian Price.
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
//
// 1. Redistributions of source code must retain the above copyright
//    notice, this list of conditions, and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions, and the disclaimer that follows
//    these conditions in the documentation and/or other materials
//    provided with the distribution.
//
// 3. The names "OBE" and "Open Business Engine" must not be used to
// 	endorse or promote products derived from this software without prior
// 	written permission.  For written permission, please contact
// 	adrianprice@sourceforge.net.
//
// 4. Products derived from this software may not be called "OBE" or
// 	"Open Business Engine", nor may "OBE" or "Open Business Engine"
// 	appear in their name, without prior written permission from
// 	Adrian Price (adrianprice@users.sourceforge.net).
//
// THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
// OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR(S) BE LIABLE FOR ANY DIRECT,
// INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
// HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
// STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
// IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.
//
// For more information on OBE, please see
// <http://obe.sourceforge.net/>.
//
// */
//
//package org.wfmc.xpdl.serializer.dom4j;
//
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentFactory;
//import org.dom4j.Element;
//import org.dom4j.io.OutputFormat;
//import org.dom4j.io.XMLWriter;
//import org.wfmc.xpdl.XPDLMessages;
//import org.wfmc.xpdl.model.activity.*;
//import org.wfmc.xpdl.model.application.Application;
//import org.wfmc.xpdl.model.condition.Condition;
//import org.wfmc.xpdl.model.condition.ConditionType;
//import org.wfmc.xpdl.model.condition.Xpression;
//import org.wfmc.xpdl.model.data.*;
//import org.wfmc.xpdl.model.ext.AssignmentStrategyDef;
//import org.wfmc.xpdl.model.ext.Loop;
//import org.wfmc.xpdl.model.misc.*;
//import org.wfmc.xpdl.model.participant.Participant;
//import org.wfmc.xpdl.model.pkg.ExternalPackage;
//import org.wfmc.xpdl.model.pkg.PackageHeader;
//import org.wfmc.xpdl.model.pkg.XPDLPackage;
//import org.wfmc.xpdl.model.workflow.ProcessHeader;
//import org.wfmc.xpdl.model.workflow.WorkflowProcess;
//import org.wfmc.xpdl.parser.dom4j.DOM4JNames;
//import org.wfmc.xpdl.serializer.ElementRequiredException;
//import org.wfmc.xpdl.serializer.XPDLSerializer;
//import org.wfmc.xpdl.serializer.XPDLSerializerException;
//
//import java.awt.*;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * Implementation of the XPDLSerializer interface which uses the DOM4J library
// * to serialize a package to an XPDL document.
// *
// * @author Anthony Eden
// * @author Adrian Price
// */
//public class Dom4JXPDLSerializer implements XPDLSerializer, DOM4JNames {
//    private static final String DEFAULT_XPDL_VERSION = "1.0";
//    private static final String DEFAULT_VENDOR = "obe.sourceforge.net";
//
//    public void serialize(XPDLPackage pkg, OutputStream out)
//            throws IOException, XPDLSerializerException {
//
//        serialize(pkg, new OutputStreamWriter(out));
//    }
//
//    /**
//     * Serialize the package to the given output stream.
//     *
//     * @param pkg The Package
//     * @param out The OutputStream
//     * @throws java.io.IOException     Any I/O exception
//     * @throws XPDLSerializerException Any serializer Exception
//     */
//    public void serialize(XPDLPackage pkg, Writer out)
//            throws IOException, XPDLSerializerException {
//
//        // Create the Document object
//        DocumentFactory df = DocumentFactory.getInstance();
//        Element pkgElement = df.createElement(PACKAGE, XPDL_NS_URI);
//        Document document = df.createDocument(pkgElement);
//
//        // Serialize the XPDLPackage
//        pkgElement.addAttribute(ID, pkg.getId());
//        pkgElement.addAttribute(NAME, pkg.getName());
////        pkgElement.add(new Namespace("", XPDL_NS_URI));
//        pkgElement.addNamespace("", XPDL_NS_URI);
//        pkgElement.addNamespace(XPDL_NS_PREFIX, XPDL_NS_URI);
//        pkgElement.addNamespace(XSD_NS_PREFIX, XSD_NS_URI);
//        pkgElement.addNamespace(XSI_NS_PREFIX, XSI_URI);
//        //pkgElement.addNamespace(OBE_NS_PREFIX, OBE_NS_URI);
//        pkgElement.addAttribute(XSI_SCHEMA_LOCATION_QNAME,
//                XPDL_SCHEMA_LOCATION);
//
//        // Store any additional XML namespace declarations, assuming there
//        // aren't any collisions in prefix usage (shouldn't be, since generally
//        // they'll all have been read from the document in the first place).
//        Map pkgNamespaces = pkg.getNamespaces();
//        for (Iterator iter = pkgNamespaces.entrySet().iterator();
//             iter.hasNext(); ) {
//            Map.Entry ns = (Map.Entry) iter.next();
//            String prefix = (String) ns.getKey();
//            if (pkgElement.getNamespaceForPrefix(prefix) == null)
//                pkgElement.addNamespace(prefix, (String) ns.getValue());
//        }
//
//        writePackageHeader(pkg.getPackageHeader(), pkgElement);
//        writeRedefinableHeader(pkg.getRedefinableHeader(), pkgElement);
//        writeConformanceClass(pkg.getConformanceClass(), pkgElement);
//        writeScript(pkg.getScript(), pkgElement);
//        writeExternalPackages(pkg.getExternalPackage(), pkgElement);
//        writeTypeDeclarations(pkg.getTypeDeclaration(), pkgElement);
//        writeParticipants(pkg.getParticipant(), pkgElement);
//        writeApplications(pkg.getApplication(), pkgElement);
//        writeDataFields(pkg.getDataField(), pkgElement);
//        writeWorkflowProcesses(pkg.getWorkflowProcess(), pkgElement);
//        writeExtendedAttributes(pkg.getExtendedAttributes(), pkgElement);
//
//        // OBE XPDL extensions.
//        //writeExtendedAttribute(OBE_CALENDAR, pkg.getCalendar(), pkgElement);
//        writeAssignmentStrategy(pkg.getAssignmentStrategy(), pkgElement);
//        //writeExtendedAttribute(OBE_COMPLETION_STRATEGY, pkg.getCompletionStrategy(), pkgElement);
//
//        // Write the document to the output stream.
//        OutputFormat format = new OutputFormat("    ", true);
//        XMLWriter writer = new XMLWriter(out, format);
//        writer.write(document);
//        out.flush();
//    }
//
//    protected void writePackageHeader(PackageHeader header, Element parent)
//            throws XPDLSerializerException {
//
//        if (header == null)
//            throw new ElementRequiredException("Package header required");
//
//        Element headerElement = Util.addElement(parent, PACKAGE_HEADER);
//        Util.addElement(headerElement, XPDL_VERSION, header.getXPDLVersion(),
//                DEFAULT_XPDL_VERSION);
//        Util.addElement(headerElement, VENDOR, header.getVendor(),
//                DEFAULT_VENDOR);
//        Util.addElement(headerElement, CREATED, header.getCreated(),
//                new Date());
//        Util.addElement(headerElement, DESCRIPTION, header.getDescription());
//        Util.addElement(headerElement, DOCUMENTATION,
//                header.getDocumentation());
//        Util.addElement(headerElement, PRIORITY_UNIT, header.getPriorityUnit());
//        Util.addElement(headerElement, COST_UNIT, header.getCostUnit());
//    }
//
//    protected void writeRedefinableHeader(RedefinableHeader header,
//                                          Element parent) {
//
//        if (header == null)
//            return;
//
//        Element headerElement = Util.addElement(parent, REDEFINABLE_HEADER);
//
//        Util.addElement(headerElement, AUTHOR, header.getAuthor());
//        Util.addElement(headerElement, VERSION, header.getVersion());
//        Util.addElement(headerElement, CODEPAGE, header.getCodepage());
//        Util.addElement(headerElement, COUNTRYKEY, header.getCountrykey());
//
//        writeResponsibles(header.getResponsible(), headerElement);
//
//        PublicationStatus publicationStatus = header.getPublicationStatus();
//        if (publicationStatus != null) {
//            headerElement.addAttribute(PUBLICATION_STATUS,
//                    publicationStatus.toString());
//        }
//    }
//
//    protected void writeConformanceClass(ConformanceClass cc, Element parent) {
//        if (cc == null) {
//            //_logger.debug("No conformance class specified");
//            return;
//        }
//
//        //_logger.debug("Writing conformance class: " + cc);
//        Element ccElement = Util.addElement(parent, CONFORMANCE_CLASS);
//        GraphConformance gc = cc.getGraphConformance();
//        if (gc != null)
//            ccElement.addAttribute(GRAPH_CONFORMANCE, gc.toString());
//    }
//
//    protected void writeScript(Script script, Element parent) {
//        if (script == null) {
//            //_logger.debug("No script language specified");
//            return;
//        }
//
//        Element scriptElement = Util.addElement(parent, SCRIPT);
//        scriptElement.addAttribute(TYPE, script.getType());
//        scriptElement.addAttribute(VERSION, script.getVersion());
//        scriptElement.addAttribute(GRAMMAR, script.getGrammar());
//    }
//
//    protected void writeExternalPackages(ExternalPackage[] externalPackages,
//                                         Element parent) throws XPDLSerializerException {
//
//        if (externalPackages == null || externalPackages.length == 0)
//            return;
//
//        Element externalPackagesElement = Util.addElement(parent,
//                EXTERNAL_PACKAGES);
//        for (int i = 0; i < externalPackages.length; i++) {
//            ExternalPackage externalPackage = externalPackages[i];
//            Element externalPackageElement = Util.addElement(
//                    externalPackagesElement, EXTERNAL_PACKAGE);
//
//            externalPackageElement.addAttribute(HREF,
//                    externalPackage.getHref());
//
//            writeExtendedAttributes(externalPackage.getExtendedAttributes(),
//                    externalPackageElement);
//        }
//    }
//
//    protected void writeTypeDeclarations(TypeDeclaration[] typeDeclarations,
//                                         Element parent) throws XPDLSerializerException {
//
//        if (typeDeclarations == null || typeDeclarations.length == 0)
//            return;
//
//        Element typeDeclarationsElement = Util.addElement(parent,
//                TYPE_DECLARATIONS);
//        for (int i = 0; i < typeDeclarations.length; i++) {
//            TypeDeclaration typeDeclaration = typeDeclarations[i];
//            Element typeDeclarationElement = Util.addElement(
//                    typeDeclarationsElement, TYPE_DECLARATION);
//
//            typeDeclarationElement.addAttribute(ID, typeDeclaration.getId());
//            typeDeclarationElement.addAttribute(NAME,
//                    typeDeclaration.getName());
//
//            writeType(typeDeclaration.getType(), typeDeclarationElement);
//            Util.addElement(typeDeclarationElement, DESCRIPTION,
//                    typeDeclaration.getDescription());
//            writeExtendedAttributes(typeDeclaration.getExtendedAttributes(),
//                    typeDeclarationElement);
//        }
//    }
//
//    protected void writeParticipants(Participant[] participants, Element parent)
//            throws XPDLSerializerException {
//
//        if (participants == null || participants.length == 0)
//            return;
//
//        Element participantsElement = Util.addElement(parent, PARTICIPANTS);
//        for (int i = 0; i < participants.length; i++) {
//            Participant participant = participants[i];
//            Element participantElement = Util.addElement(participantsElement,
//                    PARTICIPANT);
//
//            participantElement.addAttribute(ID, participant.getId());
//            if (participant.getName() != null)
//                participantElement.addAttribute(NAME, participant.getName());
//
//            Element participantTypeElement = Util.addElement(participantElement,
//                    PARTICIPANT_TYPE);
//            participantTypeElement.addAttribute(TYPE,
//                    participant.getParticipantType().toString());
//
//            Util.addElement(participantElement, DESCRIPTION,
//                    participant.getDescription());
//
//            ExternalReference extRef = participant.getExternalReference();
//            if (extRef != null)
//                writeExternalReference(extRef, participantElement);
//
//            writeExtendedAttributes(participant.getExtendedAttributes(),
//                    participantElement);
//
//            //writeExtendedAttribute(OBE_CALENDAR, participant.getCalendar(), participantElement);
//        }
//    }
//
//    protected void writeApplications(Application[] applications, Element parent)
//            throws XPDLSerializerException {
//
//        if (applications == null || applications.length == 0)
//            return;
//
//        Element applicationsElement = Util.addElement(parent, APPLICATIONS);
//        for (int i = 0; i < applications.length; i++) {
//            Application application = applications[i];
//            Element applicationElement = Util.addElement(applicationsElement,
//                    APPLICATION);
//
//            applicationElement.addAttribute(ID, application.getId());
//            if (application.getName() != null)
//                applicationElement.addAttribute(NAME, application.getName());
//
//            if (application.getDescription() != null) {
//                Util.addElement(applicationElement, DESCRIPTION,
//                        application.getDescription());
//            }
//
//            writeFormalParameters(application.getFormalParameter(), applicationElement);
//            writeExternalReference(application.getExternalReference(),
//                    applicationElement);
//            writeExtendedAttributes(application.getExtendedAttributes(),
//                    applicationElement);
//        }
//    }
//
//    protected void writeDataFields(DataField[] dataFields, Element parent)
//            throws XPDLSerializerException {
//
//        if (dataFields == null || dataFields.length == 0)
//            return;
//
//        Element dataFieldsElement = Util.addElement(parent, DATA_FIELDS);
//        for (int i = 0; i < dataFields.length; i++) {
//            DataField dataField = dataFields[i];
//            Element dataFieldElement = Util.addElement(dataFieldsElement,
//                    DATA_FIELD);
//
//            dataFieldElement.addAttribute(ID, dataField.getId());
//            if (dataField.getName() != null)
//                dataFieldElement.addAttribute(NAME, dataField.getName());
//            if (dataField.isArray()) {
//                dataFieldElement.addAttribute(IS_ARRAY,
//                        String.valueOf(dataField.isArray()));
//            }
//
//            writeDataType(dataField.getDataType(), dataFieldElement);
//
//            Util.addElement(dataFieldElement, INITIAL_VALUE,
//                    dataField.getInitialValue());
//
//            if (dataField.isArray()) {
//                Util.addElement(dataFieldElement, LENGTH,
//                        Integer.toString(dataField.getLength()));
//            }
//
//            Util.addElement(dataFieldElement, DESCRIPTION,
//                    dataField.getDescription());
//
//            writeExtendedAttributes(dataField.getExtendedAttributes(),
//                    dataFieldElement);
//        }
//    }
//
//    protected void writeDataType(DataType dataType, Element parent)
//            throws XPDLSerializerException {
//
//        if (dataType == null)
//            return;
//
//        Element dataTypeElement = Util.addElement(parent, DATA_TYPE);
//        writeType(dataType.getType(), dataTypeElement);
//    }
//
//    protected void writeResponsibles(String[] responsibles, Element parent) {
//        if (responsibles == null || responsibles.length == 0)
//            return;
//
//        Element responsiblesElement = Util.addElement(parent, RESPONSIBLES);
//        for (int i = 0; i < responsibles.length; i++)
//            Util.addElement(responsiblesElement, RESPONSIBLE, responsibles[i]);
//    }
//
//    protected void writeType(Type type, Element parent)
//            throws XPDLSerializerException {
//
//        Element typeElement;
//        if (type instanceof BasicType) {
//            typeElement = Util.addElement(parent, BASIC_TYPE);
//            typeElement.addAttribute(TYPE, type.toString());
//        } else if (type instanceof DeclaredType) {
//            typeElement = Util.addElement(parent, DECLARED_TYPE);
//            typeElement.addAttribute(ID, ((DeclaredType) type).getId());
//        } else if (type instanceof SchemaType) {
//            writeSchemaType((SchemaType) type, parent);
//        } else if (type instanceof ExternalReference) {
//            writeExternalReference((ExternalReference) type, parent);
//        } else if (type instanceof RecordType) {
//            typeElement = Util.addElement(parent, RECORD_TYPE);
//            Element memberElement = Util.addElement(typeElement, MEMBER);
//            RecordType recordType = (RecordType) type;
//            for (int i = 0, n = recordType.getMember().length; i < n; i++)
//                writeType(recordType.getMember(i), memberElement);
//        } else if (type instanceof UnionType) {
//            typeElement = Util.addElement(parent, UNION_TYPE);
//            Element memberElement = Util.addElement(typeElement, MEMBER);
//            UnionType unionType = (UnionType) type;
//            for (int i = 0, n = unionType.getMember().length; i < n; i++)
//                writeType(unionType.getMember(i), memberElement);
//        } else if (type instanceof EnumerationType) {
//            typeElement = Util.addElement(parent, ENUMERATION_TYPE);
//            EnumerationType enumerationType = (EnumerationType) type;
//            for (int i = 0; i < enumerationType.getValue().length; i++) {
//                EnumerationValue value = enumerationType.getValue(i);
//                Util.addElement(typeElement, ENUMERATION_VALUE,
//                        value.getName());
//            }
//        } else if (type instanceof ArrayType) {
//            ArrayType arrayType = (ArrayType) type;
//            typeElement = Util.addElement(parent, ARRAY_TYPE);
//            writeType(arrayType.getType(), typeElement);
//            typeElement.addAttribute(LOWER_INDEX,
//                    Integer.toString(arrayType.getLowerIndex()));
//            typeElement.addAttribute(UPPER_INDEX,
//                    Integer.toString(arrayType.getUpperIndex()));
//        } else if (type instanceof ListType) {
//            typeElement = Util.addElement(parent, LIST_TYPE);
//            writeType(((MultiValuedType) type).getType(), typeElement);
//        } else {
//            throw new XPDLSerializerException("Unsupported data type: " +
//                    type.getClass().getName());
//        }
//    }
//
//    protected void writeSchemaType(SchemaType schemaType, Element parent)
//            throws XPDLSerializerException {
//
//        try {
//            Element schemaTypeElement = Util.addElement(parent, SCHEMA_TYPE);
//            Util.importFromText(schemaType.getText(), schemaTypeElement);
//        } catch (DocumentException e) {
//            throw new XPDLSerializerException(e);
//        }
//    }
//
//    protected void writeWorkflowProcesses(WorkflowProcess[] workflowProcesses,
//                                          Element parent) throws XPDLSerializerException {
//
//        if (workflowProcesses == null || workflowProcesses.length == 0)
//            return;
//
//        Element wpsElement = Util.addElement(parent, WORKFLOW_PROCESSES);
//
//        for (int i = 0; i < workflowProcesses.length; i++)
//            writeWorkflowProcess(workflowProcesses[i], wpsElement);
//    }
//
//    protected void writeWorkflowProcess(WorkflowProcess wp, Element parent)
//            throws XPDLSerializerException {
//
//        Element wpElement = Util.addElement(parent, WORKFLOW_PROCESS);
//
//        wpElement.addAttribute(ID, wp.getId());
//        String name = wp.getName();
//        if (name != null)
//            wpElement.addAttribute(NAME, name);
//        AccessLevel accessLevel = wp.getAccessLevel();
//        if (accessLevel != null)
//            wpElement.addAttribute(ACCESS_LEVEL, accessLevel.toString());
//
//        ProcessHeader processHeader = wp.getProcessHeader();
//        if (processHeader == null)
//            throw new ElementRequiredException(
//                    XPDLMessages.PROCESS_HEADER_REQUIRED);
//        writeProcessHeader(processHeader, wpElement);
//        writeRedefinableHeader(wp.getRedefinableHeader(), wpElement);
//        writeFormalParameters(wp.getFormalParameter(), wpElement);
//        writeDataFields(wp.getDataField(), wpElement);
//        writeParticipants(wp.getParticipant(), wpElement);
//        writeApplications(wp.getApplication(), wpElement);
//        writeActivitySets(wp.getActivitySet(), wpElement);
//        writeActivities(wp.getActivity(), wpElement);
//        writeTransitions(wp.getTransition(), wpElement);
//        writeExtendedAttributes(wp.getExtendedAttributes(), wpElement);
//
//        // OBE XPDL extensions.
//        //writeExtendedAttribute(OBE_CALENDAR, wp.getCalendar(), wpElement);
//        writeAssignmentStrategy(wp.getAssignmentStrategy(), wpElement);
//        //writeExtendedAttribute(OBE_COMPLETION_STRATEGY, wp.getCompletionStrategy(), wpElement);
//        writeTrigger(wp.getTrigger(), wpElement);
//    }
//
//    protected void writeActivitySets(ActivitySet[] activitySets, Element parent)
//            throws XPDLSerializerException {
//
//        if (activitySets == null || activitySets.length == 0)
//            return;
//
//        Element activitySetsElement = Util.addElement(parent, ACTIVITY_SETS);
//
//        for (int i = 0; i < activitySets.length; i++)
//            writeActivitySet(activitySets[i], activitySetsElement);
//    }
//
//    protected void writeActivitySet(ActivitySet activitySet, Element parent)
//            throws XPDLSerializerException {
//
//        Element activitySetElement = Util.addElement(parent, ACTIVITY_SET);
//
//        activitySetElement.addAttribute(ID, activitySet.getId());
//
//        writeActivities(activitySet.getActivity(), activitySetElement);
//        writeTransitions(activitySet.getTransition(), activitySetElement);
//    }
//
//    protected void writeActivities(Activity[] activities, Element parent)
//            throws XPDLSerializerException {
//
//        if (activities == null || activities.length == 0)
//            return;
//
//        Element activitiesElement = Util.addElement(parent, ACTIVITIES);
//
//        for (int i = 0; i < activities.length; i++)
//            writeActivity(activities[i], activitiesElement);
//    }
//
//    protected void writeActivity(Activity activity, Element parent)
//            throws XPDLSerializerException {
//
//        Element activityElement = Util.addElement(parent, ACTIVITY);
//
//        activityElement.addAttribute(ID, activity.getId());
//        activityElement.addAttribute(NAME, activity.getName());
//
//        Util.addElement(activityElement, DESCRIPTION,
//                activity.getDescription());
//        Util.addElement(activityElement, LIMIT, activity.getLimit());
//
//        Implementation implementation = activity.getImplementation();
//        if (implementation != null) {
//            Element implementationElement = Util.addElement(activityElement,
//                    IMPLEMENTATION);
//            if (implementation instanceof ToolSet) {
//                ToolSet toolSet = (ToolSet) implementation;
//                for (int i = 0, n = toolSet.getTool().length; i < n; i++) {
//                    Tool tool = toolSet.getTool(i);
//                    Element toolElement = Util.addElement(
//                            implementationElement, TOOL);
//                    toolElement.addAttribute(ID, tool.getId());
//                    toolElement.addAttribute(TYPE,
//                            tool.getToolType().toString());
//                    writeActualParameters(tool.getActualParameter(),
//                            toolElement);
//                    Util.addElement(toolElement, DESCRIPTION,
//                            tool.getDescription());
//                    writeExtendedAttributes(tool.getExtendedAttributes(),
//                            toolElement);
//                }
//            } else if (implementation instanceof SubFlow) {
//                SubFlow subFlow = (SubFlow) implementation;
//                Element subFlowElement = Util.addElement(implementationElement,
//                        SUBFLOW);
//                subFlowElement.addAttribute(ID, subFlow.getId());
//                ExecutionType execution = subFlow.getExecution();
//                if (execution != null) {
//                    subFlowElement.addAttribute(EXECUTION,
//                            execution.toString());
//                }
//                writeActualParameters(subFlow.getActualParameter(),
//                        subFlowElement);
//            } else if (implementation instanceof NoImplementation) {
//                Util.addElement(implementationElement, NO);
//            } else {
//                throw new XPDLSerializerException(
//                        "Unknown implementation type: " +
//                                implementation.getClass());
//            }
//        }
//
//        Route route = activity.getRoute();
//        if (route != null) {
//            if (implementation != null) {
//                throw new XPDLSerializerException(
//                        "Activity cannot contain both a route and an implementation");
//            }
//
//            Util.addElement(activityElement, ROUTE);
//        }
//
//        BlockActivity blockActivity = activity.getBlockActivity();
//        if (blockActivity != null) {
//            if (implementation != null || route != null) {
//                throw new XPDLSerializerException(
//                        "Activity cannot contain a route or an implementation as well as a BlockActivity");
//            }
//
//            Element baElement = activityElement.addElement(BLOCKACTIVITY);
//            baElement.addAttribute(BLOCKID, blockActivity.getBlockId());
//        }
//
//        Util.addElement(activityElement, PERFORMER, activity.getPerformer());
//
//        AutomationMode startMode = activity.getStartMode();
//        if (startMode != null) {
//            Element startModeElement = Util.addElement(activityElement,
//                    START_MODE);
//            Util.addElement(startModeElement,
//                    startMode == AutomationMode.MANUAL ? MANUAL : AUTOMATIC);
//        }
//
//        AutomationMode finishMode = activity.getFinishMode();
//        if (finishMode != null) {
//            Element finishModeElement = Util.addElement(activityElement,
//                    FINISH_MODE);
//            Util.addElement(finishModeElement,
//                    finishMode == AutomationMode.MANUAL ? MANUAL : AUTOMATIC);
//        }
//
//        Util.addElement(activityElement, PRIORITY, activity.getPriority());
//
//        writeDeadlines(activity.getDeadline(), activityElement);
//        writeSimulationInformation(activity.getSimulationInformation(),
//                activityElement);
//
//        Util.addElement(activityElement, ICON, activity.getIcon());
//        Util.addElement(activityElement, DOCUMENTATION,
//                activity.getDocumentation());
//
//        writeTransitionRestrictions(activity.getTransitionRestriction(), activityElement);
//        writeExtendedAttributes(activity.getExtendedAttributes(),
//                activityElement);
//
//        // OBE XPDL extensions.
//        writeAssignmentStrategy(activity.getAssignmentStrategy(), activityElement);
//        writeBounds(activity.getBounds(), activityElement);
//        //writeExtendedAttribute(OBE_CALENDAR, activity.getCalendar(), activityElement);
//        //writeExtendedAttribute(OBE_COMPLETION_STRATEGY, activity.getCompletionStrategy(), activityElement);
//        //writeExtendedAttribute(OBE_TOOL_MODE, activity.getToolMode(), activityElement);
//        if (blockActivity != null)
//            writeLoop(blockActivity.getLoop(), activityElement);
//    }
//
//    protected void writeBounds(Rectangle bounds, Element parent) {
//        if (bounds == null)
//            return;
//
//        // For now, our parent element must be an ExtendedAttribute.
//        // Check whether the ExtendedAttributes element is already present.
//        // If not, add it now.
//        Element extAttrsElem = getExtendedAttributes(parent);
//        parent = Util.addElement(extAttrsElem, EXTENDED_ATTRIBUTE);
//        //parent.addAttribute(NAME, OBE_BOUNDS);
//
//        //Element boundsElement = parent.addElement(BOUNDS_QNAME);
//        //boundsElement.addAttribute(X, String.valueOf(bounds.x));
//        //boundsElement.addAttribute(Y, String.valueOf(bounds.y));
//        //boundsElement.addAttribute(WIDTH, String.valueOf(bounds.width));
//        //boundsElement.addAttribute(HEIGHT, String.valueOf(bounds.height));
//    }
//
//    protected void writeAssignmentStrategy(AssignmentStrategyDef strategy,
//                                           Element parent) {
//
//        if (strategy == null)
//            return;
//
//        // For now, our parent element must be an ExtendedAttribute.
//        // Check whether the ExtendedAttributes element is already present.
//        // If not, add it now.
//        Element extAttrsElem = getExtendedAttributes(parent);
//        parent = Util.addElement(extAttrsElem, EXTENDED_ATTRIBUTE);
//        //parent.addAttribute(NAME, OBE_ASSIGNMENT_STRATEGY);
//
//        //Element strategyElement = parent.addElement(ASSIGNMENT_STRATEGY_QNAME);
//        //strategyElement.addAttribute(ID, strategy.getId());
//        //strategyElement.addAttribute(EXPAND_GROUPS, String.valueOf(strategy.getExpandGroups()));
//    }
//
//    // This is an unused method that was an experiment in allowing repository
//    // metadata to be embedded in XPDL.
//    protected void writeMetaData(/*AbstractMetaData*/Object metadata,
//                                 Element parent) {
//
//        if (metadata == null)
//            return;
//
///*
//        // For now, our parent element must be an ExtendedAttribute.
//        // Check whether the ExtendedAttributes element is already present.
//        // If not, add it now.
//        Element extAttrsElem = getExtendedAttributes(parent);
//        parent = Util.addElement(extAttrsElem, EXTENDED_ATTRIBUTE);
//        parent.addAttribute(NAME, OBE_META_DATA);
//        Element metaDataElement = parent.addElement(META_DATA_QNAME);
//
//        // Marshall the *MetaData object into the meta-data element.
//        Marshaller marshaller = new Marshaller(metaDataElement);
//        marshaller.setMapping(mapping);
////        marshaller.setRootElement("repository");
////        if (getLogger().isDebugEnabled() && OBEConfig.isVerbose()) {
////            marshaller.setMarshalListener(new MarshalListener() {
////                public boolean preMarshal(Object obj) {
////                    getLogger().debug("preMarshal(\"" + obj + "\")");
////                    return true;
////                }
////
////                public void postMarshal(Object obj) {
////                    getLogger().debug("postMarshal(\"" + obj + "\")");
////                }
////            });
////        }
//        marshaller.marshal(metadata);
//*/
//    }
//
//    protected void writeLoop(Loop loop, Element parent) {
//        if (loop == null)
//            return;
//
//        // For now, our parent element must be an ExtendedAttribute.
//        // Check whether the ExtendedAttributes element is already present.
//        // If not, add it now.
//        Element extAttrsElem = getExtendedAttributes(parent);
//        parent = Util.addElement(extAttrsElem, EXTENDED_ATTRIBUTE);
//        parent.addAttribute(NAME, OBE_LOOP);
//
//        Element loopElement = parent.addElement(LOOP_QNAME);
//        LoopBody body = loop.getBody();
//        if (body instanceof ForEach) {
//            ForEach forEach = (ForEach) body;
//            Element forEachElement = loopElement.addElement(FOREACH_QNAME);
//            forEachElement.addAttribute(DATA_FIELD, forEach.getDataField());
//            Element inElement = forEachElement.addElement(IN_QNAME);
//            inElement.addText(forEach.getExpr());
//        } else if (body instanceof Until) {
//            Element untilElement = loopElement.addElement(UNTIL_QNAME);
//            writeCondition(((Until) body).getCondition(), untilElement);
//        } else if (body instanceof While) {
//            Element whileElement = loopElement.addElement(WHILE_QNAME);
//            writeCondition(((While) body).getCondition(), whileElement);
//        }
//    }
//
//    protected void writeTransitions(Transition[] transitions, Element parent)
//            throws XPDLSerializerException {
//
//        if (transitions == null || transitions.length == 0)
//            return;
//
//        Element transitionsElement = Util.addElement(parent, TRANSITIONS);
//
//        for (int i = 0; i < transitions.length; i++)
//            writeTransition(transitions[i], transitionsElement);
//    }
//
//    protected void writeTransition(Transition transition, Element parent)
//            throws XPDLSerializerException {
//
//        Element transitionElement = Util.addElement(parent, TRANSITION);
//
//        transitionElement.addAttribute(ID, transition.getId());
//        transitionElement.addAttribute(FROM, transition.getFrom());
//        transitionElement.addAttribute(TO, transition.getTo());
//
//        if (transition.getName() != null)
//            transitionElement.addAttribute(NAME, transition.getName());
//
//        writeCondition(transition.getCondition(), transitionElement);
//
//        if (transition.getDescription() != null) {
//            Util.addElement(transitionElement, DESCRIPTION,
//                    transition.getDescription());
//        }
//
//        writeExtendedAttributes(transition.getExtendedAttributes(),
//                transitionElement);
//
//        // OBE XPDL extensions.
//        writeTrigger(transition.getEvent(), transitionElement);
//        writeExecutionType(transition.getExecution(), transitionElement);
//    }
//
//    protected void writeTrigger(Trigger trigger, Element parent)
//            throws XPDLSerializerException {
//
//        if (trigger == null)
//            return;
//
//        // For now, our parent element must be an ExtendedAttribute.
//        // Check whether the ExtendedAttributes element is already present.
//        // If not, add it now.
//        Element extendedAttributesElement = parent.element(EXTENDED_ATTRIBUTES);
//        if (extendedAttributesElement == null) {
//            extendedAttributesElement = Util.addElement(parent,
//                    EXTENDED_ATTRIBUTES);
//        }
//        parent = Util.addElement(extendedAttributesElement, EXTENDED_ATTRIBUTE);
//
//        Element triggerElement;
//        if (trigger instanceof Event) {
//            triggerElement = writeEvent((Event) trigger, parent);
//        } else if (trigger instanceof Timer) {
//            triggerElement = writeTimer((Timer) trigger, parent);
//        } else {
//            throw new IllegalArgumentException("Unsupported trigger type: " +
//                    trigger);
//        }
//        triggerElement.addAttribute(ID, trigger.getId());
//        writeActualParameters(trigger.getActualParameter(), triggerElement);
//    }
//
//    protected Element writeEvent(Event event, Element parent) {
//        parent.addAttribute(NAME, OBE_EVENT);
//        Element eventElement = parent.addElement(EVENT_QNAME);
//        if (event.getPredicate() != null)
//            eventElement.addAttribute(PREDICATE, event.getPredicate());
//        return eventElement;
//    }
//
//    protected Element writeTimer(Timer timer, Element parent) {
//        parent.addAttribute(NAME, OBE_TIMER);
//        Element timerElement = parent.addElement(TIMER_QNAME);
//        if (timer.getInterval() != null) {
//            timerElement.addAttribute(INTERVAL,
//                    timer.getInterval().toString());
//        }
//        if (timer.getCalendar() != null)
//            timerElement.addAttribute(CALENDAR, timer.getCalendar());
//        if (timer.isRecoverable()) {
//            timerElement.addAttribute(RECOVERABLE, String.valueOf(
//                    timer.isRecoverable()));
//        }
//        return timerElement;
//    }
//
//    protected void writeExecutionType(ExecutionType execType, Element parent)
//            throws XPDLSerializerException {
//
//        if (execType == null)
//            return;
//
//        // For now, our parent element must be an ExtendedAttribute.
//        // Check whether the ExtendedAttributes element is already present.
//        // If not, add it now.
//        Element extendedAttributesElement = parent.element(EXTENDED_ATTRIBUTES);
//        if (extendedAttributesElement == null) {
//            extendedAttributesElement = Util.addElement(parent,
//                    EXTENDED_ATTRIBUTES);
//        }
//        parent = Util.addElement(extendedAttributesElement, EXTENDED_ATTRIBUTE);
//        parent.addAttribute(NAME, EXECUTION);
//        parent.addAttribute(VALUE, execType.toString());
//    }
//
//    protected void writeTransitionRestrictions(
//            TransitionRestriction[] transitionRestrictions, Element parent) {
//
//        if (transitionRestrictions == null ||
//                transitionRestrictions.length == 0) {
//
//            return;
//        }
//
//        Element transitionRestrictionsElement = Util.addElement(parent,
//                TRANSITION_RESTRICTIONS);
//
//        for (int i = 0; i < transitionRestrictions.length; i++) {
//            writeTransitionRestriction(transitionRestrictions[i],
//                    transitionRestrictionsElement);
//        }
//    }
//
//    protected void writeTransitionRestriction(TransitionRestriction
//                                                      transitionRestriction, Element parent) {
//        Element transitionRestrictionElement = Util.addElement(parent,
//                TRANSITION_RESTRICTION);
//        writeJoin(transitionRestriction.getJoin(),
//                transitionRestrictionElement);
//        writeSplit(transitionRestriction.getSplit(),
//                transitionRestrictionElement);
//    }
//
//    protected void writeJoin(Join join, Element parent) {
//        if (join == null)
//            return;
//
//        Element joinElement = Util.addElement(parent, JOIN);
//
//        JoinType joinType = join.getType();
//        if (joinType != null)
//            joinElement.addAttribute(TYPE, joinType.toString());
//    }
//
//    protected void writeSplit(Split split, Element parent) {
//        if (split == null)
//            return;
//
//        Element splitElement = Util.addElement(parent, SPLIT);
//
//        SplitType splitType = split.getType();
//        if (splitType != null)
//            splitElement.addAttribute(TYPE, splitType.toString());
//
//        writeTransitionReferences(split.getTransitionReference(),
//                splitElement);
//    }
//
//    protected void writeTransitionReferences(String[] transitionReferences,
//                                             Element parent) {
//
//        if (transitionReferences == null || transitionReferences.length == 0)
//            return;
//
//        Element transitionReferencesElement = Util.addElement(parent,
//                TRANSITION_REFERENCES);
//
//        for (int i = 0; i < transitionReferences.length; i++) {
//            Element transitionRefElement = Util.addElement(
//                    transitionReferencesElement, TRANSITION_REFERENCE);
//
//            transitionRefElement.addAttribute(ID, transitionReferences[i]);
//        }
//    }
//
//    protected void writeProcessHeader(ProcessHeader header, Element parent) {
//        Element headerElement = Util.addElement(parent, PROCESS_HEADER);
//
//        DurationUnit durationUnit = header.getDurationUnit();
//        if (durationUnit != null)
//            headerElement.addAttribute(DURATION_UNIT, durationUnit.toString());
//
//        Util.addElement(headerElement, CREATED, header.getCreated());
//        Util.addElement(headerElement, DESCRIPTION, header.getDescription());
//        Util.addElement(headerElement, PRIORITY, header.getPriority());
//        Util.addElement(headerElement, LIMIT, header.getLimit());
//        Util.addElement(headerElement, VALID_FROM, header.getValidFrom());
//        Util.addElement(headerElement, VALID_TO, header.getValidTo());
//        writeTimeEstimation(header.getTimeEstimation(), headerElement);
//    }
//
//    protected void writeDeadlines(Deadline[] deadlines, Element parent) {
//        if (deadlines == null || deadlines.length == 0) {
//            _logger.debug("No Deadlines found");
//            return;
//        }
//
//        for (int i = 0; i < deadlines.length; i++)
//            writeDeadline(deadlines[i], parent);
//    }
//
//    protected void writeDeadline(Deadline deadline, Element parent) {
//        if (deadline == null) {
//            _logger.debug("No Deadline found");
//            return;
//        }
//
//        _logger.debug("Writing Deadline element");
//        Element deadlineElement = Util.addElement(parent, DEADLINE);
//        deadlineElement.addAttribute(EXECUTION,
//                deadline.getExecutionType().toString());
//
//        Util.addElement(deadlineElement, DEADLINE_CONDITION,
//                deadline.getDeadlineCondition());
//        Util.addElement(deadlineElement, EXCEPTION_NAME,
//                deadline.getExceptionName());
//    }
//
//    protected void writeTimeEstimation(TimeEstimation timeEstimation,
//                                       Element parent) {
//
//        if (timeEstimation == null)
//            return;
//
//        Element timeEstimationElement = Util.addElement(parent,
//                TIME_ESTIMATION);
//        Util.addElement(timeEstimationElement, WAITING_TIME,
//                timeEstimation.getWaitingTime());
//        Util.addElement(timeEstimationElement, WORKING_TIME,
//                timeEstimation.getWorkingTime());
//        Util.addElement(timeEstimationElement, DURATION,
//                timeEstimation.getDuration());
//    }
//
//    protected void writeSimulationInformation(
//            SimulationInformation simulationInfo, Element parent) {
//
//        if (simulationInfo == null)
//            return;
//
//        Element simulationInfoElement = Util.addElement(parent,
//                SIMULATION_INFORMATION);
//
//        Util.addElement(simulationInfoElement, COST,
//                simulationInfo.getCost());
//
//        writeTimeEstimation(simulationInfo.getTimeEstimation(),
//                simulationInfoElement);
//    }
//
//    protected void writeCondition(Condition condition, Element parent) {
//        if (condition == null)
//            return;
//
//        Element conditionElement = parent.addElement(CONDITION);
//        ConditionType type = condition.getType();
//        if (type != null)
//            conditionElement.addAttribute(TYPE, type.toString());
//        if (condition.getValue() != null)
//            conditionElement.addText(condition.getValue());
//        for (int i = 0, n = condition.getXpression().length; i < n; i++) {
//            Xpression xpression = condition.getXpression(i);
//            Util.addElement(conditionElement, XPRESSION, xpression.getValue());
//        }
//    }
//
//    protected void writeExtendedAttributes(ExtendedAttributes extAttrs,
//                                           Element parent) throws XPDLSerializerException {
//
//        if (extAttrs == null)
//            return;
//
//        try {
//            Util.importFromText(extAttrs.getText(), parent);
//        } catch (DocumentException e) {
//            throw new XPDLSerializerException(e);
//        }
//    }
//
//    protected void writeFormalParameters(FormalParameter[] formalParameters,
//                                         Element parent) throws XPDLSerializerException {
//
//        if (formalParameters == null || formalParameters.length == 0)
//            return;
//
//        Element formalParametersElement = parent.addElement(FORMAL_PARAMETERS);
//        for (int i = 0; i < formalParameters.length; i++) {
//            FormalParameter formalParameter = formalParameters[i];
//            Element formalParameterElement = Util.addElement(
//                    formalParametersElement, FORMAL_PARAMETER);
//
//            formalParameterElement.addAttribute(ID, formalParameter.getId());
//            if (formalParameter.getIndex() != null) {
//                formalParameterElement.addAttribute(INDEX,
//                        formalParameter.getIndex().toString());
//            }
//            if (formalParameter.getMode() != null) {
//                formalParameterElement.addAttribute(MODE,
//                        formalParameter.getMode().toString());
//            }
//
//            writeType(formalParameter.getDataType().getType(),
//                    Util.addElement(formalParameterElement, DATA_TYPE));
//
//            Util.addElement(formalParameterElement, DESCRIPTION,
//                    formalParameter.getDescription());
//        }
//    }
//
//    protected void writeActualParameters(ActualParameter[] actualParameters,
//                                         Element parent) throws XPDLSerializerException {
//
//        if (actualParameters == null || actualParameters.length == 0)
//            return;
//
//        Element actualParametersElement = Util.addElement(parent,
//                ACTUAL_PARAMETERS);
//        for (int i = 0; i < actualParameters.length; i++) {
//            Util.addElement(actualParametersElement, ACTUAL_PARAMETER,
//                    actualParameters[i].getText());
//        }
//    }
//
//    protected void writeExternalReference(ExternalReference externalReference,
//                                          Element parent) throws XPDLSerializerException {
//
//        if (externalReference == null)
//            return;
//
//        Element element = parent.addElement(EXTERNAL_REFERENCE);
//        element.addAttribute(LOCATION, externalReference.getLocation());
//        String xref = externalReference.getXref();
//        if (xref != null)
//            element.addAttribute(XREF, xref);
//        String namespace = externalReference.getNamespace();
//        if (namespace != null)
//            element.addAttribute(NAMESPACE, namespace);
//    }
//
//    protected Element getExtendedAttributes(Element parent) {
//        // Check whether the ExtendedAttributes element is already present.
//        // If not, add it now.
//        Element extAttrsElem = parent.element(EXTENDED_ATTRIBUTES);
//        if (extAttrsElem == null)
//            extAttrsElem = Util.addElement(parent, EXTENDED_ATTRIBUTES);
//        return extAttrsElem;
//    }
//
//    protected void writeExtendedAttribute(String name, Object value,
//                                          Element parent) {
//
//        if (value == null)
//            return;
//
//        // For now, our parent element must be an ExtendedAttribute.
//        Element extAttrElem = Util.addElement(getExtendedAttributes(parent), EXTENDED_ATTRIBUTE);
//        extAttrElem.addAttribute(NAME, name);
//        extAttrElem.addAttribute(VALUE, value.toString());
//    }
//}