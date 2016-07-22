package javathreads3.examples.ch15;

public interface ScaleTester {
    void init(int nRows, int nCols, int nThreads);

    float[][] doCalc();
}
