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

package com.sun.faces.test.agnostic.application.runtimeConfig;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.PropertyResolver;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.Validator;
import java.util.Iterator;
import java.util.Locale;

import static org.junit.Assert.*;

@ManagedBean
@SessionScoped
public class ApplicationConfigBean {

    private String title = "Test Application Config";
    public String getTitle() {
        return title; 
    }

    public ApplicationConfigBean() {
    }

    private String getUpdateRuntimeComponents() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();

        ActionListener actionListener = null;
        NavigationHandler navHandler = null;
        PropertyResolver propResolver = null;
        VariableResolver varResolver = null;
        ViewHandler viewHandler = null;
        StateManager stateManager = null;

        actionListener = app.getActionListener();
        assertTrue(null != actionListener && actionListener instanceof com.sun.faces.test.agnostic.application.runtimeConfig.TestActionListener);

        navHandler = app.getNavigationHandler();
        assertTrue(null != navHandler && navHandler instanceof com.sun.faces.test.agnostic.application.runtimeConfig.TestNavigationHandler);

        // JSF1.2 BI: application.getPropertyResolver() no longer returns the
        // head of the PropertyResolver. Instead returns the head of the
        // ELResolver stack wrapped in a PropertyResolver.This also applies to
        // VariableResolver
        propResolver = app.getPropertyResolver();
        assertTrue(null != propResolver && propResolver instanceof javax.faces.el.PropertyResolver);

        varResolver = app.getVariableResolver();
        assertTrue(null != varResolver && varResolver instanceof javax.faces.el.VariableResolver);

        viewHandler = app.getViewHandler();
        assertTrue(null != viewHandler && viewHandler instanceof javax.faces.application.ViewHandler);

        stateManager = app.getStateManager();
        assertTrue(null != stateManager && stateManager instanceof javax.faces.application.StateManager);

/*
        if (app.getDefaultRenderKitId().equals("WackyRenderKit")) {
            status += "SUCCESS: Default RenderKitId**";
        } else {
            status += "FAIL: Default RenderKitId:"+app.getDefaultRenderKitId()+"**";
        }
*/

        return "SUCCESS";
    }

    private String status="";

    public String getStatus() {
        return status;
    }
}

