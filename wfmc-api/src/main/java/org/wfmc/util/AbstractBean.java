/*--

 Copyright (C) 2002-2005 Adrian Price.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows
    these conditions in the documentation and/or other materials
    provided with the distribution.

 3. The names "OBE" and "Open Business Engine" must not be used to
 	endorse or promote products derived from this software without prior
 	written permission.  For written permission, please contact
 	adrianprice@sourceforge.net.

 4. Products derived from this software may not be called "OBE" or
 	"Open Business Engine", nor may "OBE" or "Open Business Engine"
 	appear in their name, without prior written permission from
 	Adrian Price (adrianprice@users.sourceforge.net).

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR(S) BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 For more information on OBE, please see
 <http://obe.sourceforge.net/>.

 */

package org.wfmc.util;

import javax.swing.*;
import java.beans.*;
import java.beans.beancontext.BeanContext;
import java.beans.beancontext.BeanContextChild;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * An abstract bean class that supports events and array reallocation.  The
 * methods in this class are not thread-safe and require external
 * synchronization if called in a multi-threaded context.
 *
 * @author Adrian Price
 */
public abstract class AbstractBean implements BeanContextChild, Serializable {
    private static final long serialVersionUID = -1832793880243105246L;

    private BeanContext _beanContext;
    private final IndexedPropertySupport[] _indexedPropertySupport;
    private final Map _indexedPropertySupportByName;
    private transient PropertyChangeSupport _pcs;
    private transient VetoableChangeSupport _vcs;

    protected AbstractBean() {
        _indexedPropertySupport = null;
        _indexedPropertySupportByName = null;
    }

    /**
     * Constructor
     *
     * @param propertyNames
     * @param initalValues
     */
    protected AbstractBean(String[] propertyNames, Object[] initalValues) {
        // Set up support for each indexed property.
        int arrayCount = propertyNames.length;
        _indexedPropertySupport = new IndexedPropertySupport[arrayCount];
        _indexedPropertySupportByName = new HashMap(arrayCount);
        for (int i = 0; i < arrayCount; i++) {
            String propertyName = propertyNames[i];
            IndexedPropertySupport support = new IndexedPropertySupport(
                propertyName, initalValues[i]);
            _indexedPropertySupport[i] = support;
            _indexedPropertySupportByName.put(propertyName, support);
        }
    }

    final class IndexedPropertySupport implements Serializable {
        private static final long serialVersionUID = 44248828826928167L;
        private final String _propertyName;
        private Object _value;
        private transient InternalListModel _model;

        private IndexedPropertySupport(String propertyName, Object value) {
            _propertyName = propertyName;
            _value = value;
        }

        private final class InternalListModel extends AbstractListModel {
            public Object getElementAt(int index) {
                return Array.get(_value, index);
            }

            public int getSize() {
                return Array.getLength(_value);
            }

            private void fireContentsChanged(int index0, int index1) {
                fireContentsChanged(this, index0, index1);
            }

            private void fireIntervalAdded(int index0, int index1) {
                fireIntervalAdded(this, index0, index1);
            }

            private void fireIntervalRemoved(int index0, int index1) {
                fireIntervalRemoved(this, index0, index1);
            }
        }

        public ListModel getListModel() {
            if (_model == null)
                _model = new InternalListModel();
            return _model;
        }

        public Object get() {
            // If it's design time, clone the array; otherwise, no need.
            Object newArray;
            if (_beanContext != null && _beanContext.isDesignTime()) {
                if (_value == null) {
                    newArray = null;
                } else {
                    int length = Array.getLength(_value);
                    newArray = Array.newInstance(
                        _value.getClass().getComponentType(),
                        length);
                    System.arraycopy(_value, 0, newArray, 0, length);
                }
            } else {
                newArray = _value;
            }
            return newArray;
        }

        public Object get(int i) {
            return Array.get(_value, i);
        }

        public void set(Object array) throws PropertyVetoException {
            fireVetoableChange(_propertyName, _value, array);
            _value = array;
            firePropertyChange(_propertyName, _value, array);
            if (_model != null)
                _model.fireContentsChanged(0, Array.getLength(array));
        }

        public void set(int i, Object newValue) throws PropertyVetoException {
            // Check with Vetoable change listeners.
            PropertyChangeEvent evt = null;
            if (hasVetoListeners(_propertyName) ||
                hasChangeListeners(_propertyName)) {

                evt = new IndexedPropertyChangeEvent(AbstractBean.this,
                    _propertyName, Array.get(_value, i), newValue, i);
            }
            fireVetoableChange(evt);

            Array.set(_value, i, newValue);

            // Inform property change and list model listeners.
            firePropertyChange(evt);
            if (_model != null)
                _model.fireContentsChanged(i, i);
        }

