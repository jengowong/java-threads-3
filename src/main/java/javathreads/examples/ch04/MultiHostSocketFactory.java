/*
 *
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

package javathreads.examples.ch04;

import java.io.IOException;
import java.net.Socket;

public class MultiHostSocketFactory {
    private Socket sock;
    private Thread target;

    class ConnectThread extends Thread {
        String host;
        int port;
        MultiHostSocketFactory parent;

        ConnectThread(String host, int port, MultiHostSocketFactory
                parent) {
            this.host = host;
            this.port = port;
            this.parent = parent;
        }

        public void run() {
            try {
                Socket tempSock = new Socket(host, port);
                synchronized (parent) {
                    if (parent.target != this)
                        return;
                    parent.sock = tempSock;
                    parent.notify();
                }
            } catch (IOException ioe) {
                synchronized (parent) {
                    parent.notify();
                }
            }
        }
    }

    public Socket makeSocket(String hosts[], int port) {
        int i;
        sock = null;
        synchronized (this) {
            for (i = 0; i < hosts.length && sock == null; i++) {
                ConnectThread th =
                        new ConnectThread(hosts[i],
                                port, this);
                target = th;
                th.start();
                try {
                    wait(30 * 1000);
                } catch (InterruptedException ie) {
                }
                if (sock == null)
                    th.interrupt();
            }
        }
        return sock;
    }

    public static void main(String args[]) {
        System.out.println("Got " +
                new MultiHostSocketFactory().makeSocket(args, 7));
    }
}
