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

package javathreads.examples.ch09.scale;

public class ReductionInner implements ScaleTester {
    private float lookupValues[][];
    private int j;

    private int nThreads;
    private int nRows;
    private int nCols;

    float sumValue;
    Object lock = new Object();

    private class ReductionInnerHandler extends PoolLoopHandler {
        ReductionInnerHandler(int nc, int nt) {
            super(1, nc, nt);
        }

        public void loopDoRange(int start, int end) {
            float sinValue = 0.0f;
            float partialSum = 0;
            for (int i = start; i < end; i++) {
                sinValue = (float) Math.sin((i % 360) * Math.PI / 180.0);
                lookupValues[j][i] = sinValue * (float) i / 180.0f;
                lookupValues[j][i] += lookupValues[j - 1][i] * (float) j / 180.0f;
                partialSum += lookupValues[j][i];
            }
            synchronized (lock) {
                sumValue += partialSum;
            }
        }
    }

    public void init(int nRows, int nCols, int nThreads) {
        this.nThreads = nThreads;
        this.nRows = nRows;
        this.nCols = nCols;
        lookupValues = new float[nRows][];
        for (int j = 0; j < nRows; j++) {
            lookupValues[j] = new float[nCols];
        }
    }

    public float[][] doCalc() {
        for (int i = 0; i < nCols; i++) {
            lookupValues[0][i] = 0;
        }
        ReductionInnerHandler loop = new ReductionInnerHandler(nCols, nThreads);
        // Use global j
        for (j = 1; j < nRows; j++) {
            loop.loopProcess();
        }
        return lookupValues;
    }
}
