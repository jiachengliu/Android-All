### Java多线程原理及死锁
先理解以下概念：
1. **寄存器**是CPU内的组成部分，用来暂存指令、数据、位址。CPU控制部件包含指令寄存器、程序计数器，而算术逻辑部件包含累加器。这些寄存器的读写速度非常快。
2. **内存**一般分为ROM、RAM和高速缓存（cache）.
3. **Cache**是CPU与主内存之间的缓存，由于CPU处理速度远快于主内存，所以从Cache读取数据会快很多。
##### 线程调度
任一时刻，CPU只可以执行一条指令，故每个时刻只可以有一个线程获取到CPU的使用权，此时此线程为运行状态。
多线程并发是指多个线程轮流获取CPU使用权，然后处理指令。
JVM按照一定的机制处理线程调度，也就是按照一定的规则处理线程对CPU的使用权。按照线程调度模型分为：**分时调度模型/抢占式调度模型**。

- **分时调度模型**：平均分配每个线程占用的CPU时间片，轮流获取CPU使用权。
- **抢占式调度模型**：JVM采用的是该模型，按照优先级占用CPU。同一优先级线程随机占用CPU，这就保证不了线程被执行的先后顺序。会导致有线程安全问题出现。
##### 线程安全性
- **竞态**，是指对于同一共享变量，在不同时间获取的结果不一定相同。
- **原子性**，在一个线程对共享变量进行读写操作时，其余线程不会参与进来。就是说这个读写操作属于原子操作，过程中不容他人干涉。
- **可见性**，是指某个线程对共享变量修改后，其他变量是否可读取。由于共享变量可能存储于寄存器/Cache/主内存中，所以会导致有些线程可能读取不了更新后的值。
- **有序性**，多核处理器可能不是按照程序代码的顺序执行指令。而是按照哪个先就绪执行哪个指令。不是按照我们想要的顺序处理指令。
##### 线程安全
以上几点会导致线程安全性的问题。要保证线程的安全，需要原子性/可见性/有序性。具体怎么样才能保证呢？那就是锁。下面看下锁是怎么保证线程安全的。
##### 锁的五个特性
- 临界区 准备读取共享变量时，获得锁后和释放锁前执行的代码就做临界区，处理代码的过程。
- 排他性 执行临界区代码时，不可分割，原子性。
- 串行 锁相当于把多个线程对共享变量的读写由并发改为串行，这样会降低效率。
- 锁泄漏 执行临界区代码时，程序出错，导致无法释放锁。而使用synchronized时，方法抛异常的时候，锁仍然可以由JVM来自动释放。
##### 死锁产生实例1

```
/**
 * 一个多线程死锁的经典实例，对应执行类 TestThreadLock
 */
public class DeadLock implements Runnable{

    private static Object object1 = new Object();
    private static Object object2 = new Object();
    private boolean flag;//省的写二个Runnable了。
    public DeadLock(boolean flag){
        this.flag = flag;
    }
    @Override
    public void run() {
        System.out.println("运行的线程："+Thread.currentThread().getName());
        if (flag){
            synchronized (object1){
                System.out.println(Thread.currentThread().getName()+" 锁住了 "+object1);
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized(object2){// 执行不到这里
                    System.out.println("1秒钟后，"+Thread.currentThread().getName()+" 锁住了 "+object2);
                }
            }
        }else {
            synchronized (object2){
                System.out.println(Thread.currentThread().getName()+" 锁住了 "+object2);
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (object1){//执行不到这里
                    System.out.println("1秒钟后，"+Thread.currentThread().getName()+" 锁住了"+object1);
                }
            }
        }
    }
}

```
下面是执行类
```
public class TestThreadLock {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new DeadLock(true),"线程1");
        Thread thread2 = new Thread(new DeadLock(false),"线程2");
        thread1.start();
        thread2.start();
    }

}
```
运行结果如下：

```
> Task :lib_java:TestThreadLock.main()
运行的线程：线程1
运行的线程：线程2
线程1 锁住了 java.lang.Object@17d10736
线程2 锁住了 java.lang.Object@572f3b73
```
这个运行结果明显不是我们期望的。如何避免死锁，是值得研究的。针对上面代码，只需要改成：

```
        Thread thread11 = new Thread(new DeadLock(false),"线程1");
        Thread thread22 = new Thread(new DeadLock(false),"线程2");
```
这样就可以了，这样就相当于object2被线程1释放之后线程2再去获取，此时线程2没有获取到任何锁，相当于等待线程1释放object2。即满足**顺序加锁**同步执行。
##### 死锁产生实例2

```
public class SyncThread implements Runnable{

    private AtomicInteger object1;//不可以使用Integer,因其自动拆箱装箱特性
    private AtomicInteger object2;
    public SyncThread(AtomicInteger object1,AtomicInteger object2){
        this.object1 = object1;
        this.object2 = object2;
    }
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("当前线程："+name);
        synchronized (object1){
            System.out.println(name+"锁住了"+object1);
            dealWork();
            synchronized (object2){
                System.out.println(name+"锁住了"+object2);
                dealWork();
            }
            System.out.println(name+"释放了"+object2);
        }
        System.out.println(name+"释放了"+object1);
        System.out.println(name+"结束");
    }
    private void dealWork() {
        try {
            System.out.println("开始处理dealwork");
            Thread.sleep(3000);
//            System.out.println("结束处理dealwork");
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
```
执行代码

```
AtomicInteger int1 = new AtomicInteger(1);
AtomicInteger int2 = new AtomicInteger(2);
AtomicInteger int3 = new AtomicInteger(3);
Thread thread1 = new Thread(new SyncThread(int1,int2),"thread1");
Thread thread2 = new Thread(new SyncThread(int2,int3),"thread2");
Thread thread3 = new Thread(new SyncThread(int3,int1),"thread3");
thread1.start();
Thread.sleep(1000);
thread2.start();
Thread.sleep(1000);
thread3.start();
```
简单理解一下代码，三个线程的object2还没反应过来，分别就获取了“1”/“2”/“3”。这样thread1再想获取“2”，thread2再想获取“3”，thread3再想获取“1”就获取不了了。
为了不死锁，**避免嵌套锁**改进如下：

```
public void run() {//避免锁的嵌套。
        String name = Thread.currentThread().getName();
        System.out.println("当前线程："+name);
        synchronized (object1){
            System.out.println(name+"锁住了"+object1);
            dealWork();
        }
        System.out.println(name+"释放了"+object1);
        synchronized (object2){
            System.out.println(name+"锁住了"+object2);
            dealWork();
        }
        System.out.println(name+"释放了"+object2);

        System.out.println(name+"结束");
    }
```
这样就可以避免了死锁，因为避免了在同一时刻互相想得到对方的锁。尽管他们都是第一时间获取了“1”/“2”/“3”，但是不影响释放，当释放的时候再被获取后执行后续操作。当然这样会有线程安全的问题。

另外还有一种避免死锁的方法，就是避免线程无限等待对方持有的锁，加一个时间限制，synchronized不具备这个功能，使用Lock类中的tryLock方法去尝试获取锁。

参考 1.[锁机制](https://www.cnblogs.com/tison/p/8283233.html)

2.[多线程优化](https://yq.aliyun.com/articles/741019?spm=a2c4e.11157919.spm-cont-list.21.47def204kHlCIK)







