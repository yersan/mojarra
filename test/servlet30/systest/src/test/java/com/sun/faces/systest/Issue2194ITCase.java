/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2011 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.faces.systest;

import junit.framework.Test;
import junit.framework.TestSuite;
import com.sun.faces.htmlunit.HtmlUnitFacesITCase;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.NamingContainer;

public class Issue2194ITCase extends HtmlUnitFacesITCase {

    /**
     * Construct a new instance of this test case.
     *
     * @param name Name of the test case
     */
    public Issue2194ITCase(String name) {
        super(name);
    }


    /**
     * Set up instance variables required by this test case.
     */
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Return the tests included in this test suite.
     */
    public static Test suite() {
        return (new TestSuite(Issue2194ITCase.class));
    }


    /**
     * Tear down instance variables required by this test case.
     */
    public void tearDown() {
        super.tearDown();
    }

    public void testCoreListeners() throws Exception {
        HtmlPage page = getPage("/faces/listeners.xhtml");
        assertTrue(page.asText().contains("RENDER_RESPONSE 6"));
        HtmlForm form = getFormById(page, "form");
        HtmlSubmitInput submit = (HtmlSubmitInput)
            form.getInputByName("button1");
        page = (HtmlPage) submit.click();
        assertTrue(page.asText().contains("button1 was pressed"));
        submit = (HtmlSubmitInput)
            form.getInputByName("button2");
        page = (HtmlPage) submit.click();
        assertTrue(page.asText().contains("button2 was pressed"));
        HtmlInput input = (HtmlInput)
            form.getInputByName("input1");
        page = (HtmlPage) input.setValueAttribute("Foo");
        submit = (HtmlSubmitInput)
            form.getInputByName("submit");
        page = (HtmlPage) submit.click();
        assertTrue(page.asText().contains("input1 value was changed"));
        input = (HtmlInput)
            form.getInputByName("input2");
        page = (HtmlPage) input.setValueAttribute("Bar");
        submit = (HtmlSubmitInput)
            form.getInputByName("submit");
        page = (HtmlPage) submit.click();
        assertTrue(page.asText().contains("input2 value was changed"));
    }
}
        


