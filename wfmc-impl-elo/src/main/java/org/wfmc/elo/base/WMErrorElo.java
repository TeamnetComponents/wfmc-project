package org.wfmc.elo.base;

import org.wfmc.wapi.WMError;

/**
 * Created by andras on 3/5/2015.
 */
public class WMErrorElo extends WMError {

    public static final String ELO_ERROR_FILTER_NOT_SUPPORTED = "ELO_ERROR_FILTER_NOT_SUPPORTED";
    public static final String ELO_SORD_NOT_EXIST = "ELO_SORD_NOT_EXIST";

    public WMErrorElo(int mainCode) {
        super(mainCode);
    }
}
