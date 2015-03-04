/*--

 Copyright (C) 2002-2005 Adrian Price.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows
    these conditions in the documentation and/or other materials
    provided with the distribution.

 3. The names "OBE" and "Open Business Engine" must not be used to
 	endorse or promote products derived from this software without prior
 	written permission.  For written permission, please contact
 	adrianprice@sourceforge.net.

 4. Products derived from this software may not be called "OBE" or
 	"Open Business Engine", nor may "OBE" or "Open Business Engine"
 	appear in their name, without prior written permission from
 	Adrian Price (adrianprice@users.sourceforge.net).

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR(S) BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 For more information on OBE, please see
 <http://obe.sourceforge.net/>.

 */

package org.wfmc.xpdl;

import org.wfmc.util.W3CNames;

/**
 * Provides string constants for the XPDL tag names.
 *
 * @author Anthony Eden
 * @author Adrian Price
 */
public interface XPDLNames extends W3CNames {
    /**
     * Namespace prefix to use for XPDL elements.
     */
    String XPDL_NS_PREFIX = "xpdl";
    /**
     * The XPDL namespace URI.
     */
    String XPDL_NS_URI = "http://www.wfmc.org/2002/XPDL1.0";
    /**
     * The XPDL schema URI.
     */
    String XPDL_SCHEMA_LOCATION =
        "http://www.wfmc.org/2002/XPDL1.0 xpdl-1.0.xsd";

    /**
     * Unique identifier.
     */
    String ID = "Id";

    /**
     * Entity name.
     */
    String NAME = "Name";

    /**
     * Tag which defines a brief description of an element.
     */
    String DESCRIPTION = "Description";

    String EXTENDED_ATTRIBUTES = "ExtendedAttributes";
    String EXTENDED_ATTRIBUTE = "ExtendedAttribute";
    String VALUE = "Value";

    /**
     * Tag which identifies a documentation URL.
     */
    String DOCUMENTATION = "Documentation";

    /**
     * Tag which identifies an icon URL.
     */
    String ICON = "Icon";

    /**
     * The main enclosing tag which defines a complete package.
     */
    String PACKAGE = "Package";

    String PACKAGE_HEADER = "PackageHeader";
    String REDEFINABLE_HEADER = "RedefinableHeader";
    String CONFORMANCE_CLASS = "ConformanceClass";
    String GRAPH_CONFORMANCE = "GraphConformance";

    /**
     * Tag which identifies the XPDL version.
     */
    String XPDL_VERSION = "XPDLVersion";

    /**
     * Tag which identifiers the workflow designer vendor.
     */
    String VENDOR = "Vendor";

    /**
     * Tag which represents a creation date.
     */
    String CREATED = "Created";

    /**
     * Tag which represents an entity author.
     */
    String AUTHOR = "Author";

    /**
     * Tag which represents an entity version.
     */
    String VERSION = "Version";
    String CODEPAGE = "Codepage";
    String COUNTRYKEY = "Countrykey";
    String PUBLICATION_STATUS = "PublicationStatus";

    String PARTICIPANTS = "Participants";
    String PARTICIPANT = "Participant";
    String PARTICIPANT_TYPE = "ParticipantType";

    String APPLICATIONS = "Applications";
    String APPLICATION = "Application";

    String WORKFLOW_PROCESSES = "WorkflowProcesses";
    String WORKFLOW_PROCESS = "WorkflowProcess";
    String ACCESS_LEVEL = "AccessLevel";
    String PROCESS_HEADER = "ProcessHeader";
    String EXTERNAL_PACKAGES = "ExternalPackages";
    String EXTERNAL_PACKAGE = "ExternalPackage";
    String HREF = "href";

    String PRIORITY = "Priority";
    String PRIORITY_UNIT = "PriorityUnit";
    String COST = "Cost";
    String COST_UNIT = "CostUnit";
    String RESPONSIBLES = "Responsibles";
    String RESPONSIBLE = "Responsible";

    String LIMIT = "Limit";
    String VALID_FROM = "ValidFrom";
    String VALID_TO = "ValidTo";
    String TIME_ESTIMATION = "TimeEstimation";

    String DURATION = "Duration";
    String DURATION_UNIT = "DurationUnit";

    String SIMULATION_INFORMATION = "SimulationInformation";
    String INSTANTIATION_TYPE = "InstantiationType";
    String WAITING_TIME = "WaitingTime";
    String WORKING_TIME = "WorkingTime";

    String ACTIVITIES = "Activities";
    String ACTIVITY = "Activity";
    String ACTIVITY_SET = "ActivitySet";
    String ACTIVITY_SETS = "ActivitySets";

    String SUBFLOW = "SubFlow";
    String TOOL = "Tool";
    String NO = "No";

    String EXECUTION = "Execution";

    String FORMAL_PARAMETERS = "FormalParameters";
    String FORMAL_PARAMETER = "FormalParameter";
    String INDEX = "Index";
    String MODE = "Mode";

    String ACTUAL_PARAMETERS = "ActualParameters";
    String ACTUAL_PARAMETER = "ActualParameter";

