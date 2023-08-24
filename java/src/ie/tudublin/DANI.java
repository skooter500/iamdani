package ie.tudublin;

import java.util.ArrayList;

import infiniteforms.Nematode;
import processing.core.PApplet;


public class DANI extends Poly {

	int mode = 0;

    ArrayList<Word> model;
    AudioGarden v;
    String fileName;

    int line = 0;
    int ch = 0;
    public Nematode t;

    public DANI(AudioGarden v, String fileName)
    {
        super(v);
        this.v = v;
        this.fileName = fileName;
        loadFile();
    }

    public void enter()
    {
        sonnet = writeSonnet();
        line = 0;
        ch = 0;
    }

    void printAll()
    {
        for(Word w:model)
        {
            System.out.println(w);
        }
    }


    Word findWord(String word)
    {
        for(Word w:model)
        {
            if (w.getWord().equalsIgnoreCase(word))
            {
                return w;
            }
        }
        return null;
    }

    public void loadFile()
    {
        model = new ArrayList<Word>();
        String[] lines = v.loadStrings(fileName);
        for(String line:lines)
        {
            String[] words = v.split(line, ' '); 
            for(int i = 0 ; i < words.length ; i ++)
            {
                String w = words[i];
                w = w.replaceAll("[^\\w\\s]","");
                Word newWord = findWord(w);
                if (newWord == null)
                {
                    newWord = new Word(w);
                    model.add(newWord);
                }
                if (i < words.length - 1)
                {
                    String nextWord = words[i + 1]; 
                    nextWord = nextWord.replaceAll("[^\\w\\s]","");
                    
                    Follow f = newWord.findFollow(nextWord);
                    if (f == null)
                    {
                        f = new Follow(nextWord);
                        newWord.follows.add(f);
                    }
                    f.setCount(f.getCount() + 1);
                }
            }
        }
    }

    String[] sonnet;

    public String generateName()
    {
        String line = "";
        int start = (int) v.random(0, model.size());
        Word w = model.get(start);
        for(int j = 0 ; j < 2 ; j ++)
        {            
            line += w.getWord() + " ";

            if (w.follows.size() > 0)
            {
                int next = (int) v.random(0, w.follows.size());
                Follow f = w.follows.get(next);
                w = findWord(f.word);
            }
            else
            {
                break;
            }
        }
        return line;
    } 
    
    public String[] writeSonnet()
    {
        String[] sonnet = new String[14];

        sonnet[0] = generateName();
        sonnet[1] = " ";
        for(int i = 2 ; i < 12 ; i ++)
        {
            String line = "";
            int start = (int) v.random(0, model.size());
            Word w = model.get(start);
            for(int j = 0 ; j < 7 ; j ++)
            {            
                line += w.getWord() + " ";

                if (w.follows.size() > 0)
                {
                    int next = (int) v.random(0, w.follows.size());
                    Follow f = w.follows.get(next);
                    w = findWord(f.word);
                }
                else
                {
                    break;
                }
            }
            sonnet[i] = line;
            //v.println(line);            
        }
        sonnet[12] = " ";
        sonnet[13] = "i am DANI ";
        return sonnet;
    }
    
	float off = 0;

    int reset = 0;

	public void render() 
    {

        if (reset == 60 * 35)
        {
            reset = 0;
            enter();      
        }
        reset ++;
        v.textAlign(v.LEFT, v.CENTER);

        float c = v.hueShift(99);

        v.fill(c, 255, 255, v.alp);
        float cx = v.width;
        float cy = v.height / 2;

        v.translate(cx / 3, cy/3);

        

		for(int i = 0 ; i <= line ; i ++)
        { 
            float h = 50;
            if (i != line)
            {
                v.text(sonnet[i], 0, i * h);
            }
            else
            {
                for(int j = 1 ; j <= ch ; j ++)
                {
                    if (j >= sonnet[i].length())
                    {
                        System.out.println("bloop");
                    }
                    else
                    {
                        String s = sonnet[i].substring(0, j);                        
                        v.text(s, 0, i * h);
                    }                    
                }
            }       
        }  
        int interV = (int) (11 - (v.spe * 5));
        if (v.frameCount % interV == 0)
        {
            try
            {
                ch ++;
                if (ch == sonnet[line].length())
                {
                    if (line < sonnet.length - 1)
                    {    
                        ch = 0;
                        line ++;
                    }
                    else
                    {
                        ch --;
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
	}
}