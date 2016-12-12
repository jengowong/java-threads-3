package algorithm.backtracking;

import java.util.Arrays;

/**
 * 问题：
 * 从1-9这9个数字里面挑出9个数字来填入下图的9个格子里面，
 * 使得3边之和都相等，
 * 找出所有的结果。
 *
 * []
 * [] []
 * []    []
 * [] [] [] []
 *
 * 分析:
 * 要找出所有的解，需要尝试所有的组合，即在每个格子里面尝试所有的9个数字。
 * 这样就会产生9！次的尝试。
 * 这样穷举的结果集是非常庞大的，需要对结果集进行剪枝，去掉一些不可能的结果。
 * 通过分析我们得知，
 * 三个顶点的数值只可能为1，2，3。
 * 这样的情况下我们可以限定这3个顶点的取值范围。
 * 来进行剪枝。
 *
 * 结论：
 * 在穷举的基础上加上三个限制条件
 * 1：每个节点的值不一样
 * 2：三边之和相等且等于17
 * 3：三个顶点的取值范围为[1，2，3]
 */
public class Nine {

    private int[] pos = new int[9];
    private int count;

    private boolean check(int index, int value) {
        //if(index==0){return true;}
        if (index == 0 && value >= 1 && value <= 3) {
            return true;
        }
        for (int i = 0; i < index; i++) {
            if (pos[i] == value) {
                //value is exists in pos array
                return false;
            }
        }
        switch (index) {
            case 1:
            case 2:
                if ((pos[0] + value) < 17) {
                    return true;
                }
                break;
            case 3:
                if ((pos[0] + pos[1] + value) < 17) {
                    return true;
                }
                break;
            case 4:
                if ((pos[0] + pos[2] + value) < 17) {
                    return true;
                }
                break;
            case 5:
                if (value >= 1 && value <= 3 && (pos[0] + pos[1] + pos[3] + value) == 17) {
                    return true;
                }
                break;
            case 6:
                if ((pos[5] + value) < 17) {
                    return true;
                }
                break;
            case 7:
                if ((pos[5] + pos[6] + value) < 17) {
                    return true;
                }
                break;
            case 8:
                if (value >= 1 && value <= 3 && (pos[0] + pos[2] + pos[4] + value == 17) && (pos[5] + pos[6] + pos[7] + value == 17)) {
                    return true;
                }
                break;
        }
        return false;
    }

    public void print(int index) {
        if (index < 9) {
            for (int i = 1; i <= 9; i++) {
                if (check(index, i)) {
                    pos[index] = i;
                    print(index + 1);
                    if (index == 8) {
                        count++;
                        System.out.println(Arrays.toString(pos));
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Nine n = new Nine();
        n.print(0);
        System.out.println("count=" + n.count);
    }

}