import  java.util.concurrent.*;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {

        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            // как только готовность получена, уменьшаем счетчик
            MainClass.cdlR.countDown();
            // останавливаем поток до прихода остальных машин чтобы оповестить о готовности
            MainClass.cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        // как только гонку закончила очередная машина, уменьшаем счетчик
        MainClass.cdlF.countDown();

        if (MainClass.lock.tryLock()) {
            System.out.println(this.name + " WIN");
        }

        // семафор для победителя (не используется).
//        try {
//            MainClass.smfWin.acquire();
//            System.out.println(this.name + " WIN");
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            MainClass.smfWin.release();
//        }


        try {
            // ожидаем прихода всех машин на финиш чтобы оповестить об окончании гонки
            MainClass.cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

