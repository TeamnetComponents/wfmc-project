//package org.wfmc.xpdl.parser.dom4j;
//
//import org.dom4j.Namespace;
//import org.dom4j.QName;
//import org.wfmc.xpdl.XPDLNames;
//
///**
// * @author Adrian Price
// */
//public interface DOM4JNames extends XPDLNames {
//    // Namespaces and QNames required by OBE's XPDL extended attributes.
//    Namespace XSD_NS = new Namespace(XSD_NS_PREFIX, XSD_NS_URI);
//    Namespace XSI_NS = new Namespace(XSI_NS_PREFIX, XSI_URI);
//    Namespace XPDL_NS = new Namespace(XPDL_NS_PREFIX, XPDL_NS_URI);
//    //Namespace OBE_NS = new Namespace(OBE_NS_PREFIX, OBE_NS_URI);
//    QName XSI_SCHEMA_LOCATION_QNAME = new QName(XSI_SCHEMA_LOCATION, XSI_NS);
//    QName BLOCKACTIVITY_QNAME = new QName(BLOCKACTIVITY, XPDL_NS);
//    QName CONDITION_QNAME = new QName(CONDITION, XPDL_NS);
//    QName DESCRIPTION_QNAME = new QName(DESCRIPTION, XPDL_NS);
//    QName EXTENDED_ATTRIBUTES_QNAME = new QName(EXTENDED_ATTRIBUTES, XPDL_NS);
//    QName EXTERNAL_REFERENCE_QNAME = new QName(EXTERNAL_REFERENCE, XPDL_NS);
//    QName ACTUAL_PARAMETERS_QNAME = new QName(ACTUAL_PARAMETERS, XPDL_NS);
//    QName FORMAL_PARAMETERS_QNAME = new QName(FORMAL_PARAMETERS, XPDL_NS);
////    QName ASSIGNMENT_STRATEGY_QNAME = new QName(ASSIGNMENT_STRATEGY, OBE_NS);
////    QName BOUNDS_QNAME = new QName(BOUNDS, OBE_NS);
////    QName EVENT_QNAME = new QName(EVENT, OBE_NS);
////    QName TIMER_QNAME = new QName(TIMER, OBE_NS);
////    QName META_DATA_QNAME = new QName(META_DATA);
////    QName LOOP_QNAME = new QName(LOOP, OBE_NS);
////    QName WHILE_QNAME = new QName(WHILE, OBE_NS);
////    QName UNTIL_QNAME = new QName(UNTIL, OBE_NS);
////    QName FOREACH_QNAME = new QName(FOR_EACH, OBE_NS);
////    QName IN_QNAME = new QName(IN, OBE_NS);
//}