package javathreads3.examples.ch04.example3;

import javathreads3.examples.ch04.CharacterEventHandler;
import javathreads3.examples.ch04.CharacterListener;
import javathreads3.examples.ch04.CharacterSource;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RandomCharacterGenerator extends Thread implements CharacterSource {
    private static char[] chars;
    private static String charArray = "abcdefghijklmnopqrstuvwxyz0123456789";

    static {
        chars = charArray.toCharArray();
    }

    private Random random;
    private CharacterEventHandler handler;
    private boolean done = true;
    private Lock lock = new ReentrantLock();
    private Condition cv = lock.newCondition();

    public RandomCharacterGenerator() {
        random = new Random();
        handler = new CharacterEventHandler();
    }

    public int getPauseTime() {
        return (int) (Math.max(1000, 5000 * random.nextDouble()));
    }

    public void addCharacterListener(CharacterListener cl) {
        handler.addCharacterListener(cl);
    }

    public void removeCharacterListener(CharacterListener cl) {
        handler.removeCharacterListener(cl);
    }

    public void nextCharacter() {
        handler.fireNewCharacter(this,
                (int) chars[random.nextInt(chars.length)]);
    }

    public void run() {
        try {
            lock.lock();
            while (true) {
                try {
                    if (done) {
                        cv.await();
                    } else {
                        nextCharacter();
                        cv.await(getPauseTime(), TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException ie) {
                    return;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void setDone(boolean b) {
        try {
            lock.lock();
            done = b;

            if (!done) cv.signal();
        } finally {
            lock.unlock();
        }
    }
}
