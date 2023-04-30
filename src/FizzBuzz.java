public class FizzBuzz {
    /*у класі 4 методи для кожного потоку
        кожен включає цикл while(true), який переривається тільки коли дане число більше, ніж n - кінцеве число
        для синзронізації використані методу потоків wait() та notifyAll().
       вони забезпечують правильне виконання потоків: кожен потік чекає, якщо число не належить до своєї умови,
       щоб інший метод опрацював це число
     */
    private int n;
    private int currentNumber;
    private Object monitor;

    //конструктор ініціалізує об'єкт класу з кінцевим числом
    public FizzBuzz(int n) {
        this.n = n;
        this.currentNumber = 1;
        this.monitor = new Object();
    }

    //перевіряємо, що число ділиться на 3 та не ділиться на 5
    public void fizz() {
        while (true) {
            //обов'язкова синхронізація, інакше потоки працюють навперебій та програма видає неправильний результат
            synchronized (monitor) {
                if (currentNumber > n) {
                    return;
                }
                if (currentNumber % 3 == 0 && currentNumber % 5 != 0) {
                    System.out.print("fizz, ");
                    currentNumber++;
                    monitor.notifyAll();
                } else {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //перевіряємо, що число ділиться на 5 та не ділиться на 3
    public void buzz() {
        while (true) {
            synchronized (monitor) {
                if (currentNumber > n) {
                    return;
                }
                if (currentNumber % 5 == 0 && currentNumber % 3 != 0) {
                    System.out.print("buzz, ");
                    currentNumber++;
                    monitor.notifyAll();
                } else {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //перевіряємо, що число ділиться на 5 та на 3
    public void fizzbuzz() {
        while (true) {
            synchronized (monitor) {
                if (currentNumber > n) {
                    return;
                }
                if (currentNumber % 3 == 0 && currentNumber % 5 == 0) {
                    System.out.print("fizzbuzz, ");
                    currentNumber++;
                    monitor.notifyAll();
                } else {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //якщо не ділиться ні на 3, ні на 5, цей метод виводить саме число
    public void number() {
        while (true) {
            synchronized (monitor) {
                if (currentNumber > n) {
                    return;
                }
                if (currentNumber % 3 != 0 && currentNumber % 5 != 0) {
                    System.out.print(currentNumber + ", ");
                    currentNumber++;
                    monitor.notifyAll();
                } else {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //створюємо об'єкт класу, 4 потоки, запускаємо їх та використовуємо join() (якщо один потік переривається, інщі теж мають)
    public static void main(String[] args) {
        int n = 50;
        FizzBuzz fizzBuzz = new FizzBuzz(n);

        Thread threadA = new Thread(() -> fizzBuzz.fizz());
        Thread threadB = new Thread(() -> fizzBuzz.buzz());
        Thread threadC = new Thread(() -> fizzBuzz.fizzbuzz());
        Thread threadD = new Thread(() -> fizzBuzz.number());

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
