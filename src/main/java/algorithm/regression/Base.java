package algorithm.regression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base {
    private static final Logger LOG = LoggerFactory.getLogger(Base.class);

    public Base() {
    }

    /**
     * Get the max value for a non-multidimensional array
     *
     * @param sample Non-multidimensional array
     *
     * @return Max value
     */
    public static double max(double[] sample) {
        double result = 0;
        for (double v : sample) {
            if (v > result) {
                result = v;
            }
        }
        return result;
    }

    /**
     * Get the min value for a non-multidimensional array
     *
     * @param sample Non-multidimensional array
     *
     * @return Min value
     */
    public static double min(double[] sample) {
        double result = 0;
        for (double v : sample) {
            if (v < result) {
                result = v;
            }
        }
        return result;
    }

    /**
     * Get the average value for a non-multidimensional array
     *
     * @param sample Non-multidimensional array
     *
     * @return Average value
     */
    public static double avg(double[] sample) {
        double result = 0;
        for (double v : sample) {
            result += v;
        }
        return result / sample.length;
    }

    /**
     * Get the variance for a non-multidimensional array
     *
     * @param sample 样本数组
     *
     * @return 方差，如果样本为空返回-1
     */
    public static double var(double[] sample) {
        double result = -1;
        if (sample.length > 0) {
            result = 0;
            double avg = avg(sample);
            for (double v : sample) {
                result += (v - avg) * (v - avg);
            }
            result /= sample.length;
        }
        return result;
    }

    /**
     * Get the standard variance for a non-multidimensional array
     *
     * @param sample 样本数组
     *
     * @return 标准方差(方差开方)，如果样本为空返回-1
     */
    public static double stdVar(double[] sample) {
        double result = -1;
        if (sample.length > 0) {
            result = var(sample);
            result = Math.sqrt(result);
        }
        return result;
    }

    /**
     * Get the max distance for a non-multidimensional array
     *
     * @param sample 样本数组
     *
     * @return sample[]的最大值-最小值，如果样本为空返回-1
     */
    public static double maxDis(double[] sample) {
        double result = -1;
        if (sample.length > 0) {
            result = max(sample) - min(sample);
        }
        return result;
    }

    /**
     * Get the average warp for a non-multidimensional array
     *
     * @param sample 样本数组
     *
     * @return sample[]的每一个元素-平均值的绝对值的平均值，如果样本为空返回-1
     */
    public static double medDev(double[] sample) {
        double result = -1;
        if (sample.length > 0) {
            result = 0;
            double avg = avg(sample);
            for (double v : sample) {
                result += Math.abs(v - avg);
            }
            result /= sample.length;
        }
        return result;
    }

    /**
     * Get the inside multiplication of two array
     *
     * @param sample1 数组1
     * @param sample2 数组2
     *
     * @return Sum the value of two array's corresponding element production [对应元素乘积的和]
     */
    public static double inMul(double[] sample1, double[] sample2) {
        double result = 0.0d;
        if (sample1.length != sample2.length) {
            LOG.error("two vectors do not at the same dimension, please check it");
        } else {
            for (int i = 0; i < sample1.length; i++) {
                result += sample1[i] * sample2[i];
            }
        }
        return result;
    }

    /**
     * Get the opposite production of two array to comprise a new array
     *
     * @param sample1 数组1
     * @param sample2 数组2
     *
     * @return 形成新的向量，元素为对应元素的乘积
     */
    public static double[] vecMul(double[] sample1, double[] sample2) {
        double[] result = new double[sample1.length];
        if (sample1.length != sample2.length) {
            LOG.error("two vectors do not at the same dimension, please check it");
        } else {
            for (int i = 0; i < sample1.length; i++) {
                result[i] = sample1[i] * sample2[i];
            }
        }
        return result;
    }

    /**
     * Sort the element of the array into a from great to little order
     *
     * @param sample 样本数组
     */
    public static void descOrder(double[] sample) {
        int n = sample.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (sample[i] < sample[j]) {
                    double temp = sample[i];
                    sample[i] = sample[j];
                    sample[j] = temp;
                }
            }
        }
    }

    /*
    *Desc : sort the element of the array into a from  little to great order
    */
    public static void ascOrder(double[] sample) {
        int n = sample.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (sample[i] > sample[j]) {
                    double temp = sample[i];
                    sample[i] = sample[j];
                    sample[j] = temp;
                }
            }
        }
    }

    /*
    *Desc: get two point distance of standard (Euclid distance)
    *Inpute:
    *     @param  double[] Pfrom
    *     @param  double[] Pto
    * Outpute:
    *     @param  double res
    * Logic:
    */
    public static double eudis(double[] Pfrom, double[] Pto) {
        int Lfrom = Pfrom.length;
        int Lto = Pto.length;
        if (Lfrom != Lto) {
            return -1;
        }
        double res = 0;
        for (int i = 0; i < Lfrom; i++) {
            double temp = Pfrom[i] - Pto[i];
            temp *= temp;
            res += temp;
        }
        return Math.sqrt(res);
    }

    /*
    *Desc : get the min value of two array's corresponding element's distance
    *Inpute:
    *    @param  double[] Pfrom
    *    @parma  double[] Pto
    *Outpute:
    *    @return double res
    *Logic:
    */
    public static double mindis(double[] Pfrom, double[] Pto) {
        int Lfrom = Pfrom.length;
        int Lto = Pto.length;
        if (Lfrom != Lto) {
            return -1;
        }
        double res = Math.abs(Pfrom[0] - Pto[0]);
        for (int i = 1; i < Lfrom; i++) {
            double temp = Math.abs(Pfrom[i] - Pto[i]);
            if (temp < res) {
                res = temp;
            }
        }
        return res;
    }

    /*
     *Desc : get the max value of two array's corresponding element's distance
     *Inpute:
     *   @param  double[] Pfrom
     *   @parma  double[] Pto
     *Outpute:
     *   @return double res
     *Logic:
     */
    public static double maxdis(double[] Pfrom, double[] Pto) {
        int Lfrom = Pfrom.length;
        int Lto = Pto.length;
        if (Lfrom != Lto) {
            return -1;
        }
        double res = Math.abs(Pfrom[0] - Pto[0]);
        for (int i = 1; i < Lfrom; i++) {
            double temp = Math.abs(Pfrom[i] - Pto[i]);
            if (temp > res) {
                res = temp;
            }
        }
        return res;
    }


    /*
     *Desc : get the total sum value of two array's corresponding element's distance
     *Inpute:
     *   @param  double[] Pfrom
     *   @parma  double[] Pto
     *Outpute:
     *   @return double res
     *Logic:
     */
    public static double sumdis(double[] Pfrom, double[] Pto) {
        int Lfrom = Pfrom.length;
        int Lto = Pto.length;
        if (Lfrom != Lto) {
            return -1;
        }
        double res = Math.abs(Pfrom[0] - Pto[0]);
        for (int i = 1; i < Lfrom; i++) {
            double temp = Math.abs(Pfrom[i] - Pto[i]);
            res += temp;
        }
        return res;
    }

    /*
     *Desc : get the averagedistance of two array's corresponding element's distance
     *Inpute:
     *   @param  double[] Pfrom
     *   @parma  double[] Pto
     *Outpute:
     *   @return double res
     *Logic:
     */
    public static double dimaddavg(double[] Pfrom, double[] Pto) {
        int Lfrom = Pfrom.length;
        int Lto = Pto.length;
        if (Lfrom != Lto) {
            return -1;
        }
        double res = Math.abs(Pfrom[0] - Pto[0]);
        for (int i = 1; i < Lfrom; i++) {
            double temp = Math.abs(Pfrom[i] - Pto[i]);
            res += temp;
        }
        return res / Lfrom;
    }

    /*
     *Desc : get the diemnsional id  of two array's corresponding element's distance
     *       who has tha max value
     *Inpute:
     *   @param  double[] Pfrom
     *   @parma  double[] Pto
     *Outpute:
     *   @return Int  id
     *Logic:
     */
    public static int maxerrordim(double[] Pfrom, double[] Pto) {
        int Lfrom = Pfrom.length;
        int Lto = Pto.length;
        if (Lfrom != Lto) {
            return -1;
        }
        double maxerror = Math.abs(Pfrom[0] - Pto[0]);
        int res = 0;
        for (int i = 1; i < Lfrom; i++) {
            double temp = Math.abs(Pfrom[i] - Pto[i]);
            if (temp > maxerror) {
                res = i;
                maxerror = temp;
            }
        }
        return res;
    }

    @Deprecated
    public static void main(String[] args) {
        double[] sample = new double[]{1, 2, 3, 4, 5, 6};
        LOG.info("no order: {}", sample);
        descOrder(sample);
        LOG.info("desc order: {}", sample);
        ascOrder(sample);
        LOG.info("asc order: {}", sample);
    }

}