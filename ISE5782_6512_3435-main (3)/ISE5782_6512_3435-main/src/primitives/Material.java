package primitives;

/**
 * class to represent Material on which light reflects
 */
public class Material {
    /**
     * Variable kD as Double3 obj
     */
    public Double3 kD = Double3.ZERO;

    /**
     * Variable kS as Double3 obj
     */
    public Double3 kS = Double3.ZERO;

    /**
     * Variable kT as Double3 obj, transparency coefficient
     */
    public Double3 kT = Double3.ZERO;
    /**
     * Variable kR as Double3 obj, reflection coefficient
     */

    public Double3 kR = Double3.ZERO;

    /**
     * Variable Shininess factor as int
     */
    public int nShininess = 0;

    /**
     * setter for kD
     *
     * @param kD double3
     * @return the Material itself
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }


    /**
     * setter for kD
     *
     * @param kD double
     * @return the Material itself
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * setter for transparency constant
     *
     * @param kT Double3
     * @return the material itself
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * setter for transparency constant
     *
     * @param kT double
     * @return the material itself
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * setter for reflection constant
     *
     * @param kR double
     * @return material itself
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * setter for kS
     *
     * @param kS double3
     * @return the Material itself
     */
    public Material setKs(Double3 kS) {
        this.kS = (kS);
        return this;
    }

    /**
     * setter for kS
     *
     * @param kS double
     * @return the Material itself
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * setter for shininess of material
     *
     * @param nShininess the shininess factor as int
     * @return the Material itself
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * setter for shininess of material
     *
     * @param nShininess the shininess factor as double
     * @return the Material itself
     */
    public Material setShininess(double nShininess) {
        this.nShininess = (int) nShininess;
        return this;
    }

    /**
     * setter for reflection constant
     *
     * @param kR double
     * @return the material itself
     */
    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
}