    String DATA_FIELDS = "DataFields";
    String DATA_FIELD = "DataField";
    String EXPIRATION = "Expiration";
    String INITIAL_VALUE = "InitialValue";
    String IS_ARRAY = "IsArray";
    String LENGTH = "Length";

    String ROUTE = "Route";
    String IMPLEMENTATION = "Implementation";
    String BLOCKACTIVITY = "BlockActivity";
    String BLOCKID = "BlockId";
    String PERFORMER = "Performer";
    String START_MODE = "StartMode";
    String FINISH_MODE = "FinishMode";
    String MANUAL = "Manual";
    String AUTOMATIC = "Automatic";

    String TRANSITION_RESTRICTIONS = "TransitionRestrictions";
    String TRANSITION_RESTRICTION = "TransitionRestriction";
    String JOIN = "Join";
    String SPLIT = "Split";

    String BLOCK_NAME = "BlockName";
    String BEGIN = "Begin";
    String END = "End";

    String TRANSITIONS = "Transitions";
    String TRANSITION = "Transition";
    String FROM = "From";
    String TO = "To";

    String TRANSITION_REFERENCES = "TransitionRefs";
    String TRANSITION_REFERENCE = "TransitionRef";

    String CONDITION = "Condition";
    String XPRESSION = "Xpression";

    String TYPE_DECLARATIONS = "TypeDeclarations";
    String TYPE_DECLARATION = "TypeDeclaration";

    String TYPE = "Type";
    String MEMBER = "Member";
    String DATA_TYPE = "DataType";
    String PLAIN_TYPE = "PlainType";
    String BASIC_TYPE = "BasicType";
    String SCHEMA_TYPE = "SchemaType";
    String RECORD_TYPE = "RecordType";
    String UNION_TYPE = "UnionType";
    String ENUMERATION_TYPE = "EnumerationType";
    String ENUMERATION_VALUE = "EnumerationValue";
    String ARRAY_TYPE = "ArrayType";
    String LOWER_INDEX = "LowerIndex";
    String UPPER_INDEX = "UpperIndex";
    String LIST_TYPE = "ListType";
    String DECLARED_TYPE = "DeclaredType";

    String EXTERNAL_REFERENCE = "ExternalReference";
    String NAMESPACE = "namespace";
    String LOCATION = "location";
    String XREF = "xref";

    String SCRIPT = "Script";
    String GRAMMAR = "Grammar";

    String DEADLINE = "Deadline";
    String DEADLINE_CONDITION = "DeadlineCondition";
    String EXCEPTION_NAME = "ExceptionName";

    String BOOLEAN_KIND = "BOOLEAN";
    String PERFORMER_KIND = "PERFORMER";
    String STRING_KIND = "STRING";
    String FLOAT_KIND = "FLOAT";
    String INTEGER_KIND = "INTEGER";
    String REFERENCE_KIND = "REFERENCE";
    String DATETIME_KIND = "DATETIME";
    String UNION_KIND = "UNION";
    String ENUMERATION_KIND = "ENUMERATION";
    String ARRAY_KIND = "ARRAY";
    String LIST_KIND = "LIST";
    String EXTERNAL_REFERENCE_KIND = "EXTERNAL_REFERENCE";
    String SCHEMA_KIND = "SCHEMA";
    String APPLICATION_KIND = "APPLICATION";
    String PROCEDURE_KIND = "PROCEDURE";
    String CONDITION_KIND = "CONDITION";
    String OTHERWISE_KIND = "OTHERWISE";
    String EXCEPTION_KIND = "EXCEPTION";
    String DEFAULTEXCEPTION_KIND = "DEFAULTEXCEPTION";
    String OUT_KIND = "OUT";
    String IN_KIND = "IN";
    String INOUT_KIND = "INOUT";
    String PUBLIC_KIND = "PUBLIC";
    String PRIVATE_KIND = "PRIVATE";
    String SYNCHR_KIND = "SYNCHR";
    String ASYNCHR_KIND = "ASYNCHR";
    String NON_BLOCKED_KIND = "NON_BLOCKED";
    String LOOP_BLOCKED_KIND = "LOOP_BLOCKED";
    String FULL_BLOCKED_KIND = "FULL_BLOCKED";
    String ONCE_KIND = "ONCE";
    String MULTIPLE_KIND = "MULTIPLE";
    String UNDER_REVISION_KIND = "UNDER_REVISION";
    String UNDER_TEST_KIND = "UNDER_TEST";
    String RELEASED_KIND = "RELEASED";
    String RESOURCE_SET_KIND = "RESOURCE_SET";
    String RESOURCE_KIND = "RESOURCE";
    String ROLE_KIND = "ROLE";
    String ORGANIZATIONAL_UNIT_KIND = "ORGANIZATIONAL_UNIT";
    String HUMAN_KIND = "HUMAN";
    String SYSTEM_KIND = "SYSTEM";
    String AND_KIND = "AND";
    String XOR_KIND = "XOR";
    String AUTOMATIC_KIND = "AUTOMATIC";
    String MANUAL_KIND = "MANUAL";
}