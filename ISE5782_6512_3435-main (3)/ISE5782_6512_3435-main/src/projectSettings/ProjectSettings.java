package projectSettings;

/**
 * class for initializing the settings of the resolution and MT for the project.
 */
public class ProjectSettings {
    /**
     * multiThreading -> true/false
     */
    public static Boolean multiThreading = true;
    /**
     * SuperSampling -> true/false
     */
    public static Boolean SuperSampling = true;
    /**
     * resolution of super sampling. 9 is relatively ok. 17 is good quality but a bit slow.
     * set to 1 if you don't want super sampling.
     */
    public static int superSamplingResolution = 7;
    /**
     * the threads count
     */
    public static int threadsInterval = 1;
}
