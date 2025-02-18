package tlb1.imperium.core;

import java.util.function.Consumer;

public class Command {
    private final String name;
    private final String[] parameters;

    private final String commandTag;

    private final Consumer<String[]> action;
    private final Runnable update;

    public Command(String commandTag, String name, String[] parameters, Consumer<String[]> action, Runnable update) {
        this.commandTag = commandTag;
        this.name = name;
        this.parameters = parameters;
        this.action = action;
        this.update = update;
    }

    public Command(String commandTag, String name, Consumer<String[]> action, Runnable update) {
        this(commandTag, name, null, action, update);
    }

    public Command(String commandTag, String name, String[] parameters, Consumer<String[]> action) {
        this(commandTag, name, parameters, action, () -> {});
    }

    public Command(String commandTag, String name, Consumer<String[]> action) {
       this(commandTag, name, null, action);
    }


    public int getParameterCount() {
        if (parameters == null) return 0;
        return parameters.length;
    }

    public String[] getParameters() {
        return parameters;
    }

    public String getCommandTag() {
        return commandTag;
    }

    public String getName() {
        return name;
    }

    public void run(String[] args) {
        action.accept(args);
    }

    public void update() {
        update.run();
    }
}
