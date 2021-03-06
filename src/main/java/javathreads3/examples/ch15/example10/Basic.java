package javathreads3.examples.ch15.example10;

import javathreads3.examples.ch15.ScaleTester;

public class Basic implements ScaleTester {
    private float lookupValues[][];
    int nCols, nRows;

    public void init(int nRows, int nCols, int nThreads) {
        this.nCols = nCols;
        this.nRows = nRows;
        lookupValues = new float[nRows][];
        for (int j = 0; j < nRows; j++) {
            lookupValues[j] = new float[nCols];
        }
    }

    public float[][] doCalc() {
        for (int i = 0; i < nCols; i++) {
            lookupValues[0][i] = 0;
        }
        for (int j = 1; j < nRows; j++) {
            for (int i = 0; i < nCols; i++) {
                float sinValue =
                        (float) Math.sin((i % 360) * Math.PI / 180.0);
                lookupValues[j][i] = sinValue * (float) i / 180.0f;
                lookupValues[j][i] +=
                        lookupValues[j - 1][i] * (float) j / 180.0f;
            }
        }
        return lookupValues;
    }
}
