/**
 *
 *
 * @author Adrian Price
 */
package org.wfmc.util;

import org.wfmc.xpdl.XPDLRuntimeException;

import java.beans.FeatureDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Provides helpers for performing reflection-based introspections.
 *
 * @author Adrian Price
 */
public final class ClassUtils {
    private static final String INVALID_TYPE = " is not a valid Java type";
    private static final Object[][] _primitives = {
        {"boolean", boolean.class, "B"},
        {"byte", byte.class, "Y"},
        {"char", char.class, "C"},
        {"double", double.class, "D"},
        {"float", float.class, "F"},
        {"int", int.class, "I"},
        {"long", long.class, "L"},
        {"short", short.class, "S"},
        {"void", void.class, null}
    };
    private static final Map _primitiveArrayTypeNames;
    private static final Map _primitiveTypes;
    public static final Comparator featureDescriptorComparator;

    static {
        // Must build these maps in the static initializer, so that success does
        // depend upon the relative ordering of the static field declarations.
        _primitiveArrayTypeNames = Collections.unmodifiableMap(new HashMap() {
            {
                for (int i = 0; i < _primitives.length; i++) {
                    Object[] primitive = _primitives[i];
                    put(primitive[0], primitive[2]);
                }
            }
        });
        _primitiveTypes = Collections.unmodifiableMap(new HashMap() {
            {
                for (int i = 0; i < _primitives.length; i++) {
                    Object[] primitive = _primitives[i];
                    put(primitive[0], primitive[1]);
                }
            }
        });
        featureDescriptorComparator = new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((FeatureDescriptor)o1).getName().compareTo(
                    ((FeatureDescriptor)o2).getName());
            }
        };
    }

    /**
     * Returns the class that matches the supplied names.
     * <p/>
     * <em>N.B. The class name must be specified in Java source form, not the
     * JVM runtime form (which uses non-reversible names for primitive types and
     * somewhat obfuscated names for array types).</em>
     *
     * @param className Class name.
     * @return Class for supplied name.
     * @throws ClassNotFoundException
     */
    public static Class classForName(String className)
        throws ClassNotFoundException {

        // If it is an array, determine the number of dimensions.
        int dimensions = 0;
        int index = className.indexOf('[');
        if (index != -1) {
            boolean lbracket = false;
            for (int j = index, n = className.length(); j < n; j++) {
                char ch = className.charAt(j);
                switch (ch) {
                    case '[':
                        lbracket = true;
                        dimensions++;
                        continue;
                    case ']':
                        if (!lbracket) {
                            throw new IllegalArgumentException(className +
                                INVALID_TYPE);
                        }
                        lbracket = false;
                        continue;
                    default:
                        // Ignore whitespace.
                        if (Character.isWhitespace(ch))
                            break;
                        throw new IllegalArgumentException(className +
                            INVALID_TYPE);
                }
            }
            if (lbracket) {
                // Closing rbracket missing.
                throw new IllegalArgumentException(className +
                    INVALID_TYPE);
            }
            className = className.substring(0, index).trim();
        }

        // Does it look like a Java primitive type?
        Class javaClass = null;
        boolean primitive = false;
        if (className.indexOf('.') == -1) {
            javaClass = (Class)_primitiveTypes.get(className);
            if (javaClass != null) {
                primitive = true;
            } else {
                try {
                    // Is it an unqualified java.lang class?
                    String s = "java.lang." + className;
                    javaClass = Class.forName(s);
                    className = s;
                } catch (ClassNotFoundException e) {
                    javaClass = Class.forName(className);
                }
            }
        }

        // If it's an array, add the classname [+L prefix and ; suffix.
        if (dimensions != 0) {
            javaClass = null;
            if (primitive) {
                className = (String)_primitiveArrayTypeNames.get(
                    className);
            }
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < dimensions; j++)
                sb.append('[');
            sb.append('L');
            sb.append(className);
            sb.append(';');
            className = sb.toString();
        }

        if (javaClass == null)
            javaClass = Class.forName(className);

        return javaClass;
    }

    /**
     * Returns an array of classes to match the supplied names.
     * <p/>
     * <em>N.B. Class names must be specified in Java source form, not the JVM
     * runtime form (which uses non-reversible names for primitive types and
     * somewhat obfuscated names for array types).</em>
     *
     * @param classNames Class names.
     * @return Classes for supplied names or <code>null</code> if
     *         <code>classNames</code> is <code>null</code>.
     * @throws ClassNotFoundException
     */
    public static Class[] classesForNames(String[] classNames)
        throws ClassNotFoundException {

        if (classNames == null)
            return null;

        Class[] parmTypes = new Class[classNames.length];
        for (int i = 0; i < parmTypes.length; i++)
            parmTypes[i] = classForName(classNames[i]);
        return parmTypes;
    }

    /**
     * Returns the method that matches a specified signature.
     *
     * @param className The class that defines the method.
     * @param methodSig The method signature in standard Java source form.
     * @return The Method object.
     * @throws ClassNotFoundException If the class could be loaded.
     * @throws NoSuchMethodException  If no matching method was found.
     */
    public static Method findMethod(String className, String methodSig)
        throws ClassNotFoundException, NoSuchMethodException {

        // If no opening parenthesis, methodSig is the method name.
        String methodName;
        StringTokenizer strtok = null;
        if (methodSig.indexOf('(') == -1) {
            methodName = methodSig;
        } else {
            strtok = new StringTokenizer(methodSig);
            methodName = strtok.nextToken("(, )");
        }

        // Extract the method name and parameter types.
        Class[] parmTypes = null;
        if (methodName == null) {
            methodName = "main";
        } else if (strtok != null) {
            int argc = strtok.countTokens();
            String[] classNames = new String[argc];
            for (int i = 0; i < argc; i++)
                classNames[i] = strtok.nextToken();
            parmTypes = classesForNames(classNames);
        }

        // Find the method.
        return findMethod(className, methodName, parmTypes);
    }

    private static Method findMethod(String className, String methodName,
        Class[] parmTypes) throws ClassNotFoundException,
        NoSuchMethodException {

        // Make sure we can load the class.
        Class targetClass = Class.forName(className);

        // If no parameter types were supplied, search for a single method
        // with the required name.
        Method method = null;
        if (parmTypes == null) {
            boolean foundMatch = false;
            Method[] methods = targetClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method meth = methods[i];
                if (meth.getName().equals(methodName)) {
                    if (foundMatch) {
                        throw new IllegalArgumentException("Method '" +
                            methodName + "' is overloaded; please supply " +
                            "the full method signature");
                    }
                    foundMatch = true;
                    method = meth;
                }
            }
            if (method == null)
                throw new NoSuchMethodException(methodName);
        } else {
            // Otherwise, find the specific method and make sure it's public.
            method = targetClass.getMethod(methodName, parmTypes);
            if (!Modifier.isPublic(method.getModifiers())) {
                throw new IllegalArgumentException("Method " +
                    signatureFromMethod(method, false, false, true, true,
                        true, false) + " is not public");
            }
        }
        return method;
    }

    private static Class[] getInterfaceInheritancePath(Class ifClass,
        Class superIfClass) {

        // TODO: Introspect interface inheritance properly!!!
        Class[] classes;
        if (superIfClass == null)
            classes = new Class[]{ifClass};
        else
            classes = new Class[]{ifClass, superIfClass};

        return classes;
    }

    public static String[] getPropertyNames(PropertyDescriptor[] propDescs) {
        String[] propNames = new String[propDescs.length];
        for (int i = 0; i < propDescs.length; i++)
            propNames[i] = propDescs[i].getName();
        return propNames;
    }

    /**
     * Introspects properties of the specified class(es) into a map.
     *
     * @param beanClass
     * @param stopClass
     * @return Array of <code>PropertyDescriptor</code>.
     */
    public static PropertyDescriptor[] introspect(Class beanClass,
        Class stopClass) {

        try {
            PropertyDescriptor[] propDescs;
            if (!beanClass.isInterface() || stopClass == null) {
                propDescs = Introspector.getBeanInfo(beanClass, stopClass)
                    .getPropertyDescriptors();
            } else {
                List props = new ArrayList();
                Class[] classes = getInterfaceInheritancePath(beanClass,
                    stopClass);
                for (int i = 0; i < classes.length; i++) {
                    // Get the properties for the class.
                    // N.B. This has been re-written to introspect both classes
                    // separately because Introspector doesn't recognize a
                    // superinterface as it does a superclass (because
                    // Class.getSuperclass() returns null for an interface).
                    // Thus passing superinterface as stopClass doesn't work.
                    propDescs = Introspector.getBeanInfo(classes[i])
                        .getPropertyDescriptors();

                    // Filter to include only primitive and serializable types.
                    for (int j = 0; j < propDescs.length; j++) {
                        PropertyDescriptor propDesc = propDescs[j];
                        Class propertyType = propDesc.getPropertyType();
                        if (propertyType.isPrimitive() ||
                            Serializable.class.isAssignableFrom(propertyType)) {

                            props.add(propDesc);
                        }
                    }
                }
                propDescs = (PropertyDescriptor[])props.toArray(
                    new PropertyDescriptor[props.size()]);
            }

            // Sort on property name.
            Arrays.sort(propDescs, featureDescriptorComparator);

            return propDescs;
        } catch (IntrospectionException e) {
            throw new XPDLRuntimeException(e);
        }
    }

    /**
     * Converts an array of classes to an array of class names.
     *
     * @param parmTypes Array of classes.
     * @return A corresponding array of class names in Java source format.
     */
    public static String[] namesForClasses(Class[] parmTypes) {
        String[] classNames = new String[parmTypes.length];
        StringBuffer sb = new StringBuffer();
        for (int i = 0, dims = 0; i < parmTypes.length; i++, dims = 0) {
            Class parmType = parmTypes[i];
            while (parmType.isArray()) {
                parmType = parmType.getComponentType();
                dims++;
            }
            String className = parmType.getName();
            if (dims != 0) {
                sb.setLength(0);
                sb.append(className);
                for (int j = dims; j > 0; j--)
                    sb.append("[]");
                className = sb.toString();
            }
            classNames[i] = className;
        }
        return classNames;
    }

    /**
     * Generates a string method description from a Method object.
     *
     * @param method Method object.
     * @return String representation of the method in JVM runtime format.
     */
    public static String signatureFromMethod(Method method) {
        return signatureFromMethod(method, true, true, true, true, true, true);
    }

    /**
     * Generates a string method description from a Method object.
     *
     * @param method                Method object.
     * @param includeModifiers      <code>true</code> to include the modifiers.
     * @param includeReturn         <code>true</code> to include the return type.
     * @param includeClass          <code>true</code> to include the class name.
     * @param includeMethod         <code>true</code> to include the method name.
     * @param includeParameterTypes <code>true</code> to include the parameters.
     * @param includeExceptions     <code>true</code> to include the exceptions.
     * @return String representation of the method in JVM runtime format.
     */
    public static String signatureFromMethod(Method method,
        boolean includeModifiers, boolean includeReturn, boolean includeClass,
        boolean includeMethod, boolean includeParameterTypes,
        boolean includeExceptions) {

        StringBuffer sb = new StringBuffer();
        if (includeModifiers)
            sb.append(Modifier.toString(method.getModifiers())).append(' ');
        if (includeReturn)
            sb.append(method.getReturnType().getName()).append(' ');
        if (includeClass)
            sb.append(method.getDeclaringClass().getName());
        if (includeMethod) {
            if (includeClass)
                sb.append('.');
            sb.append(method.getName());
        }
        if (includeParameterTypes) {
            if (includeMethod)
                sb.append('(');
            Class[] parmTypes = method.getParameterTypes();
            for (int i = 0; i < parmTypes.length; i++) {
                if (i != 0)
                    sb.append(", ");
                sb.append(parmTypes[i].getName());
            }
            if (includeMethod)
                sb.append(')');
        }
        if (includeExceptions) {
            Class[] exceptionTypes = method.getExceptionTypes();
            int n = exceptionTypes.length;
            if (n != 0) {
                sb.append(" throws ");
                for (int i = 0; i < n; i++) {
                    Class exceptionType = exceptionTypes[i];
                    if (i != 0)
                        sb.append(", ");
                    sb.append(exceptionType.getName());
                }
            }
        }
        return sb.toString();
    }

    private ClassUtils() {
    }
}