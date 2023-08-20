package ie.tudublin;

import java.util.ArrayList;

import infiniteforms.Tadpole;
import processing.core.PApplet;


public class DANI {

	int mode = 0;

    ArrayList<Word> model;
    AudioGarden v;
    String fileName;

    int line = 0;
    int ch = 0;
    public Tadpole t;

    public DANI(AudioGarden v, String fileName)
    {
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

    public String[] writeSonnet()
    {
        String[] sonnet = new String[14];
        for(int i = 0 ; i < 14 ; i ++)
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
            v.println(line);            
        }
        return sonnet;
    }
    


	float off = 0;

	public void render() 
    {
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
        
        if (v.frameCount % 5 == 0)
            {
                ch ++;
                if (ch == sonnet[line].length())
                {
                    ch = 0;
                    line ++;
                    if (line == sonnet.length)
                    {
                        if (t != null)
                        {
                            t.enter();
                        }
                        enter();
                    }
                }
            }   
        }
	}
