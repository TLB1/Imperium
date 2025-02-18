package tlb1.imperium.core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Properties;

import static tlb1.imperium.core.ConsoleOut.Dialogue.ANSI_RED;
import static tlb1.imperium.core.ConsoleOut.Dialogue.formatColor;

public class Mod {
    private final Properties properties;
    private final Method initialiseMethod,destroyMethod;
    private final Object instance;

    private final String name, description;

    private boolean initialised;

    public Mod(Path path, Properties properties, URLClassLoader child) throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException {
        this.properties = properties;

        Class<?> classToLoad = Class.forName(properties.getProperty("main-class"), true, child);

        initialiseMethod = classToLoad.getDeclaredMethod("onInitialise");
        destroyMethod = classToLoad.getDeclaredMethod("onDestroy");
        instance = classToLoad.getDeclaredConstructor().newInstance();
        initialised = false;
        name = properties.getProperty("name") == null ? path.toString() : properties.getProperty("name");
        description = properties.getProperty("description") == null ? "" : properties.getProperty("description");
    }

    public Properties getProperties() {
        return properties;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void initialise() {
        if (initialised) return;
        try {
            initialiseMethod.invoke(instance);
            initialised = true;
        } catch (Exception e) {
            System.out.println(formatColor("Could not initialise %s".formatted(properties.getProperty("name")), ANSI_RED));
        }
    }

    public void destroy() {
        if (!initialised) return;
        try {
            destroyMethod.invoke(instance);
            initialised = false;
        } catch (Exception e) {
            System.out.println(formatColor("Could not destroy %s".formatted(properties.getProperty("name")), ANSI_RED));
        }
    }
}
