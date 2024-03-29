1. 搜索引擎发展史
	1. 分类目录：
		1. 通过人工把各个类别的高质量网站整理起来，用户根据分级目录来查找网站
	2. 文本检索：
		1. 采用信息检索模型（如布尔模型、向量空间模型、概率模型），来计算用户查询关键词和网页文本内容的相关程度。网页之间有丰富的链接关系，这一代搜索引擎并未使用这些信息
	3. 链接分析：
		1. 充分利用了网页之间的链接关系，并深入挖掘和利用了网页链接所代表的含义。通常而言，网页链接代表了一种推荐关系，所以通过链接分析可以在海量内容中找出重要的网页。这种重要性本质上是对网页流行程度的一种权衡，因为被推荐次数多的网页代表了其具有流行性。 这一代搜索引擎结果网页流行性和内容相似性来改善搜索质量。
		2. google率先提出并使用pageRank链接分析技术，能够有效改善改善结果质量，但未考虑用户的个性化要求，所以只要输入的查询请求相同，所有的用户都会返回相同的返回结果。另外，很多网站拥有者为了获得更高的搜索排名，针对链接分析算法提出了不少链接作弊方案，导致搜索结果质量变差
	4. 用户中心：
		1. 目前的搜索引擎大都可以归为这一代，即以理解用户需求为核心，不同的用户输入相同的关键字会返回不同的结果
2. 搜索引擎架构图：爬虫把网页去重后，把网页内容通过倒排索引存储起来，还保存一份网页之间的链接关系。 查询时如果没有命中缓存，通过内容相似性和链接分析，然后把网页排序后返回
	1. ![[Pasted image 20221004140854.png]]

3. 动态索引（即实时更新）
	1. 查询时把临时索引和倒排索引一起查询 然后通过已删除文档列表过滤已删除的文档![[Pasted image 20221004184513.png]]
	2. 动态索引通过在内存中维护临时索引，实现了对动态文档的实时搜索，但是随着新加的文档越来越多，内存可能不够，此时需要考虑把临时索引的内容更新到磁盘索引中，常见的更新策略有4种：
		1. 完全重建策略
		2. 再合并策略：类似增量更新，但是会生成新的索引。未改变的文档也会被迁移
			1. ![[Pasted image 20221004185253.png]]
		3. 原地更新策略：如果文档没有改动，不变，如果文档有修改，在老的索引文件里进行追加操作。 为了支持追加操作，会在每个单词的倒排列表末尾预留一定的磁盘空间。如果预留的空间不足以容纳新的倒排列表，需要在磁盘中找到一看完整的连续的存储区，把老索引中的倒排列表（类似于关系型数据库的record: "技术"：”技术出现的文档id，个数，位置“）写入新的磁盘位置。原地更新的出发点很好，但是实验数据证明其索引更新效率比再合并策略低，主要两个原因：
			1. 倒排列表的迁移比较常见， 为了能够快速迁移，需要找到足够大的连续的存储区，这个策略需要对磁盘可用空间进行维护和管理，而这种维护和查找的成本比较高
			2. 对倒排列表迁移后，在进行索引合并时无法顺序读取，还需要维护一个单词到其倒排文件位置的映射表
		4. 混合策略： 将单词根据不同的性质进行分类，不同类别的单词，对其索引采用不同的索引更新策略。常见的做法是：根据单词的倒排列表长度进行区分，长的倒排列表采用原地更新策略，端的倒排列表采用再合并策略。 这么做的原因是： 长倒排列表的读写开销较大，使用原地更新能节省磁盘读写次数；短倒排列表读写开销较小，使用再合并策略能充分利用顺序读和顺序写提高速度
5. 查询处理
	1. 一次一文档
	2. 一次一单词
	3. 示例：
		倒排列表：  搜索引擎 -> {1,3,4},   技术-> {1,2,4}
		用户输入“搜索引擎 技术”,文档1和文档4同时包含了这两个查询词 
		一次一文档：先将两个单词的倒排列表从磁盘读入内存，以倒排列表中的所有文档为单位，将每个文档与查询的最终相似性计算得分，然后根据得分大小进行排序返回给用户
		![[Pasted image 20221004222317.png]]
		一次一单词：![[Pasted image 20221004222354.png]]
