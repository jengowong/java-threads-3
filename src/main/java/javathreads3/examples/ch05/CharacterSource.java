package javathreads3.examples.ch05;

public interface CharacterSource {
    void addCharacterListener(CharacterListener cl);

    void removeCharacterListener(CharacterListener cl);

    void nextCharacter();
}    
