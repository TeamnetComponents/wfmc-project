package org.wfmc.impl.utils;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucian.Dragomir on 3/4/2015.
 */
public class Utils {
    private static final String itemSeparator = ";";
    private static final String valueSeparator = "=";
    private static final String itemSeparatorQuoted = java.util.regex.Matcher.quoteReplacement(itemSeparator);
    private static final String valueSeparatorQuoted = java.util.regex.Matcher.quoteReplacement(valueSeparator);

    public static Map<String, String> toMap(String propertyList) {
        Map<String, String> properties = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(propertyList)) {
            String[] items = propertyList.split(itemSeparatorQuoted);
            for (String item : items) {
                if (StringUtils.isNotEmpty(item)) {
                    String[] parts = item.split(valueSeparatorQuoted);
                    if (parts.length == 2) {
                        properties.put(parts[0], parts[1]);
                    } else {
                        throw new RuntimeException("Property splitter failed!");
                    }
                }
            }

        }
        return properties;
    }

    public static String toProperyList(Map<String, String> propertyMap) {
        final StringBuffer stringBuffer = new StringBuffer("");
        for (Map.Entry entry : propertyMap.entrySet()) {
            stringBuffer.append(entry.getKey()).append(valueSeparator).append(entry.getValue()).append(itemSeparator);
        }
        return stringBuffer.toString();
    }


    public static void main(String[] args) {
        String test = "Mask=10;Folder=/23/23/23;FolderHistory=/asd/sdsde/";
        Map map = toMap(test);
        System.out.println(toProperyList(map));

    }

}
