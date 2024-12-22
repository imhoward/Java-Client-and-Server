package JavaServer.config;

public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {

    }

    public static ConfigurationManager getInstance() {
        if (myConfigurationManager == null) {
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }

    /**
     *
     * @param filePath is the path to the config file to load
     */
    public void loadConfigurationFile(String filePath) {

    }

    /**
     * Returns the current loaded config
     */
    public void getCurrentConfiguration() {

    }
}
