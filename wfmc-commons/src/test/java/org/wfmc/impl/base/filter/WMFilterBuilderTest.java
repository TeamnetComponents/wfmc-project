package org.wfmc.impl.base.filter;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wfmc.wapi.WMFilter;

/**
 * Created by andras on 3/5/2015.
 */
public class WMFilterBuilderTest {


    @Test
    public void test_build_with_2_participants(){
        String firstParticipant = "firstParticipant";
        String secondParticipant = "secondParticipant";
        WMFilter wmFilter = WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipant(firstParticipant).addWorkItemParticipant(secondParticipant);

        Assert.assertEquals(((WMFilterWorkItem)wmFilter).getWmParticipantList().size(),2);
    }


}
