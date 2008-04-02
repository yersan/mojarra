/*
 * $Id: EvaluatorBean.java,v 1.7 2006/03/29 22:39:17 rlubke Exp $
 */

/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the License at
 * https://javaserverfaces.dev.java.net/CDDL.html or
 * legal/CDDLv1.0.txt. 
 * See the License for the specific language governing
 * permission and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at legal/CDDLv1.0.txt.    
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * [Name of File] [ver.__] [Date]
 * 
 * Copyright 2005 Sun Microsystems Inc. All Rights Reserved
 */

package com.sun.faces.systest;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.sun.faces.util.MultiThreadTestRunner;

public class EvaluatorBean extends Object {


    protected Object [] threadOutcomes;

    protected String [] expressions;


    protected StringBuffer results = new StringBuffer();

    protected boolean showResults = false;

    protected int numThreads = 1;

    protected int reps = 30000;

    Random random = null;
    private SimpleDateFormat formatter = new SimpleDateFormat("mm:ss:SS");
    private long end = 0;

    private long start = 0;

    // ------------------------------------------------------------ Constructors


    public EvaluatorBean() {

        random = new Random(32714);

    }

    // ---------------------------------------------------------- Public Methods


    public String [] getExpressions() {

        return expressions;

    }


    public void setExpressions(String [] newExpressions) {

        expressions = newExpressions;

    }


    public int getNumThreads() {

        return numThreads;

    }


    public void setNumThreads(int newNumThreads) {

        numThreads = newNumThreads;

    }


    public int getReps() {

        return reps;

    }


    public void setReps(int newReps) {

        reps = newReps;

    }


    public Object [] getThreadOutcomes() {

        return threadOutcomes;

    }


    public void setThreadOutcomes(Object [] newThreadOutcomes) {

        threadOutcomes = newThreadOutcomes;

    }


    public boolean isShowResults() {

        return showResults;

    }


    public void setShowResults(boolean newShowResults) {

        showResults = newShowResults;

    }


    public void doAttributeMapGet(ActionEvent event) throws Exception {

        int i = 0;
        FacesContext context = FacesContext.getCurrentInstance();

        // Create numThreads Threads passing in a new UIComponent with an
        // attribute "foo" value "bar" to the ctor.

        Thread threads[] = new Thread[numThreads];
        UIComponent curInput = null;
        threadOutcomes = new Object[numThreads];
        Runnable runnable = null;

        // initialize threads array
        for (i = 0; i < numThreads; i++) {
            curInput =
                  context.getApplication().createComponent("javax.faces.Input");
            curInput.getAttributes().put("foo", "bar");
            runnable = new AttributeGetRunnable(curInput, getReps(), "foo",
                                                random, i,
                                                true,
                                                System.out,
                                                this);

            threads[i] = new Thread(runnable, "TestThread" + i);
        }

        MultiThreadTestRunner runner =
              new MultiThreadTestRunner(threads, threadOutcomes);

        // if the user wants to show results
        if (showResults) {
            // clear the buffer for the new results
            results = new StringBuffer();
        }
        // evaluate it as a get, reps number of times 
        start = System.currentTimeMillis();
        boolean foundFailedThread = false;
        foundFailedThread = runner.runThreadsAndOutputResults(System.out);
        end = System.currentTimeMillis();

    }


    public void doGet(ActionEvent event) {

        int i = 0;
        FacesContext context = FacesContext.getCurrentInstance();
        String id = event.getComponent().getId();
        // strip off the first character
        id = "i" + id.substring(1);
        // get the expression to evaluate
        UIOutput output = (UIOutput) context.getViewRoot()
              .findComponent("form" + NamingContainer.SEPARATOR_CHAR + id);
        String expression = "#{" + output.getValue() + "}";
        ValueExpression vb =
              context.getApplication().getExpressionFactory().
                    createValueExpression(context.getELContext(),
                                          expression,
                                          Object.class);
        // if the user wants to show results
        if (showResults) {
            // clear the buffer for the new results
            results = new StringBuffer();
        }
        // evaluate it as a get, reps number of times 
        start = System.currentTimeMillis();
        for (i = 0; i < reps; i++) {
            if (showResults) {
                results.append(vb.getValue(context.getELContext()) + "\n");
            } else {
                vb.getValue(context.getELContext());
            }
        }
        end = System.currentTimeMillis();

    }


    public String getElapsedTime() {

        long elapsedSeconds = end - start;
        end = start = 0;
        return formatter.format(new Date(elapsedSeconds));

    }


    public String getResults() {

        return results.toString();

    }


    public void setResults(String newResults) {

        results = new StringBuffer(newResults);

    }

    // Each Thread has an index assigned to it and does reps get()
    // operations on the attribute map for key, pausing for a Random
    // amount of millis between each get.  EvaluatorBean maintains an
    // Object array property threadOutcomes of length numThreads that
    // stores the outcomes of the Threads.  Each Thread writes its
    // outcome to the entry at the Thread's index into the
    // threadOutcomes array.  If an exception is thrown, it is written
    // to the array.  If the Thread executes successfully, a success
    // message is written to the threadOutcomes array.

    public static class AttributeGetRunnable extends Object
          implements Runnable {


        EvaluatorBean host = null;
        Object key = null;
        PrintStream out = null;
        Random random = null;
        UIComponent component = null;
        boolean sleepBetweenGets = true;
        int index = 0;
        int reps = 1;

        // ------------------------------------------------------------ Constructors


        public AttributeGetRunnable(UIComponent component, int reps,
                                    Object key, Random random, int index,
                                    boolean sleepBetweenGets,
                                    PrintStream out,
                                    EvaluatorBean host) {

            this.component = component;
            this.reps = reps;
            this.key = key;
            this.random = random;
            this.index = index;
            this.sleepBetweenGets = sleepBetweenGets;
            this.out = out;
            this.host = host;

        }

        // --------------------------------------------------- Methods From Runnable

        public void run() {

            String name = Thread.currentThread().getName();

            for (int i = 0; i < reps; i++) {
                try {
                    component.getAttributes().get(key);
                }
                catch (Exception e) {
                    host.getThreadOutcomes()[index] = e;
                    System.out.println("index: "
                                       + index
                                       + " exception on get(): "
                                       + e.getMessage());
                    return;
                }

                if (sleepBetweenGets) {
                    try {
                        Thread.sleep(0L, Math.abs(random.nextInt()) % 100);
                    }
                    catch (InterruptedException e) {
                        host.getThreadOutcomes()[index] = e;
                        return;
                    }
                }
            }

            host.getThreadOutcomes()[index] = name + " executed successfully.";

        }

    }

}
