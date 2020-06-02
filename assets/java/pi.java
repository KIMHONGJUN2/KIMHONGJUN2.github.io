import static java.lang.Math.max;
import static java.lang.Math.random;

public class pi {
    public static void main(String[] args) {

        float r = 200;
        int total = 0;
        int circle = 0;
        double recordPI = 0;
        for (int i = 0; i < 10000; i++) {

            double x = Math.random() * 200;
            double y = Math.random() * 200;
            total++;
            double d = x * x + y * y;
            if (d < (double) r * (double) r) {
                circle++;
            }

            double pi = (double) 4 * ((double) circle / (double) total);
            double recordDiff = Math.abs(Math.PI - recordPI);
            double diff = Math.abs(Math.PI - pi);
            if (diff < recordDiff) {
                recordDiff = diff;
                recordPI = pi;
                System.out.println(recordPI);
            }
        }
    }
}

