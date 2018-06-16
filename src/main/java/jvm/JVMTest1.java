package jvm;

/**
 * 1. 内存大小-Xmx/-Xms
 *
 * 使用示例: -Xmx20m -Xms5m
 * 说明: 当下Java应用最大可用内存为20M， 最小内存为5M
 *
 * output
 * Xmx=18.0M
 * free mem=4.748664855957031M
 * total mem=5.5M
 * 大家可以发现，这里打印出来的Xmx值和设置的值之间是有差异的，
 * total Memory和最大的内存之间还是存在一定差异的，
 * 就是说JVM一般会尽量保持内存在一个尽可能低的层面，
 * 而非贪婪做法按照最大的内存来进行分配。
 */
public class JVMTest1 {

    public static void main(String[] args) {
        System.out.print("Xmx=");
        System.out.println(Runtime.getRuntime().maxMemory() / 1024.0 / 1024 + "M");

        System.out.print("free mem=");
        System.out.println(Runtime.getRuntime().freeMemory() / 1024.0 / 1024 + "M");

        System.out.print("total mem=");
        System.out.println(Runtime.getRuntime().totalMemory() / 1024.0 / 1024 + "M");

        byte[] b = new byte[8 * 1024 * 1024];
        System.out.println("分配了4M空间给数组");
        b = null;
    }

}
