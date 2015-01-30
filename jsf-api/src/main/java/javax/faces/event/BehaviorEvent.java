/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2014 Oracle and/or its affiliates. All rights reserved.
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

package javax.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.context.FacesContext;

/**
 * <p><strong class="changed_added_2_0 changed_modified_2_3">BehaviorEvent</strong> 
 * is the event that can be generated from component 
 * {@link javax.faces.component.behavior.Behavior}.</p>
 *
 * @since 2.0
 */
public abstract class BehaviorEvent extends FacesEvent {

    private static final long serialVersionUID = 6516644738910462065L;
    private final Behavior behavior;

    /**
     * <p class="changed_added_2_0 changed_removed_2_3">Construct a new event object 
     * from the specified source component and <code>behavior</code>.</p>
     *
     * @param component Source {@link UIComponent} for this event
     * @param behavior {@link Behavior} that sent this event
     * @throws IllegalArgumentException if <code>component</code> or 
     * <code>behavior</code> is <code>null</code>
     * @since 2.0
     */
    public BehaviorEvent(UIComponent component, Behavior behavior) {
        super(component);

        if (null == behavior) {
            throw new IllegalArgumentException("Behavior agrument cannot be null");
        }
        
        this.behavior = behavior;
    }

    /**
     * <p class="changed_added_2_3">Construct a new event object 
     * from the Faces context, specified source component and behavior.</p>
     *
     * @param facesContext the Faces context.
     * @param component Source {@link UIComponent} for this event
     * @param behavior {@link Behavior} that sent this event
     * @throws IllegalArgumentException if <code>component</code> or 
     * <code>behavior</code> is <code>null</code>
     * @since 2.3
     */
    public BehaviorEvent(FacesContext facesContext, UIComponent component, Behavior behavior) {
        super(facesContext, component);

        if (null == behavior) {
            throw new IllegalArgumentException("Behavior agrument cannot be null");
        }
        
        this.behavior = behavior;
    }
    
    /**
     * <p class="changed_added_2_0">Return the source {@link Behavior} 
     * that sent this event.
     *
     * @since 2.0
     */
    public Behavior getBehavior() {
        return behavior;
    }
}
