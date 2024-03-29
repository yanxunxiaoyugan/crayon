# flink1.13

调用其他转换操作之后返回的数据类型是 SingleOutputStreamOperator，说明这是一个算子操 作；而 keyBy 之后返回的数据类型是KeyedStream

一般不会在程序中设置全局并行度，因为如果在程序中对全局并行度进行硬编码，会导致无法动态扩容。

最佳实践就是在代码中只针对算子设置并行度，不设置全局并行度，这样方便我们提交作业时进行动态扩容

``` 
// 禁用算子链 .map(word -> Tuple2.of(word, 1L)).disableChaining();``` 

 // 从当前算子开始新链 .map(word -> Tuple2.of(word, 1L)).startNewChain() ```
```

整个流处理程序的并行度，就应该是所有算子并行度中最大的那个， 这代表了运行程序需要的 slot 数量



另外，除了事件时间和处理时间，Flink 还有一个“摄入时间”（Ingestion Time）的概念， 它是指数据进入 Flink 数据流的时间，也就是 Source 算子读入数据的时间。摄入时间相当于是 事件时间和处理时间的一个中和，它是把 Source 任务的处理时间，当作了数据的产生时间添 加到数据里。这样一来，水位线（watermark）也就基于这个时间直接生成，不需要单独指定 了。这种时间语义可以保证比较好的正确性，同时又不会引入太大的延迟。它的具体行为跟事 件时间非常像，可以当作特殊的事件时间来处理。 在 Flink 中，由于处理时间比较简单，早期版本默认的时间语义是处理时间；而考虑到事 件时间在实际应用中更为广泛，从 1.12 版本开始，Flink 已经将事件时间作为了默认的时间语 义。



我们直接用数据的时间戳来指示当前的时间进展，窗口的关闭自然 也是以数据的时间戳等于窗口结束时间为准，这就相当于可以不受网络传输延迟的影响了。像 之前所说 8 点 59 分 59 秒生产出来的商品，到车上的时候不管实际时间（系统时间）是几点， 我们就认为当前是 8 点 59 分 59 秒，所以它总是能赶上车的；而 9 点这班车，要等到 9 点整生 产的商品到来，才认为时间到了 9 点，这时才正式发车。这样就可以得到正确的统计结果了

