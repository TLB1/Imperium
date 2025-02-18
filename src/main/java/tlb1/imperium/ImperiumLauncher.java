package tlb1.imperium;

import tlb1.imperium.core.*;

public class ImperiumLauncher {
    public static void main(String[] arguments) {
        ImperiumService.execute(new ConsoleReader());
        ImperiumService.execute(new CommandRegistrar());
        ImperiumService.execute(new ModLoader());
        ConsoleOut.Dialogue.clearScreen();
        ConsoleOut.Dialogue.onStartUp();
    }

    public ImperiumLauncher(){
        main(new String[0]);
    }
} 