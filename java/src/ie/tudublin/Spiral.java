package ie.tudublin;

import processing.core.PApplet;

public class Spiral extends Poly{

  float angle;
  
  public Spiral(AudioGarden v){
    super(v);
    
  }

  
  
  @Override
  public void render(int ellapsed){
    // v.background(0);

    float x = v.width;
    float diameter = 150;
    int num = 100;
    float cRange;
    

    v.translate(v.width/2, v.height/2); //sentres the visual
    
    for(float a = 0; a<360; a+=22.5f){ //used to create circular rotation lower value for 'a' means more cirles and tighter rotation can also cause lag if too low
      v.rotate(-AudioGarden.radians(a));
      v.noStroke();
      v.pushMatrix();
      
      for (int i=0; i <num; i++){ //used for drawing the circles and to allow for rotation in positive direction
        if(v.beat.isKick()){
          cRange = 2000;
        }
        else{
          cRange = 3000;
        }
        diameter = v.getSmoothedAmplitude() * 600; //diametre of ellipse linked to song
        float c = PApplet.map(i,0, v.getAudioBuffer().size() , 0, cRange);
        c = v.hueShift(c);

        v.fill(c,100,100);
        v.scale(0.95f); //0.95-og //0.98 // 0.5 //0.8
        v.rotate(AudioGarden.radians(angle)/2);// /2
        v.ellipse(x, 0, diameter, diameter); //first two x,y second width height
        
      }
      v.popMatrix();
      
      v.pushMatrix();
      for (int i=0; i <num; i++){ //used for drawing the circles and to allow for rotation in negative direction
        
        if(v.beat.isKick()){ //changes colour range if a kick drum is detected in the song
          cRange = 2000;
        }
        else{
          cRange = 3000;
        }
          diameter = v.getSmoothedAmplitude() * 600;
          float c = PApplet.map(i, 0, v.getAudioBuffer().size() , 0, cRange);
          v.fill(c,150,250);
          v.scale(0.95f); //0.95-og //.98 // 0.5f //0.8
          v.rotate(-AudioGarden.radians(angle)/2); //reverses angle of rotation
          v.ellipse(x, 0, diameter, diameter); //first two x,y second width height
        
      }
      v.popMatrix();
       
    }

    angle+=(PApplet.map(v.getSmoothedAmplitude(), 0, 1.0f, 0, 1f)); //maps the rotation speed to the smoothedAmplitude
      
  }

    
    
}
