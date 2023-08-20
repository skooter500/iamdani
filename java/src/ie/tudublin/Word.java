package ie.tudublin;

import java.util.ArrayList;

public class Word {

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }

    private String word;
    public ArrayList<Follow> follows;

    public Word(String word) {

        this.word = word.toLowerCase();
        follows = new ArrayList<Follow>();
    }
    @Override
    public String toString() {
        String ret =word + ": ";
        for (Follow follow : follows) {
            ret += follow + " ";
        }
        return ret;
    }

    Follow findFollow(String word)
    {
        for(Follow f: follows)
        {
            if (f.getWord().equalsIgnoreCase(word))
            {
                return f;
            }
        }
        return null;
    }
}
