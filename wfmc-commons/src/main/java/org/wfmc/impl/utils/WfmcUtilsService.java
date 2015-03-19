package org.wfmc.impl.utils;

import org.wfmc.wapi.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucian.Dragomir on 3/4/2015.
 */
public class WfmcUtilsService {

    public List toList(WMIterator wmIterator) {
        List list = new ArrayList<>();
        if (wmIterator != null) {
            while (wmIterator.hasNext()) {
                Object object = wmIterator.next();
                list.add(object);
            }
        }
        return list;
    }

    public Map<String, WMAttribute> toWMAttributeMap(WMAttributeIterator wmAttributeIterator)
        throws WMNoMoreDataException
    {
        Map<String, WMAttribute> wmAttributeMap = new HashMap<String, WMAttribute>();
        if (wmAttributeIterator != null) {
            while (wmAttributeIterator.hasNext()) {
                WMAttribute wmAttribute = wmAttributeIterator.tsNext();
                wmAttributeMap.put(wmAttribute.getName(), wmAttribute);
            }
        }
        return wmAttributeMap;
    }

    public Map<String, Object> toMap(WMAttributeIterator wmAttributeIterator) throws WMNoMoreDataException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (wmAttributeIterator != null) {
            while (wmAttributeIterator.hasNext()) {
                WMAttribute wmAttribute = wmAttributeIterator.tsNext();
                map.put(wmAttribute.getName(), wmAttribute.getValue());
            }
        }
        return map;
    }

    public Map<String, Object> toMap(Map<String, WMAttribute> wmAttributeMap) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Map.Entry entry : wmAttributeMap.entrySet()) {
            map.put((String) entry.getKey(), ((WMAttribute) entry.getValue()).getValue());
        }
        return map;
    }

}