5. 多字段索引
	1. 多索引方式
		1. 针对每个不同的字段（eg: 标题，摘要，正文），分别建立一个索引：
		![[Pasted image 20221004223554.png]]
	2. 倒排列表方式
		1. 为了支持对指定字段的搜索，可以将字段信息存储在倒排列表里面
			![[Pasted image 20221004223728.png]]
	3. 扩展列表方式
		1. 用的比较多的方式，具体做法是为每个字段建立一个列表，这个列表记录了每个文档的每个字段出现的位置![[Pasted image 20221004225448.png]]
		2. ![[Pasted image 20221004225505.png]]
6. 短语查询 
	1. 短语是很常见的语言现象，几个经常连在一起被使用的单词就构成
了短语，比如“你懂的”。短语强调单词之间的顺序，有时尽管是同样的 单词，顺序颠倒后会产生完全不同的含义，比如“懂你的”和“你懂的”含 义相差甚远。本质问题是如何在索引中维护单词之间的顺序关系或者位置信息。常见的短语查询方法有：
		1. 位置信息索引
			1. 倒排列表可以存储三种信息：文档ID，单词频率，单词位置信息。如果存储 了位置信息，会导致倒排列表的长度剧烈增长，消耗了存储空间，影响了读取效率，所以一般补不存储单词的位置信息。但是如果索引记录了单词位置信息，则可以很方便的支持短语查询
			2. eg: 用户输入短语查询“爱情买卖”，文档5和文档9同时包含了两个查询词，为了判断在这两个文档中，用户查询是否以短语的当时存在，还要判断位置信息。可以看出文档5中的“爱情”和“买卖”是一个短语，所以把文档5给返回。尽管位置索引可以支持短语查询，但是对于某些查询存储和计算代价比较高，例如“我的团长我的团”
			3. ![[Pasted image 20221004230154.png]]
							1. <5,2,[3,7]>表示“爱情”在文档id=5中出现了2次，其位置分别是3和7
		2. 双词索引：短语至少包含两个词。统计数据表名，二词短语在短语中比例最高，提供二词短语的快速查询是最紧要的问题
			1. 对于二词短语来说
		3. 短语索引：对热门短语和高频短语进行索引![[Pasted image 20221005225747.png]]
	4. 分布式索引：按文档分区或者按单词分区，目前基本上是按文档分区，主要一下几个原因：
		1. 可扩展性：如果按文档拆分，新增文档时，新增索引服务器即可，对系统其他部分影响很小；如果按单词拆分，几乎对所有的索引服务器都有影响
		2. 负载均衡：如果按单词划分，常见的单词的倒排列表会由一台索引服务器维护，如果这个单词是流行词汇，会有很多用户搜索，会给这个服务器造成压力；如果按文档拆分，常见的单词会比较均匀的分布
		3. 容错性：如果某个索引服务器发生故障，如果按照文档拆分，只影响部分文档，对搜索结果有影响，但还是能够使用；如果按单词拆分，如果用户搜索了故障的索引服务器会导致没有搜索结果
		4. 查询方式：如果按单词拆分，只能支持一次一单词查询处理方式，按文档拆分两种都可以支持
	5. 总结：![[Pasted image 20221005230620.png]]
6. 索引的压缩
	1. 概念： 对于海量网页数据，为其建立倒排索引需要花费较大的磁盘空间，尤其是一些常见的单词，这些单词的倒排列表可能会非常大。如果用户的查询包含常见词汇，需要将这些较大的倒排列表从磁盘读入内存，由于需要经过磁盘IO，所以包含常用词的用户查询其响应速度会收到影响。为了加快磁盘IO，同时减少磁盘空间的资源，我们需要压缩
	2. 倒排索引主要由两部分构成：单词字典和单词对应的倒排列表，所以针对索引的压缩可以分为单词字典的压缩和倒排列表的压缩。倒排列表的压缩又可以分为无损压缩和有损压缩。无损压缩指可以通过加压缩完全恢复原始信息，而有损压缩是通过损失部分不重要的信息，来获得更高的数据压缩率
	3. 字典压缩：
		1. 为了提高查询速度，字典数据往往会全部加载到内存中，为了减少字典信息的内存，可以对单词字典使用压缩
