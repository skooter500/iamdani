package ie.tudublin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import infiniteforms.Nematode;
import processing.core.PApplet;


public class Basic extends Art {

	int mode = 0;

    ArrayList<Word> model;
    IAMDANI v;
    String fileName;

    int line = 0;
    int ch = 0;
    public Nematode t;

    public String toString()
  {
    return fileName;
  }

    public Basic(IAMDANI v, String fileName)
    {
        super(v);
        this.v = v;
        this.fileName = fileName;
        loadFile();
    }

    public void enter()
    {
        v.println("run");
        sonnet = writeSonnet();
        line = 0;
        ch = 0;
        f = v.random(265);
        v.cqz = 1;
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
        int sl = 25;
        String[] sonnet = new String[sl];        
        int startAt = (int) v.random(lines.length - 25);
        for(int i = 0 ; i < 25 ; i ++)
        {
            sonnet[i] = lines[startAt + i];                                            
        }
        for(String line:sonnet)
        {
            System.out.println(line);
        }


         return sonnet;
        /*
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

        for(String s:sonnet)
        {
            v.println(s);
        }
         return sonnet;
         */
    }
    
	float off = 0;

    int reset = 0;
    int toPass = 100;

	public void render()     
    {
        
        v.rotateZ(v.rol);
    
    
        v.textAlign(v.LEFT, v.CENTER);

        float c = v.hueShift(f);

        v.fill(c, 255, 255, v.alp);
        float cx = v.width;
        float cy = v.height / 2;

        v.rotateX(v.pit);
        v.rotateY(-v.yaw + 0.13f);
        v.rotateZ(v.rol);
        v.translate(cx * .30f, cy*.1f);
        

		for(int i = 0 ; i <= line ; i ++)
        { 
            float h = 36;
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
        int interV = v.max(1, (int) (11 - (v.spe * 10)));
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