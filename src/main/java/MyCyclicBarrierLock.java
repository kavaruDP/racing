import  java.util.concurrent.*;

public class MyCyclicBarrierLock {
    private CyclicBarrier cb;

    public CyclicBarrier getCyclicBarrier() {
        return cb;
    }

    public MyCyclicBarrierLock(int objLimit) {
        cb = new CyclicBarrier(objLimit);
    }

}
