package algorithm.bitmap;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link TestBitmap}
 */
@Slf4j
public class TestBitmap {

    public static final int _1MB = 1024 * 1024;
    /** 每个byte记录8bit信息,也就是8个数是否存在于数组中 */
    public static byte[] flags = new byte[512 * _1MB];

    public static void setFlags(int num) {
        //使用每个数的低三位作为byte内的映射
        //例如: 255 = 0b11111111
        //低三位(也就是num & (0b111))为0b111 = 7, 则byte的第7位为1, 表示255已存在
        flags[num >> 3] |= (0b1 << (num & (0b111)));
    }

    public static boolean getFlags(int num) {
        return ((flags[num >> 3] >> (num & (0b111))) & 0b1) == 0b1;
    }

    public static void main(String[] args) {
        log.info("0b10 & 0b1 = {}", 0b10 & 0b1);

        //待判重数据
        int[] array = {255, 1024, 0, 65536, 255};

        int index = 0;
        for (int num : array) {
            if (!getFlags(num)) {
                //未出现的元素
                array[index] = num;
                index = index + 1;
                //设置标志位
                setFlags(num);
                log.info("set {}", num);
            } else {
                log.info("{} already exist", num);
            }
        }
        log.info("{}", array);
    }

}
