package tlb1.imperium;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImperiumService {
    private static final ExecutorService es = Executors.newCachedThreadPool();;

    public static void execute(Runnable r){
        es.execute(r);
    }

}
