/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2012 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.faces.test.agnostic.context.regular;

import com.sun.faces.context.ExternalContextImpl;
import com.sun.faces.context.FacesContextImpl;
import com.sun.faces.lifecycle.LifecycleImpl;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.junit.Assert.*;

/**
 * The managed bean for the release tests.
 *
 * @author Manfred Riem (manfred.riem@oracle.com)
 */
@ManagedBean(name = "releaseBean")
@RequestScoped
public class ReleaseBean implements Serializable {

    public String getReleaseResult1() {
        try {
            FacesContext currentContext = FacesContext.getCurrentInstance();
            ExternalContextImpl externalContext =
                    new ExternalContextImpl(
                    (ServletContext) currentContext.getExternalContext().getContext(),
                    (HttpServletRequest) currentContext.getExternalContext().getRequest(),
                    (HttpServletResponse) currentContext.getExternalContext().getResponse());
            LifecycleImpl lifecycle = new LifecycleImpl();

            FacesContextImpl context = new FacesContextImpl(externalContext, lifecycle);
            assertTrue(!context.isReleased());
            context.release();
            assertTrue(context.isReleased());


        } catch (NullPointerException exception) {
            exception.printStackTrace();
            fail();
        }

        return "PASSED";
    }

    public String getReleaseResult2() {
        try {
            FacesContext currentContext = FacesContext.getCurrentInstance();
            ExternalContextImpl externalContext =
                    new ExternalContextImpl(
                    (ServletContext) currentContext.getExternalContext().getContext(),
                    (HttpServletRequest) currentContext.getExternalContext().getRequest(),
                    (HttpServletResponse) currentContext.getExternalContext().getResponse());
            LifecycleImpl lifecycle = new LifecycleImpl();

            FacesContextImpl context = new FacesContextImpl(externalContext, lifecycle);

            context.release();
            boolean exceptionThrown = false;
            try {
                context.getViewRoot();
            } catch (IllegalStateException ise) {
                exceptionThrown = true;
            }
            assertTrue(exceptionThrown);

            exceptionThrown = false;
            try {
                context.getResponseStream();
            } catch (IllegalStateException ise) {
                exceptionThrown = true;
            }

            exceptionThrown = false;
            try {
                context.getResponseWriter();
            } catch (IllegalStateException ise) {
                exceptionThrown = true;
            }
            assertTrue(exceptionThrown);

        } catch (NullPointerException exception) {
            exception.printStackTrace();
            fail();
        }

        return "PASSED";
    }
}