        /**
         * Clears the array.
         *
         * @throws java.beans.PropertyVetoException
         */
        public void clear() throws PropertyVetoException {
            Object newArray = Array.newInstance(
                _value.getClass().getComponentType(), 0);
            // Check with Vetoable change listeners.
            PropertyChangeEvent evt = null;
            if (hasVetoListeners(_propertyName) ||
                hasChangeListeners(_propertyName)) {

                evt = new PropertyChangeEvent(AbstractBean.this,
                    _propertyName, _value, newArray);
            }
            fireVetoableChange(evt);

            // Update the property value.
            _value = newArray;

            // Inform property change and list model listeners.
            firePropertyChange(evt);
            if (_model != null)
                _model.fireContentsChanged(0, 0);
        }

        /**
         * Appends an element to an array.
         *
         * @param newValue The element to append (must not be null).
         * @return The reallocated array, of the same component type as the
         *         original, or the type of the added element if the original
         *         was null.
         * @throws IllegalArgumentException if <code>elem</code> is
         *                                  <code>null</code>.
         */
        public Object add(Object newValue) throws PropertyVetoException {
            if (newValue == null)
                throw new IllegalArgumentException();

            // Create the new property value.
            Object newArray;
            int index;
            if (_value == null) {
                index = 0;
                newArray = Array.newInstance(newValue.getClass(), index + 1);
            } else {
                index = Array.getLength(_value);
                newArray = Array.newInstance(
                    _value.getClass().getComponentType(), index + 1);
                System.arraycopy(_value, 0, newArray, 0, index);
            }
            Array.set(newArray, index, newValue);

            // Check with Vetoable change listeners.
            PropertyChangeEvent evt = null;
            if (hasVetoListeners(_propertyName) ||
                hasChangeListeners(_propertyName)) {

                evt = new PropertyChangeEvent(AbstractBean.this,
                    _propertyName, _value, newArray);
            }
            fireVetoableChange(evt);

            // Update the property value.
            _value = newArray;

            // Inform property change and list model listeners.
            firePropertyChange(evt);
            if (_model != null)
                _model.fireIntervalAdded(index, index);

            return newArray;
        }

        /**
         * Removes the first matching array element.  The first element in
         * <code>array</code> to match <code>elem</code> (as determined by
         * <code>Object.equals(Object)</code>), is removed from the array, which
         * is shortened by one.
         *
         * @param elem The element to remove.
         * @return The reallocated array with the specified element removed, or
         *         the original array if it did not contain a matching element.
         * @throws IllegalArgumentException if <code>elem</code> is
         *                                  <code>null</code>.
         */
        public Object remove(Object elem) throws PropertyVetoException {
            if (_value == null)
                return null;
            if (elem == null)
                throw new IllegalArgumentException();

            Object newArray = _value;
            for (int index = 0, length = Array.getLength(_value);
                index < length; index++) {

                if (Array.get(_value, index).equals(elem)) {
                    newArray = Array.newInstance(
                        _value.getClass().getComponentType(), length - 1);
                    System.arraycopy(_value, 0, newArray, 0, index);
                    System.arraycopy(_value, index + 1, newArray, index,
                        length - index - 1);

                    // Check with Vetoable change listeners.
                    PropertyChangeEvent evt = null;
                    if (hasVetoListeners(_propertyName) ||
                        hasChangeListeners(_propertyName)) {

                        evt = new PropertyChangeEvent(AbstractBean.this,
                            _propertyName, _value, newArray);
                    }
                    fireVetoableChange(evt);

                    _value = newArray;

                    // Inform property change and list model listeners.
                    firePropertyChange(evt);
                    if (_model != null)
                        _model.fireIntervalRemoved(index, index);
                    break;
                }
            }
            return newArray;
        }
    }

    private IndexedPropertySupport getIndexedPropertySupport(
        String propertyName) {
        IndexedPropertySupport support = _indexedPropertySupportByName == null
            ? null : (IndexedPropertySupport)
            _indexedPropertySupportByName.get(propertyName);
        if (support == null) {
            throw new IllegalArgumentException("No such indexed property: " +
                propertyName);
        }
        return support;
    }

    protected final Object get(int propertyIndex) {
        return _indexedPropertySupport[propertyIndex].get();
    }

