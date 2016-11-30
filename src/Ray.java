import Lib.StdDraw;

import java.awt.*;

/**
 * Created by L i o n on 11/26/2016.
 */
public class Ray {
    //Ray Eq. is y=ax+b
    World world;
    static int CUTTRACE=100;
    double a;
    double b;
    double xin,yin;
    double xinOld,yinOld;
    int bcount; //number of times Ray has bounced. useful to consider intensity attenuation.
    boolean out; //to flag if Ray has gone out.
    boolean comingFromLeft;
    public Ray(double x,World world){
        //initiate a ray coming in at x. other constructors can be added for skew rays coming in.
        this.world=world;
        out=false;
        bcount=1;
        xinOld=x;
        yinOld=0.0;
        xin=x;
        if(x>0.0) {
            double tan2t = 2.0 * world.tanTheta / (1.0 - world.tanTheta * world.tanTheta);
            a = 1 / tan2t;
            b = (world.D - x) * world.tanTheta - x * a;
            comingFromLeft=false;
            yin=(world.D-x)*world.tanTheta;
        }else{
            double tan2t = 2.0 * world.tanTheta / (1.0 - world.tanTheta * world.tanTheta);
            a = -(1 / tan2t);
            b = (world.D + x) * world.tanTheta - x * a;
            comingFromLeft=true;
            yin=(world.D+x)*world.tanTheta;
        }
    }
    public void bounceleft(){
        xinOld=xin;
        double xin_new=(world.D*world.tanTheta-b)/(a-world.tanTheta);
        //System.out.println(a);
        if(xin_new<0.0 && xin_new>(-world.D) && bcount<CUTTRACE){
            //its a hit!
            xin=xin_new;
            bcount++;
            yinOld=yin;
            yin=a*xin+b;
            yin=a*xin+b;
            if(a>=0.0 && a< world.tanTheta){

                double tanphi=((1.0-a*world.tanTheta)/(-world.tanTheta+a));
                double tanTP=(-world.tanTheta+tanphi)/(1.0-world.tanTheta*tanphi);
                double a_new=(1.0/tanTP);
                double b_new = yin - a_new * xin;
                a=a_new;
                b=b_new;
            }else if(a>=0.0 && a>=world.tanTheta){

                double tanphi=(1.0-a*world.tanTheta)/(-world.tanTheta+a);
                double tanTP=(world.tanTheta+tanphi)/(1.0+world.tanTheta*tanphi);
                double a_new=-(1.0/tanTP);
                double b_new = yin - a_new * xin;
                a=a_new;
                b=b_new;
            }else if(a<=0.0 && Math.abs(a)<(1/world.tanTheta)){
                double tanphi=(1.0-a*world.tanTheta)/(world.tanTheta-a);
                double tanTP=(-world.tanTheta+tanphi)/(1.0-world.tanTheta*tanphi);
                double a_new=-(1.0/tanTP);
                double b_new = yin - a_new * xin;
                a=a_new;
                b=b_new;
            }else{
                double tanphi=1/((1.0+a*world.tanTheta)/(world.tanTheta+a));
                double tanTP=(world.tanTheta+tanphi)/(1.0+world.tanTheta*tanphi);
                double a_new=-(1.0/tanTP);
                double b_new = yin - a_new * xin;
                a=a_new;
                b=b_new;
            }

            comingFromLeft=true;
        }else{
            //Its going out!
            out=true;
            xinOld=xin;
            yinOld=yin;
            yin=0.0;
            xin=-b/a;
            if(xin>world.D || xin<-world.D){
                //System.out.println(xin);
                if(xin<0) xin=-world.D;
                else xin=world.D;
            }
        }
    }
    public void bounceright(){
        xinOld=xin;
         double xin_new=(world.D*world.tanTheta-b)/(a+world.tanTheta);

        if(xin_new>0.0 && xin_new<(world.D) && bcount<CUTTRACE){
            //its a hit!
            xin=xin_new;
            bcount++;
            yinOld = yin;
            if(a>=0.0 && a>(1.0/world.tanTheta) ) {


                yin = a * xin + b;
                double tanphi = (world.tanTheta - a) / (1.0 - a * world.tanTheta);

                double tanTP = (world.tanTheta + tanphi) / (1.0 + world.tanTheta * tanphi);
                double a_new = (1.0 / tanTP);
                double b_new = yin - a_new * xin;

                a = a_new;
                b = b_new;
            }else if(a>0.0){

                yin = a * xin + b;
                double tanphi = 1/((world.tanTheta - a) / (1.0 - a * world.tanTheta));

                double tanTP = (world.tanTheta - tanphi) / (1.0 - world.tanTheta * tanphi);
                double a_new = (1.0 / tanTP);
                double b_new = yin - a_new * xin;

                a = a_new;
                b = b_new;
            }else if(a<=0.0 && Math.abs(a)<world.tanTheta){
                yin = a * xin + b;
                double tanphi = 1.0/((world.tanTheta + a) / (1.0 + a * world.tanTheta));

                double tanTP = (-world.tanTheta + tanphi) / (1.0 - world.tanTheta * tanphi);
                double a_new = -(1.0 / tanTP);
                double b_new = yin - a_new * xin;

                a = a_new;
                b = b_new;
            }else if(a<0.0 && Math.abs(a)>=world.tanTheta){
                //Impossible!
                System.out.println("IMPOSSIBLE!");
            }
            comingFromLeft=false;
        }else{
            //Its going out!
            out=true;
            //xinOld=xin;
            yinOld=yin;
            yin=0.0;
            xin=-b/a;
            if(xin>world.D || xin<(-world.D)){
                //System.out.println(xin);
                if(xin<0) xin=-world.D;
                else xin=world.D;
            }
        }
    }
    public void trace(){
        while(!out){
            if(comingFromLeft){
                bounceright();
            }else{
                bounceleft();
            }
        }
    }
    public void singleTrace(){
        if(bcount==1)draw();
        if(comingFromLeft){
            bounceright();
        }else{
            bounceleft();
        }

        draw();

    }
    public void draw(){
        double w=(world.D*world.tanTheta)/2.0;
        if(bcount==1){
            StdDraw.setXscale(-w,w);
            StdDraw.setYscale(0.0,world.D*world.tanTheta);
            StdDraw.setPenRadius(0.009);
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.line(-w,0.0,0.0,world.D*world.tanTheta);
            StdDraw.line(w,0.0,0.0,world.D*world.tanTheta);
            StdDraw.setPenRadius(0.002);
        }

        StdDraw.line(xinOld*(w/world.D),yinOld,xin*(w/world.D),yin);
    }
}
