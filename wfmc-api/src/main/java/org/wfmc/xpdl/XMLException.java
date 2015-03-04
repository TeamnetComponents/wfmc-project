package org.wfmc.xpdl;


/**
 * Thrown by XML processing methods for a variety of parser & transformer
 * problems.
 *
 * @author Adrian Price
 */
public class XMLException extends Exception {
    private static final long serialVersionUID = 8239694101248128531L;

    public XMLException(String message) {
        super(message);
    }

    public XMLException(Throwable t) {
        super(t);
    }
}