class Tadpole
{
  float length = 5;


  String name = "";
  float w = 50;

  int limbs = 1;
  float eyes = 2;
  char gender = 'h';
  float r = w * 0.5f;
  float eyeRadius = w * 0.1f;
  float  c1, c2;
  float fatness = 1.0f;
  
  float speed = 0.0f;
  
  float alpha = 127;
  
    float cw = 50;


  public Tadpole(int length, String name, int limbs, int eyes, char gender) {
    this.length = length;
    this.name = name;
    this.limbs = limbs;
    this.eyes = eyes;
    this.gender = gender;
  }
  
  
  
  public Tadpole()
  {
    randomise();
  }
  
  public void randomise()
  {
    //length = (int) random(1, 15); 
    limbs = (int) random(0, 2);
    eyes = (int) random(0, 9);
    
    char[] genders = {'m','f', 'h', 'n'};    
    gender = genders[(int)random(0, genders.length)];
    
    //c1 = random(0, 255);
    
    
    int nameLength = (int)random(1,5);
    name = "";
    for(int i = 0 ; i < nameLength ; i ++)
    {
      name += nameCombos[(int)random(0, nameCombos.length)] + " ";
    }
    name = name.substring(0, name.length() - 1);
    
    println(this);
  }
  

  public void render(float cx, float cy, float offs)
  {
    
    c2 = c1 + cw;

    float half = w * length * 0.5f;
    strokeWeight(3);
    pushMatrix();
    translate(cx, cy);
    translate(0, - half);
    noFill();
    float hw = w / 2;

    textSize(36);
    textAlign(CENTER, CENTER);
    fill(c2 % 255, 255, 255, alpha);  
    //text(name, 0, -w * 3);
    noFill();
    for (int i = 0; i < length; i ++)
    { 
      //println(c1, c2);
      stroke(pingpongmap(i, 0, (length-1) * 0.5f, c1, c2) % 255, 255, 255, alpha);
    
      float y = i * w;
      float f = 0.5f;
      float w1 = sin(map(i, 0, length, f + f, PI)) * fatness; 
      //w = w1;
      //w = w1;
      ellipse(0, y, w1 * 2f, w);
      if (limbs > 0 && i > 0)
      {
        line(-w1, y, - w1 - w1, y);
        line(w1, y, w1 * 2, y);
        circle((-w1 * 2.0f) - eyeRadius, y, eyeRadius * 2);
        circle((w1 * 2.0f) + eyeRadius, y, eyeRadius * 2);
      }      
      if (i == 0)
      {
        drawEyes(eyes, w1, w * 0.5f);
      }
    }
    
    drawGenitals();

    popMatrix();
  }

  private void drawGenitals()
  {
    switch (gender)
    {
    case 'm':
      { 
        float y =  ((length-1) * w) + (w * 0.5f);   
        line(0, y, 0, y + r);
        circle(0, y + r + eyeRadius, eyeRadius * 2.0f);
      }
      break; 
    case 'f':  
      { 
        float y =  ((length-1) * w);  
        circle(0, y, eyeRadius * 4.0f);
      }
      break;
    case 'h':
      {
        float y =  ((length-1) * w);  
        circle(0, y, eyeRadius * 4.0f);
        y =  ((length-1) * w) + (w * 0.5f);   
        line(0, y, 0, y + r);
        circle(0, y + r + eyeRadius, eyeRadius * 2.0f);
      }
      break;
    }
  }
  
  public String toString() {
    return "Tadpole [c1=" + c1 + ", c2=" + c2 + ", eyeRadius=" + eyeRadius + ", eyes=" + eyes + ", gender=" + gender
            + ", length=" + length + ", limbs=" + limbs + ", name=" + name + ", r=" + r + ", w=" + w + "]";
}

  void drawEyes(float numEyes, float hw, float hh)
  { //<>//
    float offs = 90.0f / (float)numEyes;
    for(float i = 0 ; i < numEyes ; i ++)
    {
      float angle = map(i, 0, numEyes, -90, 90) + offs;
      float stalkLength = r * 0.25f + (sin(map(angle, -90, 90, 0, PI)) * r * 8);
      drawEye(angle, stalkLength, hw, hh);
    }     
  }

  private void drawEye(float angle, float stalkLength, float headW, float headH)
  {
    
    float x1 = sin(radians(angle)) * (headW);
    float y1 = - cos(radians(angle)) * (headH);

    float x2 = sin(radians(angle)) * (headW + stalkLength);
    float y2 = - cos(radians(angle)) * (headH + stalkLength);
    float ex = sin(radians(angle)) * (headW + stalkLength + eyeRadius);
    float ey = - cos(radians(angle)) * (headH + stalkLength + eyeRadius);
    circle(ex, ey, eyeRadius * 2.0f);
    line(x1, y1, x2, y2);
  }
}
