package org.wfmc.impl.model;

import java.util.Date;
import org.wfmc.wapi.WMFilter;

/**
 * Filter class that supports an ORDER BY clause and the ability to limit the
 * number of rows returned.
 *
 * @author Adrian Price
 */
public class OBEFilter extends WMFilter {
    private final String _orderBy;
    private final int _startRow;
    private final int _maxRows;

    public OBEFilter(String attributeName, int comparison,
        Boolean attributeValue, String orderBy, int startAt, int maxRows) {

        super(attributeName, comparison, attributeValue);
        _orderBy = orderBy;
        _startRow = startAt;
        _maxRows = maxRows;
    }

    public OBEFilter(String attributeName, int comparison,
        boolean attributeValue, String orderBy, int startAt, int maxRows) {

        super(attributeName, comparison, attributeValue);
        _orderBy = orderBy;
        _startRow = startAt;
        _maxRows = maxRows;
    }

    public OBEFilter(String attributeName, int comparison,
        Date attributeValue, String orderBy, int startAt, int maxRows) {

        super(attributeName, comparison, attributeValue);
        _orderBy = orderBy;
        _startRow = startAt;
        _maxRows = maxRows;
    }

    public OBEFilter(String attributeName, int comparison,
        Double attributeValue, String orderBy, int startAt, int maxRows) {

        super(attributeName, comparison, attributeValue);
        _orderBy = orderBy;
        _startRow = startAt;
        _maxRows = maxRows;
    }

    public OBEFilter(String attributeName, int comparison,
        double attributeValue, String orderBy, int startAt, int maxRows) {

        super(attributeName, comparison, attributeValue);
        _orderBy = orderBy;
        _startRow = startAt;
        _maxRows = maxRows;
    }

    public OBEFilter(String attributeName, int comparison,
        Integer attributeValue, String orderBy, int startAt, int maxRows) {

        super(attributeName, comparison, attributeValue);
        _orderBy = orderBy;
        _startRow = startAt;
        _maxRows = maxRows;
    }

    public OBEFilter(String attributeName, int comparison,
        int attributeValue, String orderBy, int startRow, int maxRows) {

        super(attributeName, comparison, attributeValue);
        _orderBy = orderBy;
        _startRow = startRow;
        _maxRows = maxRows;
    }

    public OBEFilter(String attributeName, int comparison,
        String attributeValue, String orderBy, int startRow, int maxRows) {

        super(attributeName, comparison, attributeValue);
        _orderBy = orderBy;
        _startRow = startRow;
        _maxRows = maxRows;
    }

    public OBEFilter(String sql, String orderBy, int startRow, int maxRows) {
        super(sql);
        _orderBy = orderBy;
        _startRow = startRow;
        _maxRows = maxRows;
    }

    public String getOrderBy() {
        return _orderBy;
    }

    public int getStartRow() {
        return _startRow;
    }

    public int getMaxRows() {
        return _maxRows;
    }
}