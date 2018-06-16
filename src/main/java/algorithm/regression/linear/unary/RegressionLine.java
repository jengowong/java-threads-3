package algorithm.regression.linear.unary;


import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

/**
 * 回归线实现类，(可实现统计指标的预测)
 */
public class RegressionLine {
    /** sum of x */
    private double sumX;

    /** sum of y */
    private double sumY;

    /** sum of x*x */
    private double sumXX;

    /** sum of x*y */
    private double sumXY;

    /** sum of y*y */
    private double sumYY;

    /** sum of yi-y */
    private double sumDeltaY;

    /** sum of sumDeltaY^2 */
    private double sumDeltaY2;

    /** 误差 */
    private double sse;

    private double sst;

    private double E;

    private String[] xy;

    private List<String> listX;

    private List<String> listY;

    private int XMin, XMax, YMin, YMax;

    /** line coefficient a0 */
    private float a0;

    /** line coefficient a1 */
    private float a1;

    /** number of data points */
    private int pn;

    /** true if coefficients valid */
    private boolean coefsValid;

    /**
     * Constructor.
     */
    public RegressionLine() {
        this.XMax = 0;
        this.YMax = 0;
        this.pn = 0;
        this.xy = new String[2];
        this.listX = Lists.newArrayList();
        this.listY = Lists.newArrayList();
    }

    /**
     * Constructor.
     *
     * @param data the array of data points
     */
    public RegressionLine(DataPoint data[]) {
        this.pn = 0;
        this.xy = new String[2];
        this.listX = Lists.newArrayList();
        this.listY = Lists.newArrayList();
        for (DataPoint dp : data) {
            this.addDataPoint(dp);
        }
    }

    public int getDataPointCount() {
        return this.pn;
    }

    public float getA0() {
        this.validateCoefficients();
        return this.a0;
    }

    public float getA1() {
        this.validateCoefficients();
        return this.a1;
    }

    public double getSumX() {
        return this.sumX;
    }

    public double getSumY() {
        return this.sumY;
    }

    public double getSumXX() {
        return this.sumXX;
    }

    public double getSumXY() {
        return this.sumXY;
    }

    public double getSumYY() {
        return this.sumYY;
    }

    public int getXMin() {
        return this.XMin;
    }

    public int getXMax() {
        return this.XMax;
    }

    public int getYMin() {
        return this.YMin;
    }

    public int getYMax() {
        return this.YMax;
    }

    /**
     * Add a new data point: Update the sums.
     *
     * @param dataPoint the new data point
     */
    public void addDataPoint(DataPoint dataPoint) {
        this.sumX += dataPoint.x;
        this.sumY += dataPoint.y;
        this.sumXX += dataPoint.x * dataPoint.x;
        this.sumXY += dataPoint.x * dataPoint.y;
        this.sumYY += dataPoint.y * dataPoint.y;

        if (dataPoint.x > this.XMax) {
            this.XMax = (int) dataPoint.x;
        }
        if (dataPoint.y > this.YMax) {
            this.YMax = (int) dataPoint.y;
        }

        // 把每个点的具体坐标存入List中，备用
        this.xy[0] = (int) dataPoint.x + "";
        this.xy[1] = (int) dataPoint.y + "";
        if (dataPoint.x != 0 && dataPoint.y != 0) {
            System.out.println(dataPoint);
            try {
                this.listX.add(pn, xy[0]);
                this.listY.add(pn, xy[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ++this.pn;
        this.coefsValid = false;
    }

    /**
     * Return the value of the regression line function at x. (Implementation of Evaluatable.)
     *
     * @param x the value of x
     *
     * @return the value of the function at x
     */
    public float at(int x) {
        if (this.pn < 2) {
            return Float.NaN;
        }

        validateCoefficients();
        return this.a0 + this.a1 * x;
    }

    /**
     * Reset.
     */
    public void reset() {
        pn = 0;
        sumX = sumY = sumXX = sumXY = 0;
        coefsValid = false;
    }

    /**
     * Validate the coefficients. 计算方程系数 y=ax+b 中的a
     */
    private void validateCoefficients() {
        if (coefsValid) {
            return;
        }
        if (pn >= 2) {
            float xBar = (float) sumX / pn;
            float yBar = (float) sumY / pn;

            a1 = (float) ((pn * sumXY - sumX * sumY) / (pn * sumXX - sumX * sumX));
            a0 = (float) (yBar - a1 * xBar);
        } else {
            a0 = a1 = Float.NaN;
        }

        coefsValid = true;
    }

    /**
     * 返回误差
     */
    public double getR() {
        // 遍历这个list并计算分母
        for (int i = 0; i < pn - 1; i++) {
            float Yi = (float) Integer.parseInt(listY.get(i));
            float Y = at(Integer.parseInt(listX.get(i)));
            float deltaY = Yi - Y;
            float deltaY2 = deltaY * deltaY;

            sumDeltaY2 += deltaY2;
        }

        sst = sumYY - (sumY * sumY) / pn;
        E = 1 - sumDeltaY2 / sst;

        return round(E, 4);
    }

    // 用于实现精确的四舍五入
    public double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public float round(float v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

}
