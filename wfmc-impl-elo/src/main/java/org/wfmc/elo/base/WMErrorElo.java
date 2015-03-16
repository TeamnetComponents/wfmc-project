package org.wfmc.elo.base;

import org.wfmc.wapi.WMError;

/**
 * Created by andras on 3/5/2015.
 */
public class WMErrorElo extends WMError {

    public static final String ELO_ERROR_FILTER_NOT_SUPPORTED = "ELO_ERROR_FILTER_NOT_SUPPORTED";
    public static final String ELO_SORD_NOT_EXIST = "ELO_SORD_NOT_EXIST";
    public static final String COULD_NOT_COMPLETE_WORK_ITEM = "COULD_NOT_COMPLETE_WORK_ITEM";
    public static final String COULD_NOT_FIND_WORK_ITEM = "COULD_NOT_FIND_WORK_ITEM";
    public static final String PROCESS_INSTANCE_NOT_FOUND = "PROCESS_INSTANCE_NOT_FOUND";

    public WMErrorElo(int mainCode) {
        super(mainCode);
    }
}
