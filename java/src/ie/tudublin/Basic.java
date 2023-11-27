package ie.tudublin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import infiniteforms.Nematode;
import processing.core.PApplet;


public class Basic extends Poly {

	int mode = 0;

    ArrayList<Word> model;
    AudioGarden v;
    String fileName;

    int line = 0;
    int ch = 0;
    public Nematode t;

    public Basic(AudioGarden v, String fileName)
    {
        super(v);
        this.v = v;
        this.fileName = fileName;
        loadFile();
    }

    public void enter()
    {
        writeSonnet();
        line = 0;
        ch = 0;
        f = v.random(265);
    }

    float f = 0;

    void printAll()
    {
        for(Word w:model)
        {
            System.out.println(w);
        }
    }

    String[] lines;

    public void loadFile()
    {
        lines = v.loadStrings(fileName);        
    }

    String[] sonnet;

    public String generateName()
    {
        return "LISt";
    } 
    
    public String[] writeSonnet()
    {
        String[] sonnet = new String[16];

        sonnet[0] = generateName();
        sonnet[1] = "ok";
        int startAt = (int) v.random(lines.length - 16);
        for (int i = 0 ; i < 10; i ++)
        {
            sonnet[i + 2] = lines[startAt + i];
        }
        sonnet[12] = " ";
        sonnet[13] = "i am DANI ";
        sonnet[14] = " ";        
        sonnet[15] = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(Calendar.getInstance().getTime());

         return sonnet;
    }
    
	float off = 0;

    int reset = 0;

	public void render()     
    {
        v.textAlign(v.LEFT, v.CENTER);

        float c = v.hueShift(f);

        v.fill(c, 255, 255, v.alp);
        float cx = v.width;
        float cy = v.height / 2;

        v.translate(cx * .4f, cy/3);

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
        int interV = v.max(1, (int) (11 - (v.spe * 5)));
        if (v.frameCount % interV == 0)
        {
            try
            {
                ch ++;
                if (ch == sonnet[line].length())
                {
                    if (line < sonnet.length - 1)
                    {    
                        line ++;
                        ch = 0;
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
                v.println("ch: " + ch);
                v.println("line: " + line);
                
                sonnet = writeSonnet();
            }
        }
	}
}