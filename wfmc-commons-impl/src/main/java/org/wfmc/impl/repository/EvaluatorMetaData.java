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

/**
 * Describes a user-defined evaluator call.  Different evaluator types extend
 * this class with the additional meta data required to instantiate the
 * evaluator implementation itself.
 *
 * @author Adrian Price
 */
public abstract class EvaluatorMetaData extends AbstractMetaData {
    private static final long serialVersionUID = -429157659267364196L;

    private String _grammar;

    // TODO: figure out how to handle evaluator signature discovery & meta-data.

    protected EvaluatorMetaData() {
    }

    /**
     * Constructs evaluator meta-data.
     *
     * @param id          The evaluator ID.
     * @param displayName The display name.
     * @param description Textual description of this evaluator.
     * @param docUrl      URL for documentation.
     * @param author      Author's name.
     * @param threadsafe
     * @param grammar     Url of specification for script language grammar.
     */
    protected EvaluatorMetaData(String id, String displayName,
        String description, String docUrl, String author, boolean threadsafe,
        String grammar) {

        super(id, displayName, description, docUrl, author, threadsafe);
        _grammar = grammar;
    }

    public String getGrammar() {
        return _grammar != null ? _grammar
            : _type == null || !allowInheritance ? null :
            ((EvaluatorMetaData)_type).getGrammar();
    }

    public void setGrammar(String grammar) {
        _grammar = grammar;
    }

    public String toString() {
        String className = getClass().getName();
        className = className.substring(className.lastIndexOf('.') + 1);
        return className + "[_id='" + _id + '\'' +
            ",_displayName='" + _displayName + '\'' +
            ",_description='" + _description + '\'' +
            ",_docUrl='" + _docUrl + '\'' +
            ",_author='" + _author + '\'' +
            ",_grammar='" + _grammar + '\'' +
            ",_type=" + _type +
            ']';
    }
}