package org.wfmc.util;

import org.wfmc.xpdl.XPDLRuntimeException;
import org.xml.sax.InputSource;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * Provides common configuration-related functionality.
 *
 * @author Adrian Price
 */
public class CommonConfig {
    private static final String SERVICES_DIR = "META-INF/services/";
    protected static final String CONFIG_FILE = "obe.properties";
    /**
     * Property key for the default client protocol type.
     *
     * @see #getProtocol()
     */
    public static final String PROTOCOL_PROP = "obe.client.protocol";
    /**
     * Property key for the default JAAS principal name.
     *
     * @see #getPrincipal()
     */
    public static final String PRINCIPAL_PROP = "obe.client.principal";
    /**
     * Property key for the default JAAS credentials.
     *
     * @see #getCredentials()
     */
    public static final String CREDENTIALS_PROP = "obe.client.credentials";
    /**
     * Property key for the configuration directory.
     *
     * @see #getConfigDir()
     */
    public static final String CONFIG_DIR_PROP = "obe.config.dir";
    /**
     * Property key for the default date format.
     *
     * @see #getDefaultDateFormat()
     */
    public static final String DATE_FORMAT_PROP = "obe.xpdl.date.format";
    /**
     * Property key for the default duration unit.
     *
     * @see #getDefaultDurationUnit()
     */
    public static final String DURATION_UNIT_PROP = "obe.xpdl.duration.unit";
    protected static final Properties _props = new Properties(
        System.getProperties());
    private static final File _configDir;

