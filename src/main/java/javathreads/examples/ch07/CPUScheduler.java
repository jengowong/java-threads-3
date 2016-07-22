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

package javathreads.examples.ch07;

import javathreads.examples.ch05.*;

public class CPUScheduler extends Thread {
    private CircularList threads;
    private Thread current;
    private int timeslice;
    private static boolean initialized = false;
    private boolean needThreads;

    private static synchronized boolean isInitialized() {
        if (initialized) {
            return true;
        }
        initialized = true;
        return false;
    }

    public CPUScheduler(int t) {
        if (isInitialized())
            throw new SecurityException("Already initialized");
        threads = new CircularList();
        timeslice = t;
        setDaemon(true);
    }

    public synchronized void addThread(Thread t) {
        t.setPriority(2);
        threads.insert(t);
        if (needThreads) {
            needThreads = false;
            notify();
        }
    }

    public void removeThread(Thread t) {
        threads.delete(t);
        synchronized (this) {
            if (t == current)
                current = null;
        }
    }

    public synchronized void run() {
        setPriority(6);
        while (true) {
            current = (Thread) threads.getNext();
            while (current == null) {
                needThreads = true;
                try {
                    wait();
                } catch (Exception e) {
                }
                current = (Thread) threads.getNext();
            }
            try {
                current.setPriority(4);
            } catch (Exception e) {
                removeThread(current);
                continue;
            }
            try {
                wait(timeslice);
            } catch (InterruptedException ie) {
            }
            ;
            if (current != null) {
                try {
                    current.setPriority(2);
                } catch (Exception e) {
                    removeThread(current);
                }
            }
        }
    }
}
