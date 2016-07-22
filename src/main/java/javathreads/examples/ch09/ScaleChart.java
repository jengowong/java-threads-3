/*
 * Copyright (c) 1997-1999 Scott Oaks and Henry Wong. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and
 * without fee is hereby granted.
 *
 * This sample source code is provided for example only,
 * on an unsupported, as-is basis. 
 *
 * AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. AUTHOR SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * THIS SOFTWARE IS NOT DESIGNED OR INTENDED FOR USE OR RESALE AS ON-LINE
 * CONTROL EQUIPMENT IN HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE
 * PERFORMANCE, SUCH AS IN THE OPERATION OF NUCLEAR FACILITIES, AIRCRAFT
 * NAVIGATION OR COMMUNICATION SYSTEMS, AIR TRAFFIC CONTROL, DIRECT LIFE
 * SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE FAILURE OF THE
 * SOFTWARE COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR SEVERE
 * PHYSICAL OR ENVIRONMENTAL DAMAGE ("HIGH RISK ACTIVITIES").  AUTHOR
 * SPECIFICALLY DISCLAIMS ANY EXPRESS OR IMPLIED WARRANTY OF FITNESS FOR
 * HIGH RISK ACTIVITIES.
 */

package javathreads.examples.ch09;

import javathreads.examples.ch07.SimpleScheduler;

import java.util.Enumeration;
import java.util.Vector;

public class ScaleChart implements Runnable {
    protected int numIter = 100;

    // Do large useless calculations in the number of threads
    //	 specified and return the number of seconds used.
    private long doLargeCalculation(int numThreads)
            throws InterruptedException {
        Vector tv = new Vector();
        int priority = Thread.currentThread().getPriority() - 1;

        for (int i = 0; i < numThreads; i++) {
            Thread th = new Thread(this);
            tv.addElement(th);
            th.setPriority(priority);
        }
        long startTime = System.currentTimeMillis();
        Enumeration e = tv.elements();
        while (e.hasMoreElements()) {
            ((Thread) e.nextElement()).start();
        }
        e = tv.elements();
        while (e.hasMoreElements()) {
            ((Thread) e.nextElement()).join();
        }
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    public void run() {
        float sinValue = 0.0f;
        for (int i = 0; i < numIter; i++) {
            for (int j = 0; j < 360 * 100; j++) {
                sinValue = (float) Math.sin((j % 360) * Math.PI / 180.0);
                sinValue *= (float) j / 180.0f;
            }
        }
    }

    // Configure the number of iterations so that the calculation
    //	takes more than one minute.
    private void configure() {
        try {
            while (doLargeCalculation(1) < 60)
                numIter *= 2;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    // Given the number of iterations and the time it took to run those
    //	 iterations, return the number of iterations it could have done
    //	 in a minute.
    private double normalize(int iter, long time) {
        return ((double) iter * 60.0 / (double) time);
    }

    // Generate a chart with the number of threads, the number of
    //	 iterations accomplished in one minute, and how many processors
    //	 that it scaled to.
    public void chart() {
        SimpleScheduler ss = new SimpleScheduler(100);
        ss.start();                                 // Ensure Round Robin
        configure();                                // Ensure Test Length
        try {
            double sIter = normalize(numIter, doLargeCalculation(1));
            System.out.println("\t" + 1 + "\t" + sIter + "\t" + 1.00);
            for (int i = 2; ; i++) {
                double nIter = normalize((numIter * i), doLargeCalculation(i));
                System.out.println("\t" + i + "\t" + nIter + "\t" + (nIter / sIter));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void main(String args[]) {
        ScaleChart sc = new ScaleChart();
        sc.chart();
    }
}
