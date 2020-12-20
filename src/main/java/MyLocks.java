import  java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLocks {
    private Lock lock = new ReentrantLock();
    private CyclicBarrier cb;
    private CountDownLatch cdl;

    public Lock getLock() {
        return lock;
    }

    public CyclicBarrier getCyclicBarrier() {
        return cb;
    }

    public CountDownLatch getCountDownLatch() {
        return cdl;
    }

    public MyLocks(int objCount) {
        cb = new CyclicBarrier(objCount);
        cdl = new CountDownLatch(objCount);
    }
}
