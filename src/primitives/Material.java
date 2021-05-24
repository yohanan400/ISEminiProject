package primitives;


/**
 * The class represent the material of the object
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Material {
    public double kD = 0, kS = 0;
    public int nShininess = 0;

    /**
     * Transparency factor
     */
    public double _kT = 0.0;

    /**
     * refraction factor
     */
    public double _kR = 0.0;

    /**
     * Attenuation factor of diffuse
     *
     * @param kD Attenuation factor
     * @return this
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Attenuation factor
     *
     * @param kS Attenuation factor
     * @return this
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * set the shininess factor of the material
     *
     * @param nShininess shininess factor of the material (int)
     * @return the material (Material)
     */
    public Material setNShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
