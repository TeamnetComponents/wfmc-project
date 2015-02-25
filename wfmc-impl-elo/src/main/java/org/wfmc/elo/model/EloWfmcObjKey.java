package org.wfmc.elo.model;

import org.wfmc.elo.base.WMEntityDefault;

/**
 * Created by IntelliJ IDEA.
 * User: valentin.drog
 * Date: 2/24/15
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class EloWfmcObjKey extends WMEntityDefault{

    private String[] value; //valoare atribut
    private int	id ;        //id-ul liniei din seria de atribute
    private String name;    //nume atribut
    private int	objId;      //id-ul ELO object de care apartine atributul

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }
}
