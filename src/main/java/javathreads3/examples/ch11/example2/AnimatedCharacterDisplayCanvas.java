package javathreads3.examples.ch11.example2;

import javathreads3.examples.ch11.CharacterDisplayCanvas;
import javathreads3.examples.ch11.CharacterEvent;
import javathreads3.examples.ch11.CharacterListener;
import javathreads3.examples.ch11.CharacterSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AnimatedCharacterDisplayCanvas extends CharacterDisplayCanvas
        implements ActionListener, CharacterListener {

    private int curX;
    private Timer timer;

    public AnimatedCharacterDisplayCanvas(CharacterSource cs) {
        super(cs);
        timer = new Timer(100, this);
    }

    public synchronized void newCharacter(CharacterEvent ce) {
        curX = 0;
        tmpChar[0] = (char) ce.character;
        repaint();
    }

    public synchronized void paintComponent(Graphics gc) {
        if (tmpChar[0] == 0)
            return;
        Dimension d = getSize();
        int charWidth = fm.charWidth(tmpChar[0]);
        gc.clearRect(0, 0, d.width, d.height);
        gc.drawChars(tmpChar, 0, 1, curX++, fontHeight);
        if (curX > d.width - charWidth)
            curX = 0;
    }

    public void actionPerformed(ActionEvent ae) {
        repaint();
    }

    public void setDone(boolean b) {
        if (!b)
            timer.start();
        else timer.stop();
    }
}
