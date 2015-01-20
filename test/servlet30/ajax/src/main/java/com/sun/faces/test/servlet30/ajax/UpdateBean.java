/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2013 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.faces.test.servlet30.ajax;

import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.PartialResponseWriter;
import javax.faces.FacesException;

@ManagedBean
@RequestScoped

public class UpdateBean {

    public String updateBodyTag() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        if (ctx.getPartialViewContext().isAjaxRequest()) {
            try {
                extContext.setResponseContentType("text/xml");
                extContext.addResponseHeader("Cache-Control", "no-cache");
                PartialResponseWriter writer =
                    ctx.getPartialViewContext().getPartialResponseWriter();
                writer.startDocument();
                writer.startUpdate("javax.faces.ViewBody");
                writer.startElement("body",null);
                writer.writeAttribute("class","foo","class");
                writer.writeAttribute("title","fooTitle","title");
                writer.writeAttribute("lang","fooLang","lang");
                writer.startElement("span", null);
                writer.writeAttribute("id","status","id");
                writer.endElement("span");
                writer.endElement("body");
                writer.endUpdate();
                writer.startEval();
                writer.write("var body = document.getElementsByTagName('body')[0];");
                writer.write("document.getElementById('status').innerHTML='BODY CLASS:'+body.className+' BODY TITLE:'+body.title+' BODY LANG:'+body.lang");
                writer.endEval();
                writer.endDocument();
                writer.flush();
                ctx.responseComplete();
            } catch (Exception e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    public String updateValueAsAttributeName() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        if (ctx.getPartialViewContext().isAjaxRequest()) {
            try {
                Map attrs = new HashMap();
                attrs.put("value", "");
                extContext.setResponseContentType("text/xml");
                extContext.addResponseHeader("Cache-Control", "no-cache");
                PartialResponseWriter writer =
                    ctx.getPartialViewContext().getPartialResponseWriter();
                writer.startDocument();
                writer.updateAttributes("form1:foo", attrs); 
                writer.startEval();
                writer.write("document.getElementById('form1:foo').value = 'bar';");
                writer.endEval();
                writer.endDocument();
                writer.flush();
                ctx.responseComplete();
            } catch (Exception e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    public String updateDisabledAsAttributeName() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        if (ctx.getPartialViewContext().isAjaxRequest()) {
            try {
                Map attrs = new HashMap();
                attrs.put("disabled", "true");
                extContext.setResponseContentType("text/xml");
                extContext.addResponseHeader("Cache-Control", "no-cache");
                PartialResponseWriter writer =
                    ctx.getPartialViewContext().getPartialResponseWriter();
                writer.startDocument();
                writer.updateAttributes("form1:foo", attrs);
                writer.startEval();
                writer.write("document.getElementById('form1:foo').disabled = false;");
                writer.endEval();
                writer.endDocument();
                writer.flush();
                ctx.responseComplete();
            } catch (Exception e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    public String updateClassAsAttributeName() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        if (ctx.getPartialViewContext().isAjaxRequest()) {
            try {
                Map attrs = new HashMap();
                attrs.put("class", "myclass");
                extContext.setResponseContentType("text/xml");
                extContext.addResponseHeader("Cache-Control", "no-cache");
                PartialResponseWriter writer =
                    ctx.getPartialViewContext().getPartialResponseWriter();
                writer.startDocument();
                writer.updateAttributes("form1:foo", attrs);
                writer.startEval();
                writer.write("document.getElementById('form1:foo').value = document.getElementById('form1:foo').className;");
                writer.endEval();
                writer.endDocument();
                writer.flush();
                ctx.responseComplete();
            } catch (Exception e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    public String updateCheckedAsAttributeName() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        if (ctx.getPartialViewContext().isAjaxRequest()) {
            try {
                Map attrs = new HashMap();
                attrs.put("checked", "true");
                extContext.setResponseContentType("text/xml");
                extContext.addResponseHeader("Cache-Control", "no-cache");
                PartialResponseWriter writer =
                    ctx.getPartialViewContext().getPartialResponseWriter();
                writer.startDocument();
                writer.updateAttributes("form1:foo", attrs);
                writer.endDocument();
                writer.flush();
                ctx.responseComplete();
            } catch (Exception e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    public String updateReadonlyAsAttributeName() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        if (ctx.getPartialViewContext().isAjaxRequest()) {
            try {
                Map attrs = new HashMap();
                attrs.put("readonly", "true");
                extContext.setResponseContentType("text/xml");
                extContext.addResponseHeader("Cache-Control", "no-cache");
                PartialResponseWriter writer =
                    ctx.getPartialViewContext().getPartialResponseWriter();
                writer.startDocument();
                writer.updateAttributes("form1:foo", attrs);
                writer.endDocument();
                writer.flush();
                ctx.responseComplete();
            } catch (Exception e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    public String updateOnAsAttributeName() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        if (ctx.getPartialViewContext().isAjaxRequest()) {
            try {
                Map attrs = new HashMap();
                attrs.put("onclick", "document.getElementById('form1:out').innerHTML='ONCLICK CALLED';return;");
                extContext.setResponseContentType("text/xml");
                extContext.addResponseHeader("Cache-Control", "no-cache");
                PartialResponseWriter writer =
                    ctx.getPartialViewContext().getPartialResponseWriter();
                writer.startDocument();
                writer.updateAttributes("form1:button1", attrs);
                writer.endDocument();
                writer.flush();
                ctx.responseComplete();
            } catch (Exception e) {
                throw new FacesException(e);
            }
        }
        return null;
    }
}
