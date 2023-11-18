package ie.tudublin;


import processing.core.PApplet;

public class Cubesquared2 extends Poly{
    public Cubesquared2 (AudioGarden v){
        super(v);
    }

    
    int off_max = 450;
    int yo,xo,zo;
    //Minim minim;
    int beatcount =0;
    @Override
    public void render(int ellapsed) {
        

        
        
       //v.background(0);
       v.colorMode(PApplet.HSB); 
       //v.background(0);
       v.translate( v.width/2, v.height/2, -off_max*2.3f);
       v.rotateY(v.yaw * 2.0f);
       v.rotateZ(v.pit * 2.0f);       
       v.rotateY(v.frameCount*0.01f * v.spe);
       v.rotateX(v.frameCount*0.01f * v.spe);
       v.rotateZ(v.frameCount*0.01f * v.spe);
       v.strokeWeight(1);
       v.stroke(v.hueShift(90), 255, 255, v.alp);
       //v.rotateY(v.frameCount*v.getSmoothedAmplitude()/100);
       //v.rotateX(v.frameCount*v.getSmoothedAmplitude()/100);
       //v.rotateZ(v.frameCount*v.getSmoothedAmplitude()/100);
       

      
       for (xo = -off_max;xo<=off_max ; xo +=50) {//value of offmax sets how many pixels off centre of x axis
        for(yo = -off_max;yo<=off_max; yo +=50){//50 value spaces each box 50 pixels apart
            for(zo=-off_max;zo<=off_max;zo+=50){
                v.pushMatrix();

                float scaleFactor = v.random(0.5f, 2.0f) * v.spe;// scales individual cubes by a random float between 0.5 and 2.0
                
                //v.translate(xo, yo , zo+100);


                v.translate(xo*(1.5f*v.getSmoothedAmplitude()),yo*(1.5f*v.getSmoothedAmplitude()),zo*(1.5f*v.getSmoothedAmplitude()));

              
                float c = PApplet.map(zo, 0, v.getAudioBuffer().size(),0,255);
                v.rotateY(v.frameCount*0.1f);//rotates individual cubes on y axis
                v.rotateX(v.frameCount*0.1f);//rotates individual cubes on x axis 
                v.rotateZ(v.frameCount*0.1f);//rotates individual cubes on z axis

                v.fill(v.hueShift(c),(255+v.frameCount)%255,(255+v.frameCount)%255, v.alp);
                v.scale(scaleFactor); 
                v.box(v.getSmoothedAmplitude()*50);//box size changes by amplitude of song
                //v.box(20);
                //v.sphere(v.getSmoothedAmplitude());;
                v.popMatrix();
            }
        }
       }
       
    }
}