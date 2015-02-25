package org.wfmc.elo.model;

import org.wfmc.elo.base.WMEntityDefault;

/**
 * @author adrian.zamfirescu
 * @since 23/02/2015
 */
public class EloWfmcSord extends WMEntityDefault{

    private String sordId;
    private String sordName;
    private String parentId;  //parintele sordului
    private String maskId;   //formularul de indexare

    private EloWfmcObjKey[] objKeys;   //atributele sord-ului


    public EloWfmcSord(String sordId){
        this.sordId = sordId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getSordName() {
        return sordName;
    }

    public void setSordName(String sordName) {
        this.sordName = sordName;
    }

    public EloWfmcObjKey[] getObjKeys() {
        return objKeys;
    }

    public void setObjKeys(EloWfmcObjKey[] objKeys) {
        this.objKeys = objKeys;
    }
}
