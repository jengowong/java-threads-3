package javathreads.examples.ch04;/*
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


import java.util.*;
import java.io.*;
import java.net.*;

class StockObservable extends Observable {
	String lastTick;

	void setTick(String s) {
		lastTick = s;
		setChanged();
		notifyObservers();
	}
}

public class StockHandler extends Thread {
	private BufferedReader br;
	private InputStream is;
	private Socket sock;
	private StockObservable stock;
	private boolean done = false;
	private Object lock = new Object();

	class StockHandlerThread extends Thread {
		public void run() {
			String s;
			try {
				while ((s = br.readLine()) != null)
					stock.setTick(s);
			} catch (IOException ioe) {}
			done = true;
			synchronized(lock) {
				lock.notify();
			}
 		}
	}

	public StockHandler(StockObservable o, String host, int port)
					throws IOException, UnknownHostException {
		sock = new Socket(host, port);
		is = sock.getInputStream();
		stock = o;
	}

	public void run() {
		br = new BufferedReader(new InputStreamReader(is));
		Thread t = new StockHandlerThread();
		t.start();
		synchronized(lock) {
			while (!done) {
				try {
					lock.wait(Integer.MAX_VALUE);
				} catch (InterruptedException ie) {
					done = true;
					try {
						t.interrupt();
						is.close();
						sock.close();
					} catch (IOException ioe) {}
				}
			}
		}
	}
}
