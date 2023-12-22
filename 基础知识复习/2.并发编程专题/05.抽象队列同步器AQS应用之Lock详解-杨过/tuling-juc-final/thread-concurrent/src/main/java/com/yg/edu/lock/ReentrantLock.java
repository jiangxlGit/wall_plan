package com.yg.edu.lock;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ReentrantLock implements Lock, java.io.Serializable {
    private static final long serialVersionUID = 7373984872572414699L;
    /**
     * 内部调用AQS的动作，都基于该成员属性实现
     */
    private final Sync sync;

    /**
     * ReentrantLock锁同步操作的基础类,继承自AQS框架.
     * 该类有两个继承类，1、NonfairSync 非公平锁，2、FairSync公平锁
     */
    abstract static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = -5179523762034025860L;

        /**
         * 加锁的具体行为由子类实现
         */
        abstract void lock();

        /**
         * 尝试获取非公平锁
         */
        final boolean nonfairTryAcquire(int acquires) {
            //acquires = 1
            final Thread current = Thread.currentThread();
            int c = getState();
            /**
             * 不需要判断同步队列（CLH）中是否有排队等待线程
             * 判断state状态是否为0，不为0可以加锁
             */
            if (c == 0) {
                //unsafe操作，cas修改state状态
                if (compareAndSetState(0, acquires)) {
                    //独占状态锁持有者指向当前线程
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            /**
             * state状态不为0，判断锁持有者是否是当前线程，
             * 如果是当前线程持有 则state+1
             */
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            //加锁失败
            return false;
        }

        /**
         * 释放锁
         */
        protected final boolean tryRelease(int releases) {
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }

        /**
         * 判断持有独占锁的线程是否是当前线程
         */
        protected final boolean isHeldExclusively() {
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        //返回条件对象
        final ConditionObject newCondition() {
            return new ConditionObject();
        }


        final Thread getOwner() {
            return getState() == 0 ? null : getExclusiveOwnerThread();
        }

        final int getHoldCount() {
            return isHeldExclusively() ? getState() : 0;
        }

        final boolean isLocked() {
            return getState() != 0;
        }

        /**
         * Reconstitutes the instance from a stream (that is, deserializes it).
         */
        private void readObject(java.io.ObjectInputStream s)
                throws java.io.IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0); // reset to unlocked state
        }
    }

    /**
     * 非公平锁
     */
    static final class NonfairSync extends Sync {
        private static final long serialVersionUID = 7316153563782823691L;

        /**
         * 加锁行为
         */
        final void lock() {
            /**
             * 第一步：直接尝试加锁
             * 与公平锁实现的加锁行为一个最大的区别在于，此处不会去判断同步队列(CLH队列)中
             * 是否有排队等待加锁的节点，上来直接加锁（判断state是否为0,CAS修改state为1）
             * ，并将独占锁持有者 exclusiveOwnerThread 属性指向当前线程
             * 如果当前有人占用锁，再尝试去加一次锁
             */
            if (compareAndSetState(0, 1))
                setExclusiveOwnerThread(Thread.currentThread());
            else
                //AQS定义的方法,加锁
                acquire(1);
        }

        /**
         * 父类AbstractQueuedSynchronizer.acquire()中调用本方法
         */
        protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);
        }
    }

    /**
     * 公平锁
     */
    static final class FairSync extends Sync {
        private static final long serialVersionUID = -3000897897090466540L;

        final void lock() {
            acquire(1);
        }

        /**
         * 重写aqs中的方法逻辑
         * 尝试加锁，被AQS的acquire()方法调用
         */
        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                /**
                 * 与非公平锁中的区别，需要先判断队列当中是否有等待的节点
                 * 如果没有则可以尝试CAS获取锁
                 */
                if (!hasQueuedPredecessors() &&
                        compareAndSetState(0, acquires)) {
                    //独占线程指向当前线程
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
    }

    /**
     * 默认构造函数，创建非公平锁对象
     */
    public ReentrantLock() {
        sync = new NonfairSync();
    }

    /**
     * 根据要求创建公平锁或非公平锁
     */
    public ReentrantLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }

    /**
     * 加锁
     */
    public void lock() {
        sync.lock();
    }

    /**
     * 尝试获去取锁，获取失败被阻塞，线程被中断直接抛出异常
     */
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    /**
     * 尝试加锁
     */
    public boolean tryLock() {
        return sync.nonfairTryAcquire(1);
    }

    /**
     * 指定等待时间内尝试加锁
     */
    public boolean tryLock(long timeout, TimeUnit unit)
            throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }

    /**
     * 尝试去释放锁
     */
    public void unlock() {
        sync.release(1);
    }

    /**
     * 返回条件对象
     */
    public Condition newCondition() {
        return sync.newCondition();
    }

    /**
     * 返回当前线程持有的state状态数量
     */
    public int getHoldCount() {
        return sync.getHoldCount();
    }

    /**
     * 查询当前线程是否持有锁
     */
    public boolean isHeldByCurrentThread() {
        return sync.isHeldExclusively();
    }

    /**
     * 状态表示是否被Thread加锁持有
     */
    public boolean isLocked() {
        return sync.isLocked();
    }

    /**
     * 是否公平锁？是返回true 否则返回 false
     */
    public final boolean isFair() {
        return sync instanceof FairSync;
    }

    /**
     * 获取持有锁的当前线程
     */
    protected Thread getOwner() {
        return sync.getOwner();
    }

    /**
     * 判断队列当中是否有在等待获取锁的Thread节点
     */
    public final boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    /**
     * 当前线程是否在同步队列中等待
     */
    public final boolean hasQueuedThread(Thread thread) {
        return sync.isQueued(thread);
    }

    /**
     * 获取同步队列长度
     */
    public final int getQueueLength() {
        return sync.getQueueLength();
    }

    /**
     * 返回Thread集合，排队中的所有节点Thread会被返回
     */
    protected Collection<Thread> getQueuedThreads() {
        return sync.getQueuedThreads();
    }

    /**
     * 条件队列当中是否有正在等待的节点
     */
    public boolean hasWaiters(Condition condition) {
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
            throw new IllegalArgumentException("not owner");
        return sync.hasWaiters((AbstractQueuedSynchronizer.ConditionObject) condition);
    }

}
