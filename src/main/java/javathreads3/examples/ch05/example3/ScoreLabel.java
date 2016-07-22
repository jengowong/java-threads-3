package javathreads3.examples.ch05.example3;

import javathreads3.examples.ch05.CharacterEvent;
import javathreads3.examples.ch05.CharacterListener;
import javathreads3.examples.ch05.CharacterSource;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicReference;

public class ScoreLabel extends JLabel implements CharacterListener {
    private AtomicScoreAndCharacter scoreAchar = new AtomicScoreAndCharacter();
    private AtomicReference<CharacterSource> generator = null;
    private AtomicReference<CharacterSource> typist = null;

    public ScoreLabel(CharacterSource generator, CharacterSource typist) {
        this.generator = new AtomicReference<CharacterSource>(generator);
        this.typist = new AtomicReference<CharacterSource>(typist);

        if (generator != null)
            generator.addCharacterListener(this);
        if (typist != null)
            typist.addCharacterListener(this);
    }

    public ScoreLabel() {
        this(null, null);
    }

    public void resetGenerator(CharacterSource newGenerator) {
        CharacterSource oldGenerator;

        if (newGenerator != null)
            newGenerator.addCharacterListener(this);

        oldGenerator = generator.getAndSet(newGenerator);
        if (oldGenerator != null)
            oldGenerator.removeCharacterListener(this);
    }

    public void resetTypist(CharacterSource newTypist) {
        CharacterSource oldTypist;

        if (newTypist != null)
            newTypist.addCharacterListener(this);

        oldTypist = typist.getAndSet(newTypist);
        if (oldTypist != null)
            oldTypist.removeCharacterListener(this);
    }

    public void resetScore() {
        scoreAchar.set(0, -1);
        setScore();
    }

    private void setScore() {
        // This method will be explained later in chapter 7
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setText(Integer.toString(scoreAchar.getScore()));
            }
        });
    }

    public void newCharacter(CharacterEvent ce) {
        // Previous character not typed correctly - 1 point penalty
        if (ce.source == generator.get()) {
            scoreAchar.setCharacterUpdateScore(ce.character);
            setScore();
        }
        // If character is extraneous - 1 point penalty
        // If character does not match - 1 point penalty
        else if (ce.source == typist.get()) {
            scoreAchar.processCharacter(ce.character);
            setScore();
        }
    }
}
