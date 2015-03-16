package org.wfmc.impl.base;

import org.junit.Test;

import junit.framework.Assert;

/**
 * Created by andras on 3/12/2015.
 */
public class WMWorkItemAttributeNamesTest {


    @Test
    public void test_from_name_with_not_null_value(){
        Assert.assertEquals(WMWorkItemAttributeNames.fromName("transitionNextWorkItemId"), WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID);
    }

    @Test
    public void test_from_name_with_null_value(){
        Assert.assertEquals(WMWorkItemAttributeNames.fromName(null), WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_from_name_with_invalid_value(){
        WMWorkItemAttributeNames.fromName("");
    }
}
