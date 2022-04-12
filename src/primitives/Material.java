package primitives;

public class Material {
    public double kD = 0, kS = 0;
    public int nShininess = 0;
    public double kT = 0, kR = 0;

    public Material setKD(double kD) {
        this.kD = kD;
        return this;
    }

    public Material setKS(double kS) {
        this.kS = kS;
        return this;
    }

    public Material setKT(double kT) {
        this.kT = kT;
        return this;
    }

    public Material setKR(double kR) {
        this.kR = kR;
        return this;
    }

    public Material setNShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

}
