/*--

 Copyright (C) 2002 Anthony Eden.
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
    me@anthonyeden.com.

 4. Products derived from this software may not be called "OBE" or
    "Open Business Engine", nor may "OBE" or "Open Business Engine"
    appear in their name, without prior written permission from
    Anthony Eden (me@anthonyeden.com).

 In addition, I request (but do not require) that you include in the
 end-user documentation provided with the redistribution and/or in the
 software itself an acknowledgement equivalent to the following:
     "This product includes software developed by
      Anthony Eden (http://www.anthonyeden.com/)."

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

 For more information on OBE, please see <http://obe.sourceforge.net/>.

 */

package org.wfmc.wapi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * WfMC error codes.
 *
 * @author Anthony Eden, Adrian Price
 */
public class WMError implements Serializable {
    private static final long serialVersionUID = -8918399382127820849L;

    private static final String WM_SUCCESS_STR = "WM_SUCCESS";
    private static final String WM_CONNECT_FAILED_STR = "WM_CONNECT_FAILED";
    private static final String WM_INVALID_PROCESS_DEFINITION_STR =
        "WM_INVALID_PROCESS_DEFINITION";
    private static final String WM_INVALID_ACTIVITY_NAME_STR =
        "WM_INVALID_ACTIVITY_NAME";
    private static final String WM_INVALID_PROCESS_INSTANCE_STR =
        "WM_INVALID_PROCESS_INSTANCE";
    private static final String WM_INVALID_ACTIVITY_INSTANCE_STR =
        "WM_INVALID_ACTIVITY_INSTANCE";
    private static final String WM_INVALID_WORKITEM_STR = "WM_INVALID_WORKITEM";
    private static final String WM_INVALID_ATTRIBUTE_STR =
        "WM_INVALID_ATTRIBUTE";
    private static final String WM_ATTRIBUTE_ASSIGNMENT_FAILED_STR =
        "WM_INVALID_ATTRIBUTE_ASSIGNMENT_FAILED";
    private static final String WM_TRANSITION_NOT_ALLOWED_STR =
        "WM_TRANSITION_NOT_ALLOWED";
    private static final String WM_INVALID_SESSION_HANDLE_STR =
        "WM_INVALID_SESSION_HANDLE";
    private static final String WM_INVALID_QUERY_HANDLE_STR =
        "WM_INVALID_QUERY_HANDLE";
    private static final String WM_INVALID_SOURCE_USER_STR =
        "WM_INVALID_SOURCE_USER";
    private static final String WM_INVALID_TARGET_USER_STR =
        "WM_INVALID_TARGET_USER";
    private static final String WM_INVALID_FILTER_STR = "WM_INVALID_FILTER";
    private static final String WM_LOCKED_STR = "WM_LOCKED";
    private static final String WM_NOT_LOCKED_STR = "WM_NOT_LOCKED";
    private static final String WM_NO_MORE_DATA_STR = "WM_NO_MORE_DATA";
    private static final String WM_INSUFFICIENT_BUFFER_SIZE_STR =
        "WM_INSUFFICIENT_BUFFER_SIZE";
    private static final String WM_EXECUTE_FAILED_STR = "WM_EXECUTE_FAILED";
    private static final String WM_IOERROR_STR = "WM_IOERROR";
    private static final String WM_GENERAL_ERROR_STR = "WM_GENERAL_ERROR";

    private static final Map MAIN_CODE_TO_STRING = new HashMap();
    private static final Map SUB_CODE_TO_STRING = new HashMap();

    // These error codes are listed in wmerror.h in WFMC-TC-1013 version 1.4
    public static final int WM_SUCCESS = 0x00000000;
    public static final int WM_CONNECT_FAILED = 0x00100000;
    public static final int WM_INVALID_PROCESS_DEFINITION = 0x00200000;
    public static final int WM_INVALID_ACTIVITY_NAME = 0x00300000;
    public static final int WM_INVALID_PROCESS_INSTANCE = 0x00400000;
    public static final int WM_INVALID_ACTIVITY_INSTANCE = 0x00500000;
    public static final int WM_INVALID_WORKITEM = 0x00600000;
    public static final int WM_INVALID_ATTRIBUTE = 0x00700000;
    public static final int WM_ATTRIBUTE_ASSIGNMENT_FAILED = 0x00800000;
    public static final int WM_INVALID_STATE = 0x00900000;
    public static final int WM_TRANSITION_NOT_ALLOWED = 0x00A00000;
    public static final int WM_INVALID_SESSION_HANDLE = 0x00B00000;
    public static final int WM_INVALID_QUERY_HANDLE = 0x00C00000;
    public static final int WM_INVALID_SOURCE_USER = 0x00D00000;
    public static final int WM_INVALID_TARGET_USER = 0x00E00000;
    public static final int WM_INVALID_FILTER = 0x00F00000;
    public static final int WM_LOCKED = 0x00F10000;
    public static final int WM_NOT_LOCKED = 0x00F20000;
    public static final int WM_NO_MORE_DATA = 0x00F30000;
    public static final int WM_INSUFFICIENT_BUFFER_SIZE = 0x00F40000;
    public static final int WM_EXECUTE_FAILED = 0x00F50000;

