package org.wfmc.elo.model;

import org.wfmc.elo.base.WMEntityDefault;

/**
 * @author adrian.zamfirescu
 * @since 23/02/2015
 */
public class ELOWfMCProcessInstanceAttributes extends WMEntityDefault{

    private String sordId;
    private String maskId;   //formularul de indexare
    private EloWfmcObjKey[] objKeys;   //atributele sord-ului


    public ELOWfMCProcessInstanceAttributes(String sordId){
        this.sordId = sordId;
    }


    public String getMaskId() {
        return maskId;
    }

    public void setMaskId(String maskId) {
        this.maskId = maskId;
    }

    public String getSordId() {
        return sordId;
    }

    public void setSordId(String sordId) {
        this.sordId = sordId;
    }

    public EloWfmcObjKey[] getObjKeys() {
        return objKeys;
    }

    public void setObjKeys(EloWfmcObjKey[] objKeys) {
        this.objKeys = objKeys;
    }
}
