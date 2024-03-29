package cn.kofes.code;

/**
 * <pre>
 * author: Kofe
 * blog  : https://www.kofes.cn
 * time  : 2019/08/31
 * desc  :
 * ==============================================
 * 《剑指Offer——名企面试官精讲典型编程题》代码
 * ==============================================
 * 面试题 3：找出数组中重复的数字
 * 题目：在一个长度为 n 的数组里的所有数字都在 0 到 n-1 的范围内。
 * 数组中某些数字是重复的，但不知道有几个数字重复，也不知道每个数字重复了几次。
 * 请找出数组中任意一个重复的数字。
 * 例如，如果输入长度为 7 的数组 {2, 3, 1, 0, 2, 5, 3}，
 * 那么对应的输出是重复的数字 2 或者 3。
 * ==============================================
 * </pre>
 */
public class DuplicationInArray {
    /**
     * 请找出数组中任意一个重复的数字 (下标筛数法)
     * 下标筛数学法决定了数组中数字的取值范围是 [0, n-1]
     *
     * @param numbers     整型数组
     * @param length      数组的长度
     * @param duplication 返回任意重复的一个数字，赋值给 duplication[0]
     * @return True 输入有效且数组中存在重复的数字；False 其他情况
     */
    public static boolean duplicate(int numbers[], int length, int[] duplication) {

        boolean isDuplicate = false;
        boolean isAllPositive = true;

        if (null != numbers && null != duplication) {
            if (length == numbers.length) {
                // 下标筛数学法决定了数组中数字的取值范围是 [0, n-1]
                for (int number : numbers) {
                    if (number < 0 || number >= numbers.length) {
                        isAllPositive = false;
                        break;
                    }
                }

                if (isAllPositive) {
                    for (int i = 0; i < numbers.length; i++) {
                        if (isDuplicate) break; // 跳出最临近的循环体 -- for

                        while (numbers[i] != i) {
                            if (numbers[i] == numbers[numbers[i]]) {
                                duplication[0] = numbers[i];
                                isDuplicate = true;
                                break; // 跳出最临近的循环体 -- while
                            }
                            swap(numbers, i, numbers[i]);
                        }
                    }
                }
            }
        }

        return isDuplicate;
    }

    /**
     * 请找出数组中任意一个重复的数字 (哈希表筛数法)
     * 数组中数字的取值范围是 [0, Integer.MAX_VALUE]
     *
     * @param numbers     整型数组
     * @param length      数组的长度
     * @param duplication 返回任意重复的一个数字，赋值给 duplication[0]
     * @return True 输入有效且数组中存在重复的数字；False 其他情况
     */
    public static boolean duplicateWithHash(int numbers[], int length, int[] duplication) {

        boolean isDuplicate = false;
        boolean isAllPositive = true;   // 控制全为正整数的开关 (暂时取消)

        // 若 hashSize 不为素数，则自动扩容为素数单位的哈希表
        HashSearch hashSearch = new HashSearch();
        int hashSize = (hashSearch.isPrimeNumber(length)) ?
                length : hashSearch.getProximalPrimeNumber(length);

        if (null != numbers && null != duplication) {

            // 初始化 HashTable
            int[] hashTable = new int[hashSize];
            for (int i = 0; i < hashTable.length; i++) {
                hashTable[i] = -1;
            }

            if (isAllPositive) {
                for (int i = 0, d = 0, dSum = 0, flag = -1, hcKey = 0; i < numbers.length; i++) {

                    if (isDuplicate) break; // 跳出最临近的循环体 -- for

                    // 处理冲突 ( 二次探测再散列 )
                    while (-1 != hashTable[ hcKey = hashSearch.getHashKeyWithMod(numbers[i] + dSum, hashSize) ] && d <= hashSize / 2) {
                        // 发生了冲突，检验冲突位置是否是相同元素
                        if (hashTable[hcKey] == numbers[i]) {
                            duplication[0] = numbers[i];
                            isDuplicate = true;
                            break; // 跳出最临近的循环体 -- while
                        }

                        if (flag < 0) d += 1;
                        dSum = flag * d;
                        flag = -flag;
                    }

                    hashTable[hcKey] = numbers[i];
                }
            }
        }

        return isDuplicate;
    }

    // 交换数组中两位置的数值 (位运算)
    private static void swap(int[] numbers, int i, int j) {
        numbers[i] = numbers[i] ^ numbers[j];
        numbers[j] = numbers[i] ^ numbers[j];
        numbers[i] = numbers[i] ^ numbers[j];
    }

}
