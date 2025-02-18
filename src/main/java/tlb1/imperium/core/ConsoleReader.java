package tlb1.imperium.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static tlb1.imperium.core.ConsoleOut.Dialogue.ANSI_RED;
import static tlb1.imperium.core.ConsoleOut.Dialogue.formatColor;

public class ConsoleReader implements Runnable {
    public static BufferedReader reader;

    @Override
    public void run() {
        String command = "";
        reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                Thread.sleep(20);
                command = reader.readLine();
                if (!Objects.equals(command.strip(), "")) Commands.query(command);


            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                System.out.println(formatColor(e.toString(), ANSI_RED));
            }
        }
    }

}