package tlb1.imperium.core;

public class ConsoleOut {


    public static class Dialogue {
        public static final String LINE_BREAK = "--------------------";
        public static final String LINE_BREAK_DANGER = "********************";

        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_PURPLE = "\u001B[35m";
        public static final String ANSI_RED = "\u001B[31m";


        public static void onStartUp() {
            println(ANSI_PURPLE + LINE_BREAK);
            println("Welcome to Imperium!");
            println(LINE_BREAK + ANSI_RESET);
            println("Type " + formatColor("help", ANSI_CYAN) + " for more info");
        }

        public static void commandParameterError(Command command) {
            println(formatColor("!: Command " + command.getName() + " requires " + command.getParameterCount() + " parameter", ANSI_RED));
            println("USAGE: " + Commands.format(command));
        }

        public static void commandNotFoundError() {
            println(formatColor("That command is not valid, try running ", ANSI_RED) + formatColor("help", ANSI_BLUE));
        }

        public static void commandParameterNotNeededError() {
            println(formatColor("That command doesn't require any parameters: ", ANSI_RED) + formatColor("running anyway", ANSI_BLUE));
        }

        public static void println(String text) {
            System.out.println(text);
        }

        public static String formatColor(String text, String color) {
            return color + text + ANSI_RESET;
        }

        public static void clearScreen() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
}
