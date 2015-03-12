package org.wfmc.impl.base;

/**
 * Created by andras on 3/12/2015.
 */
public enum WMWorkItemAttributeNames {

    TRANSITION_ID("transitionId"),
    TRANSITION_NAME("transitionName"),
    TRANSITION_NEXT_WORK_ITEM_ID("transitionNextWorkItemId");

    private String wmWorkItemAttributeName;

    WMWorkItemAttributeNames(String wmWorkItemAttributeName) {
        this.wmWorkItemAttributeName = wmWorkItemAttributeName;
    }

    public String getWMWorkItemAttributeName() {
        return wmWorkItemAttributeName;
    }


    public static WMWorkItemAttributeNames fromName(String name) {
        if (name == null) {
            name = WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.getWMWorkItemAttributeName();
        }
        for (WMWorkItemAttributeNames c : WMWorkItemAttributeNames.values()) {
            if (c.getWMWorkItemAttributeName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        throw new IllegalArgumentException(name);
    }

}
