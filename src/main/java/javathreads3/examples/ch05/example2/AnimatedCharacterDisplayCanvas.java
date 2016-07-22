package javathreads3.examples.ch05.example2;

import javathreads3.examples.ch05.CharacterDisplayCanvas;
import javathreads3.examples.ch05.CharacterEvent;
import javathreads3.examples.ch05.CharacterListener;
import javathreads3.examples.ch05.CharacterSource;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class AnimatedCharacterDisplayCanvas extends CharacterDisplayCanvas implements CharacterListener, Runnable {

    private AtomicBoolean done = new AtomicBoolean(true);
    private AtomicInteger curX = new AtomicInteger(0);
    private AtomicInteger tempChar = new AtomicInteger(0);
    private Thread timer = null;

    public AnimatedCharacterDisplayCanvas() {
        startAnimationThread();
    }

    public AnimatedCharacterDisplayCanvas(CharacterSource cs) {
        super(cs);
        startAnimationThread();
    }

    private void startAnimationThread() {
        if (timer == null) {
            timer = new Thread(this);
            timer.start();
        }
    }

    public void newCharacter(CharacterEvent ce) {
        curX.set(0);
        tempChar.set(ce.character);
        repaint();
    }

    protected void paintComponent(Graphics gc) {
        char[] localTmpChar = new char[1];
        localTmpChar[0] = (char) tempChar.get();
        int localCurX = curX.get();

        Dimension d = getSize();
        int charWidth = fm.charWidth(localTmpChar[0]);
        gc.clearRect(0, 0, d.width, d.height);
        if (localTmpChar[0] == 0)
            return;

        gc.drawChars(localTmpChar, 0, 1,
                localCurX, fontHeight);
        curX.getAndIncrement();
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                if (!done.get()) {
                    repaint();
                }
            } catch (InterruptedException ie) {
                return;
            }
        }
    }

    public void setDone(boolean b) {
        done.set(b);
    }
}
