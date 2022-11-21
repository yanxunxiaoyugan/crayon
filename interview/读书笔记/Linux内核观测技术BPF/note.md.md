# 概念
bpf(berkeley packet filter)是Linux内核中的一个强大的组件.最开始是在1922年steven McCanne和Van Jacobson写了一篇论文: "the BSD Packet Filter: A New Architecture for User-Level Packet Capture", 里面讲述了如果在unix内核中实现数据包的过滤,比传统的数据包过滤技术快20倍.数据包过滤有个目的: 可以编写应用程序使用内核信息来监控系统网络.BPF有两个创新: vm设计、应用程序使用缓存只复制过滤数据包相关的数据。
2014年，alesei Starovoitov实现了eBPF。主要是针对现代化的硬件做了优化（使用了更加优秀的cpu指令、增加了虚拟机中的寄存器数量和大小），而且eBPF用处更广，可以配合XDP等技术一起使用.XDP是一种可以把eBPF挂靠到更低级别（如网络驱动设备）的技术。XDP允许eBPF直接读取和写入网络数据包，并在到达内核之前确定如果处理数据包。XDP和eBPF可以这篇文章：https://www.tigera.io/learn/guides/ebpf/ebpf-xdp/#:~:text=eBPF%20programs%20have%20to%20write,without%20relying%20on%20the%20kernel.
eBPF和BPF的区别：
1. 寄存器数量从2个增加到10个
2. 寄存器宽度从32位增加到64位
![[Pasted image 20220717155806.png]]
eBPF教程：https://blog.stackpath.com/bpf-hook-points-part-1/
###  架构
BPF是一种虚拟机，类似于JVM。LLVM和GCC可以把c代码编译成BPF指令，编译完后使用BPF验证器来确保程序在内核汇总安全运行。BPF验证器能阻止可能是内核崩溃的代码。
HOOK：内核中有很多执行点，当应用程序选择了特定的执行点时，内核会提供一些帮助函数，是执行点和BPF程序能够紧密的配合
BPF映射：主要是为了在内核与用户空间之间共享数据，这个共享和mmap不同，BPF的映射是双向的，支持内核和用户空间分别读写