7. 检索模型和搜索排序 
	1. 检索模型：
		1. 布尔模型：
			1. 检索模型中最简单的一种，其数学基础是集合论。比如用户想查找苹果公司相关的信息，可以使用这个表达式（苹果 and （乔布斯 or ipad））；可以查出，只要文档满足逻辑表达式，就认为是相关的，否则就是不相关的。缺点是无法根据相关程度进行排序，搜索结果过于粗糙，并且要求用户以布尔表达式来构建查询，对用户要求过高
		2. 向量空间模型
			1. 向量空间模型把每个文档看做是t维特征组成的向量，特征的定义可以采取不同的方式，可以是单词、词组、N-gram片段，最常见的是以单词为特征。其中每个特征会根据一定的依据计算其权重，这t维带权重的特征共同构成了一个文档。在实际应用中t的大小非常高，达到成千上万维很正常
			2. 相似性计算：需要按照用户查询的特征向量和文档特征向量做相似性计算，然后按文档的相似性得分排序作为结果输出。最常见的相似性计算方式是：Cosine计算
			3. 特征权重计算：文档和查询转换为特征向量时，每个特征都会赋予一定的权重，计算这个权重的方式有很多种，一般的计算方式主要考虑亮哥哥计算因子
				1. 词频因子（Tf）：Tf计算因子代表了词频，即一个单词在文档中出现的次数，一种词频因子的计算公式是： W（Tf） = 1 + log(Tf),取对数是为了抑制差异过大的情况。另一种计算公式是W（Tf） = a + （1 - a）* Tf/Max(Tf),a取0.4效果较好，这样主要是对长文档的一种抑制
				2. 逆文档频率（IDF）： 代表是文档集合范围的一种全局因子。给定一个文档集合，每个单词的IDF值就唯一确定，跟具体的文档无关。IDF考虑的是不是文档本身的特征，而是特征单词之间的相对重要性。IDF的计算公式为：IDF(k) = log(N/N(k)),其中N 代表文档集合中总共有多少个文档，N(k)表示特征单词k在多少个文档中出现过，即文档频率。由公式可知，文档频率越高，IDF的值越低。在极端情况下，可能每个文档都出现了term即N（k） = N,说明带上term进行搜索不能区分相关文档。所以IDF就是衡量不同单词对文档的分区能力，其值越高，代表其区分不同文档差异的能力越强
				3. TF * IDF 就是文档的权重了
		3. 概率模型：
			1. 概率模型是目前最好的模型之一
			2. 原理：给定一个用户查询，如果搜索系统能够在搜索结果排序时按照文档和用户需求的相关性由高到低排序，那么这个搜索系统的准确性是最优的，而在文档集合的基础上尽可能准确的对这种相关性进行估计。这是一种对用户需求相关性进行建模的方法
			3. 概率模型把所有的文档分为相关文档和不相关文档
		4. 语言模型：从基本思路上来说，其他的大多数检索模型的思考路径是从查询到
文档，即给定用户查询，如何找出相关的文档。语言模型方法的思路正 好相反，是由文档到查询这个方向，即为每个文档建立不同的语言模 型，判断由文档生成用户查询的可能性有多大，然后按照这种生成概率 由高到低排序，作为搜索结果。给定了一篇文档和对应的用户查询，如何计算文档生成查询的概率呢？图5-10展示了利用语言模型来生成查询的过程。首先，可以为每个 文档建立一个语言模型，语言模型代表了单词或者单词序列在文档中的 分布情况。我们可以想象从文档的语言模型生成查询类似于从一个装 满“单词”的壶中随机抽取，一次抽取一个单词，这样，每个单词都有一 个被抽取到的概率。对于查询中的单词来说，每个单词都对应一个抽取 概率，将这些单词的抽取概率相乘就是文档生成查询的总体概率：![[Pasted image 20221006214921.png]]
![[Pasted image 20221006214946.png]]
一般采用数据平滑的方式解决数据稀疏问题
		5. 机器学习排序算法：
			1. 前面的检索模型都是用查询和文档的相关性进行排序，所考虑的因素不多，主要是利用词频、逆文档频率和文档长度这几个因子来人工模拟排序公式。机器学习排序可以考虑更多的因子，排序公式可以由机器自动学习获得。机器学习排序系统由4个步骤组成：人工标注训练数据、文档特征抽取、学习分类函数、在实际搜索系统中采用机器学习模型。
8. 链接分析
	1. 