    static {
        _configDir = new File(System.getProperty(CONFIG_DIR_PROP, "."));
        InputStream in = openInputStream(CONFIG_FILE);
        if (in != null) {
            try {
                _props.load(in);
            } catch (IOException e) {
                throw new XPDLRuntimeException(e);
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new XPDLRuntimeException(e);
                }
            }
        }
    }

    protected static boolean getBooleanProperty(String propertyName,
        boolean defaultValue) {

        String property = _props.getProperty(propertyName);
        return property == null ? defaultValue
            : Boolean.valueOf(property).booleanValue();
    }

    protected static int getIntProperty(String propertyName, int defaultValue) {
        String property = _props.getProperty(propertyName);
        return property == null ? defaultValue : Integer.parseInt(property);
    }

    protected static String getStringProperty(String propertyName,
        String defaultValue) {

        return _props.getProperty(propertyName, defaultValue);
    }

    /**
     * Returns the configuration directory.  Loose files in this directory
     * override their counterparts in the obeconfig.jar archive.
     * <p/>
     * The setting reflects the string value of the system property
     * <code>obe.config.dir</code>.
     *
     * @return The configuration directory, defaulting to the current directory.
     * @see #CONFIG_DIR_PROP
     */
    public static File getConfigDir() {
        return _configDir;
    }

    /**
     * Returns the default client protocol type.
     * <p/>
     * The setting reflects the string value of the configuration property
     * <code>obe.client.protocol</code>.
     *
     * @return Client protocol type, defaulting to <code>"local"</code>.
     * @see #PROTOCOL_PROP
     */
    public static String getProtocol() {
        return getStringProperty(PROTOCOL_PROP, "local");
    }

    /**
     * Returns the default client principal name.
     * <p/>
     * The setting reflects the string value of the configuration property
     * <code>obe.client.principal</code>.
     *
     * @return Client principal name, defaulting to <code>"system"</code>.
     * @see #PRINCIPAL_PROP
     */
    public static String getPrincipal() {
        return getStringProperty(PRINCIPAL_PROP, "system");
    }

    /**
     * Returns the credentials with which the client should authenticate.
     * <p/>
     * The setting reflects the string value of the configuration property
     * <code>obe.client.credentials</code>.
     *
     * @return Client credentials, defaulting to <code>"password"</code>.
     * @see #CREDENTIALS_PROP
     */
    public static String getCredentials() {
        return getStringProperty(CREDENTIALS_PROP, "password");
    }

    /**
     * Returns the date format to be used when rendering dates.
     * <p/>
     * The setting reflects the value of the configuration property
     * <code>obe.xpdl.date.format</code>.
     *
     * @return Date format, defaulting to <code>yyyy-MM-dd'T'HH:mm:ssZ</code>.
     * @see #DATE_FORMAT_PROP
     */
    public static String getDefaultDateFormat() {
        return getStringProperty(DATE_FORMAT_PROP, "yyyy-MM-dd'T'HH:mm:ssZ");
    }

    /**
     * Returns the default duration unit to use if an XPDL file does not specify
     * a default.
     * <p/>
     * The setting reflects the value of the configuration property
     * <code>obe.xpdl.duration.unit</code>.
     *
     * @return Default duration unit, defaulting to <code>"D"</code> (day).
     * @see #DURATION_UNIT_PROP
     */
    public static String getDefaultDurationUnit() {
        return getStringProperty(DURATION_UNIT_PROP, "D");
    }

    /**
     * Locates a configuration resource.  The resource can either be in
     * the configuration directory, or it can be packaged in obeconfig.jar.
     *
     * @param resource The resource name.
     * @return The URL of the resource, or <code>null</code> if it could not be
     *         located.
     */
    public static URL findResource(String resource) {
        URL url;
        File file = new File(_configDir, resource);
        if (file.exists()) {
            try {
                file = file.getCanonicalFile();
                url = file.toURL();
            } catch (IOException e) {
                throw new XPDLRuntimeException(e);
            }
        } else {
            ClassLoader cl = CommonConfig.class.getClassLoader();
            url = cl.getResource(SERVICES_DIR + resource);
            // Last ditch attempt in case we're running under the debugger.
            if (url == null)
                url = cl.getResource(resource);
        }
        if (url != null) {
            //_logger.info("Located resource at " + url);
            {}
        } else {
            // No configuration - we'll just use the defaults.
            //_logger.info("Resource '" + resource +
            //    "' not found - using defaults");
        }
        return url;
    }

    /**
     * Opens an input stream for the specified resource.  The caller is
     * responsible for closing the input stream after reading from it.  The
     * implementation checks first whether the resource is present as a file in
     * the configuration directory, and if this is not the case it checks for
     * the presence of the resource inside the obeconfig.jar file.
     *
     * @param resource The partially qualified resource name, <em>relative to
     *                 the configuration directory or the root of obeconfig.jar</em>.
     * @return The input stream, or <code>null</code> if the resource could not
     *         be located.
     */
    public static InputStream openInputStream(String resource) {
        InputStream in = null;
        URL url = findResource(resource);
        if (url != null) {
            try {
                in = url.openStream();
            } catch (IOException e) {
                // We'll return null if we can't open a stream.
            }
        }
        return in;
    }

    /**
     * Opens an output stream for the specified resource.  The caller is
     * responsible for closing the output stream after writing to it.  The
     * implementation attempts to open the specified file in the configuration
     * directory (or a subdirectory thereof if the resource name contains a
     * path).
     *
     * @param resource The partially qualified resource name, <em>relative to
     *                 the configuration directory</em>.
     * @return The output stream, or <code>null</code> if the resource could not
     *         be located.
     */
    public static OutputStream openOutputStream(String resource) {
        OutputStream out = null;
        File file = new File(_configDir, resource);
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            //_logger.error("Unable to open file for writing: " + file);
        }
        return out;
    }

    /**
     * Opens a SAX input source for a resource.  The caller is responsible for
     * closing the associated byte stream afterwards.
     *
     * @param resource The resource name.
     * @return The input source, or <code>null</code> if the resource could not
     *         be located.
     */
    public static InputSource openInputSource(String resource) {
        InputSource src = null;
        URL url = findResource(resource);
        if (url != null) {
            try {
                InputStream in = url.openStream();
                src = new InputSource(in);
                src.setSystemId(url.toExternalForm());
            } catch (IOException e) {
                // We'll return null if we can't open a stream.
            }
        }
        return src;
    }

    /**
     * Opens a character stream for a resource.  The caller is responsible for
     * closing the reader afterwards.
     *
     * @param resource The resource name.
     * @return The character stream, or <code>null</code> if the resource could
     *         not be located.
     */
    public static Reader openReader(String resource) {
        Reader reader = null;
        InputStream in = openInputStream(resource);
        if (in != null)
            reader = new InputStreamReader(in);
        return reader;
    }

    protected CommonConfig() {
    }
}