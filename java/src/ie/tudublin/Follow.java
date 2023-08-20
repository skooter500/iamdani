package ie.tudublin;

public class Follow 
{
    public Follow(String word) {
        this.word = word.toLowerCase();
    }
    @Override
    public String toString() {
        return word + "(" + count + ")";
    }

    String word;
    
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    int count;
    
}
