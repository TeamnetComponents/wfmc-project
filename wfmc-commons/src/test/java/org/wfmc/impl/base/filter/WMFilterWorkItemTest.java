package org.wfmc.impl.base.filter;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wfmc.impl.base.WMParticipantImpl;
import org.wfmc.wapi.WMFilter;
import org.wfmc.wapi.WMParticipant;

/**
 * Created by andras on 3/5/2015.
 */
public class WMFilterWorkItemTest {

    WMFilterWorkItem wmFilterWorkItem;

    @Before
    public void setUp(){
        wmFilterWorkItem = new WMFilterWorkItem();
    }

    @Test
    public void test_add_work_item_participant(){
        WMParticipant wmParticipant = new WMParticipantImpl("me");
        wmFilterWorkItem.addWorkItemParticipant(wmParticipant);
        Assert.assertEquals(wmFilterWorkItem.getWmParticipantList().get(0).getName(),"me");
    }

    @Test
    public void test_add_work_item_name(){
        wmFilterWorkItem.addWorkItemName("me");
        Assert.assertEquals(wmFilterWorkItem.getWorkItemName(),"me");
    }
}
