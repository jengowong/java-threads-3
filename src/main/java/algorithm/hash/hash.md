#常见 Hash 算法的原理

散列表，它是基于高速存取的角度设计的，也是一种典型的“空间换时间”的做法。
顾名思义，该数据结构能够理解为一个线性表，可是当中的元素不是紧密排列的，而是可能存在空隙。

散列表（Hash table，也叫哈希表），是依据“关键码”“值”(Key value)而直接进行訪问的数据结构。
也就是说，它通过把关键码值映射到表中一个位置来訪问记录，以加快查找的速度。
这个映射函数叫做*散列函数*，存放记录的数组叫做散列表。

比方我们存储70个元素，但我们可能为这70个元素申请了100个元素的空间。
70/100=0.7，这个数字称为*负载因子*。
我们之所以这样做，也是为了“高速存取”的目的。
我们基于一种结果尽可能随机平均分布的固定函数H为每一个元素安排存储位置，
这样就能够避免遍历性质的线性搜索，以达到高速存取。
可是因为此随机性，也必定导致一个问题就是*冲突*。

所谓冲突，即两个元素通过散列函数H得到的地址同样，那么这两个元素称为*“同义词”*。
这类似于70个人去一个有100个椅子的饭店吃饭。
散列函数的计算结果是一个存储单位地址，每一个存储单位称为*“桶”*。
设一个散列表有m个桶，则散列函数的值域应为[0,m-1]。

解决冲突是一个复杂问题。
冲突主要取决于：
(1)散列函数，一个好的散列函数的值应尽可能平均分布。
(2)处理冲突方法。
(3)负载因子的大小。太大不一定就好，并且浪费空间严重，负载因子和散列函数是联动的。

解决冲突的办法：
(1)线性探查法：冲突后，线性向前试探，找到近期的一个空位置。
             缺点是会出现堆积现象。存取时，可能不是同义词的词也位于探查序列，影响效率。
(2)双散列函数法：在位置d冲突后，
              再次使用还有一个散列函数产生一个与散列表桶容量m*互质*的数c，
              依次试探(d+n*c)%m，使探查序列跳跃式分布。



#经常使用的构造散列函数的方法

散列函数能使对一个数据序列的訪问过程更加迅速有效，通过散列函数，数据元素将被更快地定位。

## 1.直接寻址法：
取keyword或keyword的某个线性函数值为散列地址。
即 H(key)=key 或 H(key) = a•key + b，当中a和b为常数（这样的散列函数叫做自身函数）

## 2.数字分析法：
分析一组数据，
比方一组员工的出生年月日，
这时我们发现出生年月日的前几位数字大体同样，
这种话，出现冲突的几率就会非常大，
可是我们发现年月日的后几位表示月份和详细日期的数字区别非常大，
假设用后面的数字来构成散列地址，则冲突的几率会明显减少。
因此数字分析法就是找出数字的规律，尽可能利用这些数据来构造冲突几率较低的散列地址。

## 3.平方取中法：
取keyword平方后的中间几位作为散列地址。

## 4.折叠法：
将keyword切割成位数同样的几部分，最后一部分位数能够不同，然后取这几部分的叠加和（去除进位）作为散列地址。

## 5.随机数法：
选择一随机函数，取keyword的随机值作为散列地址，通经常使用于keyword长度不同的场合。

## 6.除留余数法：
取keyword被某个不大于散列表表长m的数p除后所得的余数为散列地址。
即 H(key) = key MOD p, p<=m。
不仅能够对keyword直接取模，也可在折叠、平方取中等运算之后取模。
对p的选择非常重要，一般取*素数或m*，
若p选的不好，easy产生同义词。



#查找的性能分析

散列表的查找过程基本上和造表过程同样。
一些关键码可通过散列函数转换的地址直接找到，
还有一些关键码在散列函数得到的地址上产生了冲突，须要按处理冲突的方法进行查找。
在介绍的三种处理冲突的方法中，产生冲突后的查找仍然是给定值与关键码进行比較的过程。
所以，对散列表查找效率的量度，依旧用*平均查找长度*来衡量。

查找过程中，关键码的比較次数，取决于产生冲突的多少，
产生的冲突少，查找效率就高，产生的冲突多，查找效率就低。
因此，影响产生冲突多少的因素，也就是影响查找效率的因素。
影响产生冲突多少有下面三个因素：
(1)散列函数是否均匀；
(2)处理冲突的方法；
(3)散列表的装填因子。

散列表的装填因子定义为：α = 填入表中的元素个数 / 散列表的长度
α 是散列表装满程度的标志因子。
因为表长是定值，α 与“填入表中的元素个数”成正比，
所以，α 越大，填入表中的元素较多，产生冲突的可能性就越大；
α 越小，填入表中的元素较少，产生冲突的可能性就越小。

实际上，散列表的平均查找长度是装填因子α的函数，仅仅是不同处理冲突的方法有不同的函数。

