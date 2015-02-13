package org.wfmc.impl.repository;

import java.io.Serializable;

/**
 * @author Adrian Price
 */
public final class Message implements Serializable {
    private static final long serialVersionUID = 2631157984208820012L;
    public String name;
    public Part[] parts;

    public Message() {
    }

    public Message(String name, Part[] parts) {
        this.name = name;
        this.parts = parts;
    }
}
