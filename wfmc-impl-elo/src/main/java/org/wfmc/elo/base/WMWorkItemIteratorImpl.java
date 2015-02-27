package org.wfmc.elo.base;

import org.wfmc.wapi.WMNoMoreDataException;
import org.wfmc.wapi.WMWorkItem;
import org.wfmc.wapi.WMWorkItemIterator;

import java.util.List;

/**
 * Created by Ioan.Ivan on 2/25/2015.
 */

public class WMWorkItemIteratorImpl implements WMWorkItemIterator {

    private List<WMWorkItem> examples;
    private int index;

    public WMWorkItemIteratorImpl(List<WMWorkItem> examples) {
        this.examples = examples;
        index = 0;
    }

    @Override
    public WMWorkItem tsNext() throws WMNoMoreDataException {
        return examples.get(index++);
    }

    @Override
    public int getCount() {
        return examples.size();
    }

    @Override
    public boolean hasNext() {
        return !(examples.size() == index);
    }

    @Override
    public Object next() {
        return examples.get(index++);
    }
}