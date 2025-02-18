package tlb1.imperium.core;

import tlb1.imperium.util.Multimap;

import java.util.*;

import static tlb1.imperium.core.ConsoleOut.Dialogue.*;

public class Commands {
    private static final List<Command> commands = new ArrayList<>();
    private static final Multimap<String, Command> commandTags = new Multimap<>();
    private static final Set<String> commandNames = new HashSet<>();

    public static void register(Command command){
        if(commandNames.contains(command.getName())) throw new IllegalStateException("Command with name %s already exists".formatted(command.getName()));
        commandNames.add(command.getName());
        commandTags.put(command.getCommandTag(), command);
        commands.add(command);
    }
    public static List<Command> getCommandsByTag(String tag){
        return List.copyOf(commandTags.get(tag));
    }
    public static boolean tagExists(String tag){
        return commandTags.containsKey(tag);
    }

    public static void update() {
        commands.forEach(Command::update);
    }

    public static String format(Command c) {
        return formatColor(c.getName(), ANSI_BLUE) + formatColor(formatParameters(c.getParameters()), ANSI_CYAN);
    }

    public static void log(Command c) {
        System.out.println(format(c));
    }

    public static void query(String command) {
        String finalCommand = command.strip().toLowerCase();
        if (finalCommand.indexOf(' ') < 0) {
            for (Command c : commands) {
                if (!Objects.equals(c.getName(), finalCommand)) continue;
                if (c.getParameterCount() > 0) {
                    ConsoleOut.Dialogue.commandParameterError(c);
                    return;
                }
                runCommand(finalCommand);
                return;
            }
        }
        String[] splitCommand = finalCommand.split(" ");
        for (Command c : commands) {
            if (!Objects.equals(splitCommand[0], c.getName())) continue;
            if (c.getParameterCount() > 0){{
                if(splitCommand.length - 1 != c.getParameterCount()){
                    commandParameterError(c);
                    return;
                }
                runCommandWithParameters(splitCommand);
            }}
            else {
                commandParameterNotNeededError();
                runCommand(splitCommand[0]);
            }
            return;
        }
        commandNotFoundError();
    }

    public static List<String> getTags(){
        return new ArrayList<>(commandTags.keySet());
    }

    private static void runCommand(String command) {
        for (Command c : commands) {
            if (Objects.equals(c.getName(), command)) c.run(new String[0]);
        }
    }

    private static void runCommandWithParameters(String[] commandWithParameters) {
        String command = commandWithParameters[0];
        String[] args = new String[commandWithParameters.length - 1];
        System.arraycopy(commandWithParameters, 1, args, 0, commandWithParameters.length - 1);

        for (Command c : commands) {
            if (!Objects.equals(c.getName(), command)) continue;
            c.run(args);
            break;
        }
    }

    private static String formatParameter(String parameter) {
        return " <" + parameter + ">";
    }

    private static String formatParameters(String[] parameters) {
        if (parameters == null) return "";
        StringBuilder out = new StringBuilder();
        for (String parameter : parameters) {
            out.append(formatParameter(parameter));
        }
        return out.toString();
    }
}