了解了hash基本定义，
就不能不提到一些著名的hash算法，
MD5 和 SHA-1 能够说是眼下应用最广泛的Hash算法，
而它们都是以 MD4 为基础设计的。
那么他们都是什么意思呢?

(1)MD4
MD4(RFC 1320)是 MIT 的 Ronald L. Rivest 在 1990 年设计的，
MD 是 Message Digest 的缩写。
它适用在32位字长的处理器上用快速软件实现–-它是基于 32 位操作数的位操作来实现的。

(2)MD5
MD5(RFC 1321)是 Rivest 于1991年对MD4的改进版本号。
它对输入仍以512位分组，其输出是4个32位字的级联，与 MD4 同样。
MD5比MD4来得复杂，而且速度较之要慢一点，但更安全，在抗分析和抗差分方面表现更好。

(3)SHA-1 及其它
SHA1是由NIST NSA设计为同DSA一起使用的，它对长度小于264的输入，
产生长度为160bit的散列值，因此抗穷举(brute-force)性更好。
SHA-1 设计时基于和MD4同样原理,而且模仿了该算法。

哈希表不可避免冲突(collision)现象：
对不同的keyword可能得到同一哈希地址，即key1≠key2，而hash(key1)=hash(key2)。
因此，在建造哈希表时不仅要设定一个好的哈希函数，并且要设定一种处理冲突的方法。
可用如以下描写叙述哈希表：
依据设定的哈希函数 H(key) 和所选中的处理冲突的方法，
将一组keyword映像到一个有限的、地址连续的地址集(区间)上并以keyword在地址集中的“象”作为对应记录在表中的存储位置，
这样的表被称为*哈希表*。

对于动态查找表而言，
1)表长不确定；
2)在设计查找表时，仅仅知道keyword所属范围，而不知道确切的keyword。
因此，普通情况需建立一个函数关系，
以f(key)作为keyword为key的录在表中的位置，
通常称这个函数f(key)为*哈希函数*。
*(注意：这个函数并不一定是数学函数)*

哈希函数是一个映象，即：将keyword的集合映射到某个地址集合上，
它的设置非常灵活，仅仅要这个地址集合的大小不超出同意范围就可以。

现实中哈希函数是须要构造的，而且构造的好才干使用的好。



#那么这些Hash算法究竟有什么用呢?

Hash算法在信息安全方面的应用主要体如今下面的3个方面：
(1) 文件校验
我们比較熟悉的校验算法有奇偶校验和CRC校验，
这2种校验并没有抗数据篡改的能力，
它们一定程度上能检測并纠正传输数据中的信道误码，但却不能防止对数据的恶意破坏。

MD5 Hash算法的“数字指纹”特性，
使它成为眼下应用最广泛的一种文件完整性校验和(Checksum)算法，
不少Unix系统有提供计算md5 checksum的命令。

(2)数字签名
Hash算法也是现代password体系中的一个重要组成部分。
因为非对称算法的运算速度较慢，
所以在数字签名协议中，单向散列函数扮演了一个重要的角色。
对 Hash 值，又称“数字摘要”进行数字签名，
在统计上能够觉得与对文件本身进行数字签名是等效的。
并且这种协议还有其它的长处。

(3)鉴权协议
例如以下的鉴权协议又被称作挑战–认证模式：
在传输信道是可被侦听，但不可被篡改的情况下，这是一种简单而安全的方法。



#文件hash值
MD5-Hash-文件的数字文摘通过Hash函数计算得到。
无论文件长度怎样，它的Hash函数计算结果是一个固定长度的数字。
与加密算法不同，这一个Hash算法是一个*不可逆*的单向函数。
採用安全性高的Hash算法，如MD5、SHA时，两个不同的文件差点儿不可能得到同样的Hash结果。
因此，一旦文件被改动，就可检測出来。

Hash函数还有另外的含义：实际中的Hash函数是指把一个大范围映射到一个小范围。
把大范围映射到一个小范围的目的往往是为了节省空间，使得数据easy保存。
除此以外，Hash函数往往应用于查找上。
所以，在考虑使用Hash函数之前，须要明确它的几个限制：
(1)Hash的主要原理就是把大范围映射到小范围；
...所以，你输入的实际值的个数必须和小范围相当或者比它更小。不然冲突就会非常多。
(2)因为Hash逼近单向函数；所以，你能够用它来对数据进行加密。
(3)不同的应用对Hash函数有着不同的要求；
...比方，用于加密的Hash函数主要考虑它和单项函数的差距，
...而用于查找的Hash函数主要考虑它映射到小范围的冲突率。

应用于加密的Hash函数已经探讨过太多了，本文仅仅探讨用于查找的Hash函数。
Hash函数应用的主要对象是数组（比方，字符串），而其目标通常是一个int类型。
下面我们都依照这样的方式来说明。
一般的说，Hash函数能够简单的划分为例如以下几类：
1.加法Hash；
2.位运算Hash；
3.乘法Hash；
4.除法Hash；
5.查表Hash；
6.混合Hash；