import Lib.StdDraw;

import java.awt.*;

/**
 * Created by L i o n on 11/26/2016.
 */
public class Runner {
    public static int N = 400;

    public static void main(String[] args) {
        World w = new World();
        Ray r=new Ray(0.1,w);
        for(int i=0;i<400;i++){
            if(i%3==0)StdDraw.setPenColor(Color.RED);
            if(i%3==1)StdDraw.setPenColor(Color.BLUE);
            if(i%3==2)StdDraw.setPenColor(Color.GREEN);
            r.singleTrace();
            if(r.out){
                System.out.println("COUNT IS "+r.bcount);
                break;
            }
        }
//        r=new Ray(-0.1,w);
//        for(int i=0;i<100;i++){
//            if(i%3==0)StdDraw.setPenColor(Color.RED);
//            if(i%3==1)StdDraw.setPenColor(Color.BLUE);
//            if(i%3==2)StdDraw.setPenColor(Color.GREEN);
//            r.singleTrace();
//            if(r.out){
//                System.out.println("COUNT IS "+r.bcount);
//                break;
//            }
//        }

    }
}
