package ie.tudublin;

import ddf.minim.AudioInput;
import processing.core.PApplet;
import processing.data.TableRow;

public class Nematode extends Poly
{

    int length = 5;

    public int getLength() {
        return length;
    }


    public void setlength(int length) {
        this.length = length;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public float getW() {
        return w;
    }


    public void setW(float w) {
        this.w = w;
    }


    public int getLimbs() {
        return limbs;
    }


    public void setLimbs(int limbs) {
        this.limbs = limbs;
    }




    public char getGender() {
        return gender;
    }


    public void setGender(char gender) {
        this.gender = gender;
    }


    public float getR() {
        return r;
    }


    public void setR(float r) {
        this.r = r;
    }


    public float getEyeRadius() {
        return eyeRadius;
    }


    public void setEyeRadius(float eyeRadius) {
        this.eyeRadius = eyeRadius;
    }

    String name = "Test";
    float w = 50;

    int limbs = 1;
    int eyes = 2;
    char gender = 'h';
    float r = w * 0.5f;
    float eyeRadius = w * 0.1f;

    public Nematode(AudioGarden v, int length, String name, int limbs, boolean eyes, char gender) {
        super(v);
        this.v = v;
        this.length = length;
        this.name = name;
        this.limbs = limbs;
        this.gender = gender;
    }

    void drawEyes(float numEyes, float hw, float hh)
  { //<>//
    float offs = 90.0f / (float)numEyes;
    for(float i = 0 ; i < numEyes ; i ++)
    {
      float angle = v.map(i, 0, numEyes, -90, 90) + offs;
      float stalkLength = r * 0.25f + (v.sin(v.map(angle, -90, 90, 0, v.PI)) * r * 8);
      drawEye(angle, stalkLength, hw, hh);
    }     
  }

  private void drawEye(float angle, float stalkLength, float headW, float headH)
  {
    
    float x1 = v.sin(v.radians(angle)) * (headW);
    float y1 = - v.cos(v.radians(angle)) * (headH);

    float x2 = v.sin(v.radians(angle)) * (headW + stalkLength);
    float y2 = - v.cos(v.radians(angle)) * (headH + stalkLength);
    float ex = v.sin(v.radians(angle)) * (headW + stalkLength + eyeRadius);
    float ey = - v.cos(v.radians(angle)) * (headH + stalkLength + eyeRadius);
    v.circle(ex, ey, eyeRadius * 2.0f);
    v.line(x1, y1, x2, y2);
  }

    public void enter()
    {
        limbs = (int) v.random(0, 2);
        eyes = (int) v.random(0, 9);
    
        
        char[] genders = {'m','f', 'h', 'n'};    
        gender = genders[(int)v.random(0, genders.length)];
        
        //c1 = random(0, 255);
    
        //fatness = v.random(50, 200);
        
        
        int nameLength = (int)v.random(1,5);
        name = "";
        for(int i = 0 ; i < nameLength ; i ++)
        {
          name += nameCombos[(int)v.random(0, nameCombos.length)] + " ";
        }
        name = name.substring(0, name.length() - 1);
        colorOffset = v.random(0, 256);
    }

    String[] nameCombos = {"Safe", "Milk", "Strictly", "Personal", "Trout", "Mask", "Replica", "Lick", "My", "Decals", "Off", "Baby", "Mirror", "Man", "Spotlight", "Kid", "Clear", "Spot", "Unconditionally", "Guaranteed", "Bluejeans", "Moonbeams", "Shiny", "Beast", "Bat", "Chain", "Puller", "Doc", "Radar", "Station", "Ice", "Cream", "Crow", "Bat", "Chain", "Puller"};


    
    public Nematode(AudioGarden v)
    {
        super(v);
    }

    float colorOffset = 0;

    public void render()
    {
        render(v.width / 2, v.height / 1.8f);
    }

    public void render(float cx, float cy)
    {
        
        float half = w * length * 0.5f; 

        v.strokeWeight(2);
        v.pushMatrix();
        v.translate(cx, cy);
        v.translate(0, - half);
        float hw = w / 2;
        v.textSize(36);
        v.fill(colorOffset, 255, 255);
        v.textAlign(PApplet.CENTER, PApplet.CENTER);
        v.text(name, 0, -w * 2);
        v.noFill();
        
        for(int i = 0 ; i < length ; i ++)
        {
            float c = v.pingpongmap(i, 0, (length-1) * 0.5f, 0, 255) % 255;
            c = v.hueShift(c + colorOffset);
            v.stroke(c, 255, 255);
            float y = i * w;
            v.ellipse(0, y, w, w);       
            if (limbs > 0)
            {
                v.line(-hw, y, - hw - hw, y);
                v.line(hw, y, hw * 2, y);
            }
            if (i == 0)
                {
                    drawEyes(eyes, w, w * 0.5f);
                }
        }

        drawGenitals();

        v.popMatrix();
    }

    private void drawGenitals()
    {
        switch (gender)
        {
            case 'm':
                {
                  float y1 =  (length * w) - r;
                  v.line(0, y1, 0,  y1 + r);
                  v.circle(0, y1 + r + eyeRadius, eyeRadius * 2.0f);
                }
                break;
            case 'f':
                {
                    float y =  ((length - 1) * w);
                    v.circle(0, y, eyeRadius * 4.0f);                      
                }
                break;
            case 'h':
                {
                    float y1 =  (length * w) - r;
                    v.line(0, y1, 0,  y1 + r);
                    v.circle(0, y1 + r + eyeRadius, eyeRadius * 2.0f);
  
                    float y =  ((length - 1) * w);
                    v.circle(0, y, eyeRadius * 4.0f);                      
                }
                break;
        }
    }

    private void drawEye(float angle)
    {
        float x1 = PApplet.sin(PApplet.radians(angle)) * (r);
        float y1 = - PApplet.cos(PApplet.radians(angle)) * (r);
        
        float x2 = PApplet.sin(PApplet.radians(angle)) * (r + r);
        float y2 = - PApplet.cos(PApplet.radians(angle)) * (r + r);
        float ex = PApplet.sin(PApplet.radians(angle)) * (r + r + eyeRadius);
        float ey = - PApplet.cos(PApplet.radians(angle)) * (r + r + eyeRadius);
        v.circle(ex, ey, eyeRadius * 2.0f);
        v.line(x1, y1, x2, y2);

    }
}
