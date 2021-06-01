package primitives;


/**
 * The class represent the material of the object
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Material {

    /**
     * The diffuse attenuation factor of the object material type
     */
    public double kD = 0;

    /**
     * The specular attenuation factor of the object material type
     */
    public double kS = 0;

    /**
     * The shininess factor of the object material type
     */
    public int nShininess = 0;

    /**
     * The transparency attenuation factor of the object material type
     */
    public double _kT = 0.0;

    /**
     * The refraction attenuation factor of the object material type
     */
    public double _kR = 0.0;

    /**
     * Set the diffuse attenuation factor
     *
     * @param kD Attenuation factor
     * @return this (Material)
     */
    public Material setKd(double kD) {
        this.kD = kD;

        // return this for chaining
        return this;
    }

    /**
     * Set the specular attenuation factor
     *
     * @param kS Attenuation factor
     * @return this (Material)
     */
    public Material setKs(double kS) {
        this.kS = kS;

        // return this for chaining
        return this;
    }

    /**
     * Set the shininess factor of the material
     *
     * @param nShininess shininess factor of the material (int)
     * @return this (Material)
     */
    public Material setNShininess(int nShininess) {
        this.nShininess = nShininess;

        // return this for chaining
        return this;
    }

    /**
     * Set the Transparency attenuation factor
     *
     * @param kT Transparency factor
     * @return this (Material)
     */
    public Material setKt(double kT) {
        _kT = kT;

        // return this for chaining
        return this;
    }

    /**
     * Set the refraction attenuation factor
     *
     * @param kR refraction factor
     * @return this (Material)
     */
    public Material setKr(double kR) {
        _kR = kR;

        // return this for chaining
        return this;
    }
}
