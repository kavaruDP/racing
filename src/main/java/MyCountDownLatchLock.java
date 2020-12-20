import  java.util.concurrent.*;

public class MyCountDownLatchLock {
    private CountDownLatch cdl;

    public CountDownLatch getCountDownLatch() {
        return cdl;
    }

    public MyCountDownLatchLock(int objLimit) {
        cdl = new CountDownLatch(objLimit);
    }
}
