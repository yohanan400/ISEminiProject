package primitives;

public class Material {
    public double kD =0, kS =0;
    public int nShininess =0;

    /**
     *
     * @param kD
     * @return
     */
    public Material setkD(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     *
     * @param kS
     * @return
     */
    public Material setkS(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * set the shininess factor of the material
     * @param nShininess shininess factor of the material (int)
     * @return the material (Material)
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
