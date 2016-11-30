import Lib.StdDraw;

/**
 * Created by L i o n on 11/26/2016.
 */
public class Runner2 {
    public static int N=400;
    public static double eps=0.00000000001;
    public static void main(String[] args){
        World w=new World();
        int[] count=new int[N+1];
        int min=100000000,max=0;
        double domain=w.D/1000000.0;
        for(int i=0;i<=N;i++){
            double xin=-domain+(double)i*(domain*2.0/(double)N)+eps;
            Ray r=new Ray(xin,w);
            r.trace();
            count[i]=r.bcount;
            if(count[i]>max) max=count[i];
            if(count[i]<min) min=count[i];

        }
        StdDraw.setXscale(0.0,(double)N);
        StdDraw.setYscale((double)min,(double)max);
        for(int i=0;i<N;i++){
            StdDraw.line((double)i,(double)count[i],(double)(i+1),(double)count[i+1]);
        }
        System.out.print(max);
    }
}
