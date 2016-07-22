package javathreads3.examples.ch02.example3;

import javathreads3.examples.ch02.CharacterEventHandler;
import javathreads3.examples.ch02.CharacterListener;
import javathreads3.examples.ch02.CharacterSource;

import java.util.Random;

public class RandomCharacterGenerator extends Thread implements CharacterSource {
    static char[] chars;
    static String charArray = "abcdefghijklmnopqrstuvwxyz0123456789";

    static {
        chars = charArray.toCharArray();
    }

    Random random;
    CharacterEventHandler handler;

    private volatile boolean done = false;

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
        while (!done) {
            nextCharacter();
            try {
                Thread.sleep(getPauseTime());
            } catch (InterruptedException ie) {
                return;
            }
        }
    }

    public void setDone() {
        done = true;
    }
}
