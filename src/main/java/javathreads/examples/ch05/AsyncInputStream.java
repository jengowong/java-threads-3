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

package javathreads.examples.ch05;

import javathreads.examples.ch03.BusyFlag;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AsyncInputStream extends FilterInputStream implements Runnable {
    private Thread runner;        // Async Reader Thread
    private byte result[];        // Buffer
    private int reslen;        // Buffer Length
    private boolean EOF;        // End-of-File Indicator
    private IOException IOError;    // IOExceptions

    BusyFlag lock;            // Data Lock
    CondVar empty, full;        // Signal Variables

    protected AsyncInputStream(InputStream in, int bufsize) {
        super(in);

        lock = new BusyFlag();        // Allocate sync variables
        empty = new CondVar(lock);
        full = new CondVar(lock);

        result = new byte[bufsize];    // Allocate Storage Area
        reslen = 0;            // and initialize variables
        EOF = false;
        IOError = null;
        runner = new Thread(this);     // Start Reader Thread
        runner.start();
    }

    protected AsyncInputStream(InputStream in) {
        this(in, 1024);
    }

    public int read() throws IOException {
        try {
            lock.getBusyFlag();
            while (reslen == 0) {
                try {
                    if (EOF) return (-1);
                    if (IOError != null) throw IOError;
                    empty.cvWait();
                } catch (InterruptedException e) {
                }
            }
            return (int) getChar();
        } finally {
            lock.freeBusyFlag();
        }
    }

    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    public int read(byte b[], int off, int len) throws IOException {
        try {
            lock.getBusyFlag();
            while (reslen == 0) {
                try {
                    if (EOF) return (-1);
                    if (IOError != null) throw IOError;
                    empty.cvWait();
                } catch (InterruptedException e) {
                }
            }

            int sizeread = Math.min(reslen, len);
            byte c[] = getChars(sizeread);
            System.arraycopy(c, 0, b, off, sizeread);
            return (sizeread);
        } finally {
            lock.freeBusyFlag();
        }
    }

    public long skip(long n) throws IOException {
        try {
            lock.getBusyFlag();
            int sizeskip = Math.min(reslen, (int) n);
            if (sizeskip > 0) {
                byte c[] = getChars(sizeskip);
            }
            return ((long) sizeskip);
        } finally {
            lock.freeBusyFlag();
        }
    }

    public int available() throws IOException {
        return reslen;
    }

    public void close() throws IOException {
        try {
            lock.getBusyFlag();
            reslen = 0;        // Clear Buffer
            EOF = true;        // Mark End Of File
            empty.cvBroadcast();    // Alert all Threads
            full.cvBroadcast();
        } finally {
            lock.freeBusyFlag();
        }
    }

    public void mark(int readlimit) {
    }

    public void reset() throws IOException {
    }

    public boolean markSupported() {
        return false;
    }

    public void run() {
        try {
            while (true) {
                int c = in.read();
                try {
                    lock.getBusyFlag();
                    if ((c == -1) || (EOF)) {
                        EOF = true;        // Mark End Of File
                        in.close();        // Close Input Source
                        return;            // End IO Thread
                    } else {
                        putChar((byte) c);  // Store the byte read
                    }
                    if (EOF) {
                        in.close();        // Close Input Source
                        return;            // End IO Thread
                    }
                } finally {
                    lock.freeBusyFlag();
                }
            }

        } catch (IOException e) {
            IOError = e;              //  Store Exception
            return;
        } finally {
            try {
                lock.getBusyFlag();
                empty.cvBroadcast();              //  Alert all Threads
            } finally {
                lock.freeBusyFlag();
            }
        }
    }

    private void putChar(byte c) {
        try {
            lock.getBusyFlag();
            while ((reslen == result.length) && (!EOF)) {
                try {
                    full.cvWait();
                } catch (InterruptedException ie) {
                }
            }
            if (!EOF) {
                result[reslen++] = c;
                empty.cvSignal();
            }
        } finally {
            lock.freeBusyFlag();
        }
    }

    private byte getChar() {
        try {
            lock.getBusyFlag();
            byte c = result[0];
            System.arraycopy(result, 1, result, 0, --reslen);
            full.cvSignal();
            return c;
        } finally {
            lock.freeBusyFlag();
        }
    }

    private byte[] getChars(int chars) {
        try {
            lock.getBusyFlag();
            byte c[] = new byte[chars];
            System.arraycopy(result, 0, c, 0, chars);
            reslen -= chars;
            System.arraycopy(result, chars, result, 0, reslen);
            full.cvSignal();
            return c;
        } finally {
            lock.freeBusyFlag();
        }
    }
}