    // These additional error codes are referenced in WFMC-TC-1009
    // Version 2.0e (Beta), but do not appear to be defined anywhere.
//    public static final int WM_INSUFFICENT_BUFFER_SIZE = ;
//    public static final int WM_APPLICATION_BUSY = ;
//    public static final int WM_INVALID_APPLICATION = ;
//    public static final int WM_APPLICATION_NOT_STARTED = ;
//    public static final int WM_APPLICATION_NOT_DEFINED = ;
//    public static final int WM_APPLICATION_NOT_STOPPED = ;
    public static final int WM_UNSUPPORTED = 0x00F60000;

    // These error codes don't seem to be defined anywhere.  Are they even WfMC?
    public static final int WM_IOERROR = 0x01000000;
    public static final int WM_GENERAL_ERROR = 0xffff0000;

    private int mainCode;
    private short subCode;

    static {
        MAIN_CODE_TO_STRING.put(new Integer(WM_SUCCESS),
            WM_SUCCESS_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_CONNECT_FAILED),
            WM_CONNECT_FAILED_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_INVALID_PROCESS_DEFINITION),
            WM_INVALID_PROCESS_DEFINITION_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_INVALID_ACTIVITY_NAME),
            WM_INVALID_ACTIVITY_NAME_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_INVALID_PROCESS_INSTANCE),
            WM_INVALID_PROCESS_INSTANCE_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_INVALID_ACTIVITY_INSTANCE),
            WM_INVALID_ACTIVITY_INSTANCE_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_INVALID_WORKITEM),
            WM_INVALID_WORKITEM_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_INVALID_ATTRIBUTE),
            WM_INVALID_ATTRIBUTE_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_ATTRIBUTE_ASSIGNMENT_FAILED),
            WM_ATTRIBUTE_ASSIGNMENT_FAILED_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_TRANSITION_NOT_ALLOWED),
            WM_TRANSITION_NOT_ALLOWED_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_INVALID_SESSION_HANDLE),
            WM_INVALID_SESSION_HANDLE_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_INVALID_QUERY_HANDLE),
            WM_INVALID_QUERY_HANDLE_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_INVALID_SOURCE_USER),
            WM_INVALID_SOURCE_USER_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_INVALID_TARGET_USER),
            WM_INVALID_TARGET_USER_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_INVALID_FILTER),
            WM_INVALID_FILTER_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_LOCKED),
            WM_LOCKED_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_NOT_LOCKED),
            WM_NOT_LOCKED_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_NO_MORE_DATA),
            WM_NO_MORE_DATA_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_INSUFFICIENT_BUFFER_SIZE),
            WM_INSUFFICIENT_BUFFER_SIZE_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_EXECUTE_FAILED),
            WM_EXECUTE_FAILED_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_IOERROR),
            WM_IOERROR_STR);
        MAIN_CODE_TO_STRING.put(new Integer(WM_GENERAL_ERROR),
            WM_GENERAL_ERROR_STR);
    }

    public WMError(int mainCode) {
        this(mainCode, (short)0);
    }

    public WMError(int mainCode, short subCode) {
        this.mainCode = mainCode;
        this.subCode = subCode;
    }

    public int getMainCode() {
        return mainCode;
    }

    public int getSubCode() {
        return subCode;
    }

    public String toString() {
        return "WMError[mainCode="
            + MAIN_CODE_TO_STRING.get(new Integer(mainCode))
            + ", subCode=" + SUB_CODE_TO_STRING.get(new Integer(subCode))
            + ']';
    }
}