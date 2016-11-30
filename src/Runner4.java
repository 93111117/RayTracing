import Lib.StdDraw;
import Lib.StdRandom;

/**
 * Created by L i o n on 11/26/2016.
 */
public class Runner4 {
    public static int N=800000;
    static int K=50;
    public static double eta=0.2;
    public static double eps=0.0000001;
    public static void main(String[] args){
        World w=new World();
        double[] count=new double[N+1];
        double[] xout=new double[N+1];
        double min=10000000000000000.0, max=0.0;
        double domain=w.D;
        for(int i=0;i<=N;i++){
            double xin= StdRandom.uniform(-w.D,w.D);
            if(Math.abs(xin)<eps) continue;
            Ray r=new Ray(xin,w);
            r.trace();
            //count[i]=(Math.pow(eta,r.bcount));
            count[i]=(Math.pow(eta,r.bcount-1))*Math.abs(r.a/Math.sqrt(1.0+r.a*r.a));
            //count[i]=1.0;
            if(r.bcount==r.CUTTRACE) count[i]=-1.0;
            xout[i]=r.xin;
            //System.out.println(xout[i]);

        }
        double[] pwrbuck=new double[K];
        double sum=0.0;
        for(int i=0;i<=N;i++){
            if(count[i]<0) continue;
            sum+=count[i];
            int cnt=((int)((xout[i]+w.D)*(((double)K)/2.0))+K)%K;
            pwrbuck[cnt]+=count[i];
            pwrbuck[K-1-cnt]+=count[i];
            if(pwrbuck[cnt]>max) max=pwrbuck[cnt];

        }
        for(int i=0;i<K;i++){
            if(pwrbuck[i]<min) min=pwrbuck[i];

        }
        System.out.println("MINMAX "+min+"  "+max);
        System.out.println(sum/(double)N);

        StdDraw.setXscale(0.0,(double)K);
        StdDraw.setYscale((double)min,(double)max);
        for(int i=0;i<K-1;i++){
            StdDraw.line((double)i,(double)pwrbuck[i],(double)(i+1),(double)pwrbuck[i+1]);
        }
        System.out.print(max);
    }
}
