package algorithm.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 源地址哈希（Hash）法
 * <pre/>
 *
 * 源地址哈希的思想是获取客户端访问的IP地址值，通过哈希函数计算得到一个数值，
 * 用该数值对服务器列表的大小进行取模运算，得到的结果便是要访问的服务器的序号。
 *
 * 前两部分和轮询法、随机法一样就不说了，差别在于路由选择部分。
 * 通过客户端的ip也就是remoteIp，取得它的Hash值，
 * 对服务器列表的大小取模，结果便是选用的服务器在服务器列表中的索引值。
 *
 * 优点：保证了相同客户端IP地址将会被哈希到同一台后端服务器，直到后端服务器列表变更。
 * .....根据此特性可以在服务消费者与服务提供者之间建立有状态的session会话。
 *
 * 缺点：除非集群中服务器的非常稳定，基本不会上下线，
 * .....否则一旦有服务器上线、下线，
 * .....那么通过源地址哈希算法路由到的服务器是服务器上线、下线前路由到的服务器的概率非常低，
 * .....如果是session则取不到session，如果是缓存则可能引发“雪崩”。
 * .....如果这么解释不适合明白，可以看我之前的一篇文章MemCache超详细解读，一致性Hash算法部分。
 */
public class Hash {

    public static String getServer() {
        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> serverMap = new HashMap<String, Integer>();
        serverMap.putAll(IpMap.serverWeightMap);

        // 取得Ip地址List
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<String>();
        keyList.addAll(keySet);

        // 在Web应用中可通过HttpServlet的getRemoteIp方法获取
        String remoteIp = "127.0.0.1";
        int hashCode = remoteIp.hashCode();
        int serverListSize = keyList.size();
        int serverPos = hashCode % serverListSize;

        return keyList.get(serverPos);
    }

}
