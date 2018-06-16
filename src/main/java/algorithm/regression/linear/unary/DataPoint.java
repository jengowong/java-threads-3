package algorithm.regression.linear.unary;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 座标点实体类，(可实现统计指标的预测)
 */
public class DataPoint {

    /** the x value */
    public float x;

    /** the y value */
    public float y;

    /**
     * Constructor.
     *
     * @param x the x value
     * @param y the y value
     */
    public DataPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
