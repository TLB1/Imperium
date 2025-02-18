package tlb1.imperium.core;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static tlb1.imperium.core.ConsoleOut.Dialogue.ANSI_RED;
import static tlb1.imperium.core.ConsoleOut.Dialogue.formatColor;

public class ModLoader implements Runnable {
    private static List<Mod> mods = new ArrayList<>();
    private static PrintStream logger;


    public static List<Mod> getMods() {
        return mods;
    }

    public static void destroy() {
        mods.forEach(Mod::destroy);
    }

    @Override
    public void run() {
        try {
            logger = new PrintStream(new FileOutputStream("mod-loader.log", true));
        } catch (IOException ignored) {

        }
        try (Stream<Path> paths = Files.walk(Paths.get("./mods"))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".jar"))
                    .forEach(this::loadMod);


        } catch (IOException e) {
            e.printStackTrace();
        }
        mods.forEach(Mod::initialise);

        if (!mods.isEmpty()) {
            System.out.printf("Loaded %s mod%s...\n\n", mods.size(), mods.size() == 1 ? "" : "s");
        }
    }

    public void loadMod(Path path) {
        try (URLClassLoader loader = new URLClassLoader(new URL[]{path.toFile().toURI().toURL()}, this.getClass().getClassLoader())) {
            Properties properties = new Properties();
            properties.load(loader.getResourceAsStream("config.properties"));

            try {
                mods.add(new Mod(path, properties, loader));

            } catch (Exception e) {
                System.out.println(formatColor("Could not load %s"
                                .formatted(properties.getProperty("name") == null ? path.getFileName().toString() : properties.getProperty("name")),
                        ANSI_RED));
                e.printStackTrace(logger);
            }

        } catch (IOException e) {
            System.out.println("Could not load mod at path: " + path);
            e.printStackTrace(logger);
        }
    }
}
