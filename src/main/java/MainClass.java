import  java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    public static final int CARS_COUNT = 3;
    //Две защелки для подготовки машин и для фиксации окончания гонки. Повторно применять их нельзя!
    public static CountDownLatch cdlR = new CountDownLatch(CARS_COUNT);
    public static CountDownLatch cdlF = new CountDownLatch(CARS_COUNT);
    //Барьер для выравнивания машин на одной линии. Многоразовый.
    public static CyclicBarrier cb = new CyclicBarrier(CARS_COUNT);
    //Семафор для прохождения тоннеля
    public static Semaphore smfTonnel = new Semaphore(CARS_COUNT/2);
    //Семафор для победителя (не используется)
    public static Semaphore smfWin = new Semaphore(1);
    //Замок для победителя
    public static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            // Ожидание основным потоком завершения потоков гоночных машин (подготовительный этап)
            // пока счетчик неготовых машин не обнулится, будем стоять на этой строке
            cdlR.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        try {
            // Ожидание основным потоком завершения потоков гоночных машин (соревновательный этап)
            // пока счетчик закончивших трассу машин не обнулится, будем стоять на этой строке
            cdlF.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

    }
}
