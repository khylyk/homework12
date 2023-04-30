import java.time.Duration;
import java.time.Instant;

public class Timer {
    public static void main(String[] args) {

        //цей потік рахує різницю між моментом початку та теперішнім, виводить різницю у секудах та чекає 1 секунду - в циклі
        Thread timeThread = new Thread(() -> {
            Instant start = Instant.now();
            while(true){
                Instant current = Instant.now();
                Duration elapsed = Duration.between(start, current);
                long seconds = elapsed.getSeconds();
                System.out.println("Elapsed " + seconds + " second(s)");
                try {
                    //затримка на 1000 мілісекунд, аби писався текст саме кожну секунду
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        timeThread.start();

        //цей потік чекає 5 секунд (5000 мілісекунд) та виводить текст у нескінченному циклі
        Thread fiveSecondsThread = new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Минуло 5 секунд");
            }
        });
        fiveSecondsThread.start();
    }


}