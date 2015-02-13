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

package org.wfmc.impl.repository;

import org.wfmc.server.core.util.ClassUtils;
import org.xml.sax.EntityResolver;

import java.io.Serializable;
import java.lang.reflect.Constructor;

/**
 * An abstract base class for meta-data implementations.  The meta-data supports
 * a kind of inheritance; repository entries can reference a parent type (by id)
 * via their 'type' attribute.  Property accessor methods automatically return
 * the parent property value if the child value is <code>null</code>.  N.B.
 * subclasses' property getters are expected to conform to this pattern.
 *
 * @author Adrian Price
 */
public abstract class AbstractMetaData implements Serializable {
    private static final long serialVersionUID = 3738728655687826825L;
    /**
     * Enables field inheritance via the <code>type</code> delegate chain.
     */
    public static boolean allowInheritance = true;
    protected static final String[] NO_ARGS_SIG = {};
    protected static final Object[] EMPTY_ARGS = {};
    private transient Constructor _ctor;
    protected AbstractMetaData _type;   // The type of which this is a sub-type.
    protected String _id;
    protected String _displayName;
    protected String _description;
    protected String _docUrl;
    protected String _author;
    protected boolean _threadsafe;

    protected AbstractMetaData() {
    }

    protected AbstractMetaData(String id, String displayName,
        String description, String docUrl, String author, boolean threadsafe) {

        _id = id;
        _displayName = displayName;
        _description = description;
        _docUrl = docUrl;
        _author = author;
        _threadsafe = threadsafe;
    }

    protected final Object createInstance(Object[] ctorArgs)
        throws RepositoryException {

        try {
            // This is all done by reflection to eliminate dependencies of
            // Client API classes upon engine classes.  This method may fail
            // if called in a client environment.
            if (_ctor == null) {
                Class clazz = getClass().getClassLoader()
                    .loadClass(getImplClass());
                Class[] implCtorSig = ClassUtils
                    .classesForNames(getImplCtorSig());
                _ctor = clazz.getConstructor(implCtorSig);
            }
            return _ctor.newInstance(ctorArgs);
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    public abstract Object createInstance(EntityResolver entityResolver)
        throws RepositoryException;


    public Object createInstance(EntityResolver entityResolver, Object args)
        throws RepositoryException {

        // Default implementation ignores the state parameter.
        return createInstance(entityResolver);
    }

    protected abstract String getImplClass();

    protected abstract String[] getImplCtorSig();

    public final String getId() {
        return _id;
    }

    public final void setId(String id) {
        _id = id;
    }

    public final String getDisplayName() {
        return _displayName != null ? _displayName
            : _type == null || !allowInheritance ? null :
            _type.getDisplayName();
    }

    public final void setDisplayName(String displayName) {
        _displayName = displayName;
    }

    public final String getDescription() {
        return _description != null ? _description
            : _type == null || !allowInheritance ? null :
            _type.getDescription();
    }

    public final void setDescription(String description) {
        _description = description;
    }

    public final String getDocUrl() {
        return _docUrl != null ? _docUrl
            : _type == null || !allowInheritance ? null : _type.getDocUrl();
    }

    public final void setDocUrl(String docUrl) {
        _docUrl = docUrl;
    }

    public final String getAuthor() {
        return _author != null ? _author
            : _type == null || !allowInheritance ? null : _type.getAuthor();
    }

    public final void setAuthor(String author) {
        _author = author;
    }

    public final boolean isThreadsafe() {
        return _threadsafe;
    }

    public final boolean getThreadsafe() {
        return _threadsafe;
    }

    public final void setThreadsafe(boolean threadsafe) {
        _threadsafe = threadsafe;
    }

    public final AbstractMetaData getType() {
        return _type;
    }

    public final void setType(AbstractMetaData type) {
        _type = type;
    }

    public String toString() {
        String className = getClass().getName();
        className = className.substring(className.lastIndexOf('.') + 1);
        return className +
            "[id='" + _id +
            "',displayName='" + _displayName +
            "',description='" + _description +
            "',docUrl='" + _docUrl +
            "',author='" + _author +
            "',threadsafe=" + _threadsafe +
            ",type=" + _type +
            ']';
    }
}