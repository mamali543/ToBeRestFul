import java.util.Random;

public class EQUA2D implements Comparable<EQUA2D>{
    double a;
    double b;
    double c;

    public EQUA2D()
    {
        Random rand = new Random();
        this.a = rand.nextDouble() * (130 - 30) + 30;
        this.b = rand.nextDouble() * (55 - 25) + 25;
        this.c = rand.nextDouble() * (190 - 19) + 19;
    }

    public int compareTo(EQUA2D e)
    {
        if(this.a > e.a)
        {
            return 1;
        }
        else if(this.a < e.a)
        {
            return -1;
        }
        return 0;
    }

}
