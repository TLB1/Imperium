package tlb1.imperium.core;

import static tlb1.imperium.core.ConsoleOut.Dialogue.*;

public class CommandRegistrar implements Runnable {
    @Override
    public void run() {
        Command explore = new Command(Tags.BASIC, "explore", new String[]{"tag"},
                (args) -> {
                    if (Commands.tagExists(args[0])) {
                        System.out.println(formatColor(ConsoleOut.Dialogue.LINE_BREAK, ANSI_PURPLE));
                        System.out.println(formatColor(args[0] + " commands", ANSI_PURPLE));
                        System.out.println(formatColor(ConsoleOut.Dialogue.LINE_BREAK, ANSI_PURPLE));
                        Commands.getCommandsByTag(args[0]).forEach(Commands::log);
                    } else {
                        System.out.println(formatColor("Tag not found", ANSI_PURPLE));
                    }
                }
        );

        Command tags = new Command(Tags.BASIC, "tags",
                (args) -> {
                    System.out.println(formatColor(ConsoleOut.Dialogue.LINE_BREAK, ANSI_PURPLE));
                    System.out.println(formatColor("Tags", ANSI_PURPLE));
                    System.out.println(formatColor(ConsoleOut.Dialogue.LINE_BREAK, ANSI_PURPLE));
                    Commands.getTags().forEach(tag -> System.out.println(formatColor(tag, ANSI_BLUE)));
                }
        );


        Commands.register(new Command(Tags.BASIC, "help",
                (args) -> {
                    explore.run(new String[]{"basic"});
                    System.out.println();
                    tags.run(new String[]{});
                },
                () -> System.out.println("Help has updated")
        ));

        Commands.register(new Command(Tags.BASIC, "exit",
                (args) -> System.exit(0)
        ));

        Commands.register(new Command(Tags.BASIC, "update",
                (args) -> Commands.update()
        ));

        Commands.register(new Command(Tags.BASIC, "clear",
                (args) -> ConsoleOut.Dialogue.clearScreen()
        ));

        Commands.register(tags);

        Commands.register(explore);

        Commands.register(new Command(Tags.BASIC, "mods", (args)->{
            System.out.println(formatColor(ConsoleOut.Dialogue.LINE_BREAK, ANSI_PURPLE));
            System.out.println(formatColor("Mods", ANSI_PURPLE));
            System.out.println(formatColor(ConsoleOut.Dialogue.LINE_BREAK, ANSI_PURPLE));

            System.out.println("%s mod%s loaded".formatted(ModLoader.getMods().size(), ModLoader.getMods().size() == 1 ? "" : "s"));
            System.out.println(ConsoleOut.Dialogue.LINE_BREAK);
            ModLoader.getMods().forEach(mod -> System.out.println(formatColor((mod == null) ? "unknown" : mod.getName(), ANSI_BLUE)));
        }
                ));


    }
}
