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

class CircularListNode {
    Object o;
    CircularListNode next;
    CircularListNode prev;
}

public class CircularList {
    private CircularListNode current;

    public synchronized void insert(Object o) {
        CircularListNode tn = new CircularListNode();
        tn.o = o;
        if (current == null) {
            tn.next = tn.prev = tn;
            current = tn;
        } else {    // Add Before Current Node
            tn.next = current;
            tn.prev = current.prev;
            current.prev.next = tn;
            current.prev = tn;
        }
    }

    public synchronized void delete(Object o) {
        CircularListNode p = find(o);
        CircularListNode next = p.next;
        CircularListNode prev = p.prev;
        if (p == p.next) {       // Last Object on the list
            current = null;
            return;
        }
        prev.next = next;
        next.prev = prev;
        if (current == p) current = next;
    }

    private CircularListNode find(Object o) {
        CircularListNode p = current;
        if (p == null)
            throw new IllegalArgumentException();
        do {
            if (p.o == o) return p;
            p = p.next;
        } while (p != current);
        throw new IllegalArgumentException();
    }

    public synchronized Object locate(Object o) {
        CircularListNode p = current;
        do {
            if (p.o.equals(o)) return p.o;
            p = p.next;
        } while (p != current);
        throw new IllegalArgumentException();
    }

    public synchronized Object getNext() {
        if (current == null)
            return null;
        current = current.next;
        return current.o;
    }
}
