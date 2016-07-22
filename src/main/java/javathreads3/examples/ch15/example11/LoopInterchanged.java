package javathreads3.examples.ch15.example11;

import javathreads3.examples.ch15.PoolLoopHandler;
import javathreads3.examples.ch15.ScaleTester;

public class LoopInterchanged implements ScaleTester {
    private float lookupValues[][];
    private int nRows, nCols, nThreads;
    float sumValue;
    Object lock = new Object();

    private class LoopInterchangedHandler
            extends PoolLoopHandler {
        LoopInterchangedHandler(int nc, int nt) {
            super(0, nc, nt);
        }

        public void loopDoRange(int start, int end) {
            float partialSum = 0;
            for (int i = start; i < end; i++) {
                lookupValues[0][i] = 0;
            }
            float sinValue = 0.0f;
            for (int i = start; i < end; i++) {
                for (int j = 1; j < nRows; j++) {
                    sinValue =
                            (float) Math.sin((i % 360) * Math.PI / 180.0);
                    lookupValues[j][i] = sinValue * (float) i / 180.0f;
                    lookupValues[j][i] +=
                            lookupValues[j - 1][i] * (float) j / 180.0f;
                    partialSum += lookupValues[j][i];
                }
            }
            synchronized (lock) {
                sumValue += partialSum;
            }
        }
    }

    public void init(int nRows, int nCols, int nThreads) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.nThreads = nThreads;
        lookupValues = new float[nRows][];
        for (int j = 0; j < nRows; j++) {
            lookupValues[j] = new float[nCols];
        }
    }

    public float[][] doCalc() {
        LoopInterchangedHandler loop =
                new LoopInterchangedHandler(nCols, nThreads);
        loop.loopProcess();
        return lookupValues;
    }
}
