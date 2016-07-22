package javathreads3.examples.ch03;

public interface CharacterSource {
    public void addCharacterListener(CharacterListener cl);

    public void removeCharacterListener(CharacterListener cl);

    public void nextCharacter();
}    
