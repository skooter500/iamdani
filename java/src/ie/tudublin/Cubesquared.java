package ie.tudublin;

import processing.core.PApplet;

public class Cubesquared extends Poly{
    public Cubesquared (AudioGarden v){
        super(v);
    }

    
    int off_max = 450;
    int yo,xo,zo;
    //Minim minim;
    int beatcount =0;
    @Override
    public void render() {
        
        
      
       v.colorMode(PApplet.RGB); 
       v.background(0);
       v.translate( v.width/2, v.height/2, -off_max*1.3f);
       v.rotateY(v.frameCount*0.01f);
       v.rotateX(v.frameCount*0.01f);
       v.rotateZ(v.frameCount*0.01f);
       //v.rotateY(v.frameCount*v.getSmoothedAmplitude()/100);
       //v.rotateX(v.frameCount*v.getSmoothedAmplitude()/100);
       //v.rotateZ(v.frameCount*v.getSmoothedAmplitude()/100);
       /*v.getBeat().detect(v.getAudioBuffer());
       if(v.getBeat().isOnset()){
        beatcount++;
        System.out.println(beatcount);
       }*/
       //int BeatType=beatcount%2;
       
       

      
       for (xo = -off_max;xo<=off_max ; xo +=50) {
        for(yo = -off_max;yo<=off_max; yo +=50){
            for(zo=-off_max;zo<=off_max;zo+=50){
                v.pushMatrix();

                float scaleFactor = v.random(0.5f, 2.0f); // Random scaling factor for each cube

                //v.translate(xo,yo,zo);

                /*if (v.frameCount % 90 < 30) {
                    v.translate(xo, yo, zo);
                } else if (v.frameCount % 90 < 60) {
                    v.translate(xo * v.getSmoothedAmplitude() * 35, yo * v.getSmoothedAmplitude() * 35, zo * v.getAmplitude() + 35);
                } else {
                    v.translate(xo * v.getSmoothedAmplitude() * 70, yo * v.getSmoothedAmplitude() * 70, zo * v.getAmplitude() + 70);
                }
                if(v.frameCount%90>=30){
                    v.translate(xo,yo,zo);
                }
                if(v.frameCount%90<=30){
                    v.translate(xo * v.getSmoothedAmplitude() * 35, yo * v.getSmoothedAmplitude() * 35, zo * v.getAmplitude() + 35);
                }
                if(v.frameCount%90<=60){
                    v.translate(xo * v.getSmoothedAmplitude() * 70, yo * v.getSmoothedAmplitude() * 70, zo * v.getAmplitude() + 70);
                }*/
                
                
                /*if(v.frameCount%60<=30){
                    v.translate(xo, yo , zo);
                    //v.translate(xo*scaleFactor, yo*scaleFactor , zo*scaleFactor);

                }*/ 
                if(v.frameCount%45 <=22.5F){
                    v.translate(xo*v.getSmoothedAmplitude()*35, yo*v.getSmoothedAmplitude()*35 , zo*v.getAmplitude()+35);
                }
                else{
                    v.translate(xo*v.getSmoothedAmplitude()*70, yo*v.getSmoothedAmplitude()*70 , zo*v.getAmplitude()+70);
                }
                
                //v.translate(xo, yo , zo);
                v.rotateY(v.frameCount*0.4f);
                v.rotateX(v.frameCount*0.2f);
                v.rotateZ(v.frameCount*0.1f);
            
                float c = PApplet.map(zo, 0, v.getAudioBuffer().size(),0,150);
                if(v.frameCount%30 <= 15){
                    v.stroke(zo, yo, xo, off_max);
                    v.strokeWeight(5);
                }
                else{
                    v.stroke(0);
                    v.strokeWeight(5);
                    v.fill(zo,yo,xo,off_max);
                    //v.fill(c,yo,xo,off_max);
                    //v.fill(v.getSmoothedAmplitude(),v.getSmoothedAmplitude(),v.getSmoothedAmplitude());
                }

                //v.fill(c,255,255);
                //v.scale(scaleFactor); 
                v.box(v.getSmoothedAmplitude()*200);
                //v.sphere(v.getSmoothedAmplitude());;
                v.popMatrix();
            }
        }
       }
       
    }
}