    protected final Object get(int propertyIndex, int index) {
        return _indexedPropertySupport[propertyIndex].get(index);
    }

    protected final void set(int propertyIndex, Object array)
        throws PropertyVetoException {

        _indexedPropertySupport[propertyIndex].set(array);
    }

    protected final void set(int propertyIndex, int index, Object newValue)
        throws PropertyVetoException {

        _indexedPropertySupport[propertyIndex].set(index, newValue);
    }

    protected final Object add(int propertyIndex, Object newValue)
        throws PropertyVetoException {

        return _indexedPropertySupport[propertyIndex].add(newValue);
    }

    protected final Object remove(int propertyIndex, Object elem)
        throws PropertyVetoException {

        return _indexedPropertySupport[propertyIndex].remove(elem);
    }

    public final ListModel getListModel(String propertyName) {
        return getIndexedPropertySupport(propertyName).getListModel();
    }

    public final void clear(String propertyName) throws PropertyVetoException {
        getIndexedPropertySupport(propertyName).clear();
    }

    public final void setBeanContext(BeanContext bc)
        throws PropertyVetoException {
        _beanContext = bc;
    }

    public final BeanContext getBeanContext() {
        return _beanContext;
    }

    public final void addPropertyChangeListener(
        PropertyChangeListener listener) {

        if (_pcs == null)
            _pcs = new PropertyChangeSupport(this);
        _pcs.addPropertyChangeListener(listener);
    }

    public final void addPropertyChangeListener(String name,
        PropertyChangeListener pcl) {

        if (_pcs == null)
            _pcs = new PropertyChangeSupport(this);
        _pcs.addPropertyChangeListener(name, pcl);
    }

    public final void addVetoableChangeListener(
        VetoableChangeListener listener) {

        if (_vcs == null)
            _vcs = new VetoableChangeSupport(this);
        _vcs.addVetoableChangeListener(listener);
    }

    public final void addVetoableChangeListener(String name,
        VetoableChangeListener vcl) {

        if (_vcs == null)
            _vcs = new VetoableChangeSupport(this);
        _vcs.addVetoableChangeListener(name, vcl);
    }

    protected final void firePropertyChange(PropertyChangeEvent evt) {
        if (_pcs != null)
            _pcs.firePropertyChange(evt);
    }

    protected final void firePropertyChange(String propertyName,
        Object oldValue,
        Object newValue) {

        if (_pcs != null)
            _pcs.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected final void firePropertyChange(String propertyName, int oldValue,
        int newValue) {

        if (_pcs != null)
            _pcs.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected final void firePropertyChange(String propertyName,
        boolean oldValue, boolean newValue) {

        if (_pcs != null)
            _pcs.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected final void fireVetoableChange(PropertyChangeEvent evt)
        throws PropertyVetoException {

        if (_vcs != null)
            _vcs.fireVetoableChange(evt);
    }

    protected final void fireVetoableChange(String propertyName,
        Object oldValue, Object newValue) throws PropertyVetoException {

        if (_vcs != null)
            _vcs.fireVetoableChange(propertyName, oldValue, newValue);
    }

    protected final void fireVetoableChange(String propertyName, int oldValue,
        int newValue) throws PropertyVetoException {

        if (_vcs != null)
            _vcs.fireVetoableChange(propertyName, oldValue, newValue);
    }

    protected final void fireVetoableChange(String propertyName,
        boolean oldValue, boolean newValue) throws PropertyVetoException {

        if (_vcs != null)
            _vcs.fireVetoableChange(propertyName, oldValue, newValue);
    }

    protected final boolean hasChangeListeners(String propertyName) {
        return _pcs != null && _pcs.hasListeners(propertyName);
    }

    protected final boolean hasVetoListeners(String propertyName) {
        return _vcs != null && _vcs.hasListeners(propertyName);
    }

    public final void removePropertyChangeListener(
        PropertyChangeListener listener) {

        if (_pcs != null)
            _pcs.removePropertyChangeListener(listener);
    }

    public final void removePropertyChangeListener(String name,
        PropertyChangeListener pcl) {

        if (_pcs != null)
            _pcs.removePropertyChangeListener(name, pcl);
    }

    public final void removeVetoableChangeListener(VetoableChangeListener vcl) {
        if (_vcs != null)
            _vcs.removeVetoableChangeListener(vcl);
    }

    public final void removeVetoableChangeListener(String name,
        VetoableChangeListener vcl) {

        if (_vcs != null)
            _vcs.removeVetoableChangeListener(name, vcl);
    }
}