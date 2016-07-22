package javathreads3.examples.ch12;

public interface CharacterSource {
    public void addCharacterListener(CharacterListener cl);

    public void removeCharacterListener(CharacterListener cl);

    public void nextCharacter();
}    
