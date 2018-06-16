package algorithm.search.kmp;

/**
 * 定义接口
 */
interface StringMatcher {
    /**
     * 从原字符串中查找模式字符串的位置, 如果模式字符串存在, 则返回模式字符串第一次出现的位置, 否则返回-1
     *
     * @param source  原字符串
     * @param pattern 模式字符串
     *
     * @return if substring exists, return the first occurrence of pattern substring, return -1 if not.
     */
    int indexOf(String source, String pattern);
}
