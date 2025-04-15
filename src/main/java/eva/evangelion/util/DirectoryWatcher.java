package eva.evangelion.util;

import java.nio.file.*;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

public class DirectoryWatcher implements Runnable {
    private final Path directory;
    private final String targetFileName;
    private final Runnable callback;

    public DirectoryWatcher(Path directory, String targetFileName, Runnable callback) {
        this.directory = directory;
        this.targetFileName = targetFileName;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            directory.register(watchService,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_CREATE);

            // Add 1-second cooldown to prevent multiple triggers
            final long COOLDOWN_MS = 1000;
            long lastTrigger = 0;

            while (!Thread.currentThread().isInterrupted()) {
                WatchKey key = watchService.take();

                for (WatchEvent<?> event : key.pollEvents()) {
                    Path changedFile = (Path) event.context();

                    if (changedFile.toString().equals(targetFileName)) {
                        long now = System.currentTimeMillis();

                        if (now - lastTrigger > COOLDOWN_MS) {
                            lastTrigger = now;
                            // Add initial delay before triggering
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Platform.runLater(callback);
                                }
                            }, 500); // 500ms delay to allow file write completion
                        }
                    }
                }
                key.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}