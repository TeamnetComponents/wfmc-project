package org.wfmc.elo;

import org.wfmc.wapi2.WMEntity;

/**
 * @author adrian.zamfirescu
 * @since 23/02/2015
 */
public class EloWfmcSord implements WMEntity{

    private String sordId;
    private String sordName;

    public EloWfmcSord(String sordId){
        this.sordId = sordId;
    }

    public String getSordId() {
        return sordId;
    }

    public void setSordId(String sordId) {
        this.sordId = sordId;
    }

    public String getSordName() {
        return sordName;
    }

    public void setSordName(String sordName) {
        this.sordName = sordName;
    }

}
