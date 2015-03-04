package org.wfmc.impl.base.filter;

import org.wfmc.wapi.WMFilter;
import org.wfmc.wapi.WMParticipant;
import org.wfmc.wapi.WMWorkItem;

import java.util.List;

/**
 * Created by Lucian.Dragomir on 3/4/2015.
 */
public class WMFilterWorkItem extends WMFilter{

    WMWorkItem wmWorkItem;
    List<WMParticipant> lista;







    public WMFilterWorkItem(Object expression) {
        super(expression);

        //wmWorkItem.getName()

    }



    //                  este builder peste WMFilterWorkItem
   //WMFilter wmFilter=WMFilterBuilder.createFilterWorkItem.addParticipant(parti).addParticipant(...).addName().build();



}
