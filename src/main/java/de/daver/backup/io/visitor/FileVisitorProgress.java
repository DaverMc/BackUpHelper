package de.daver.backup.io.visitor;

import de.daver.backup.LoggingHelper;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class FileVisitorProgress {

    private final AtomicInteger max;
    private final AtomicInteger current;

    public FileVisitorProgress(Path sourceRoot, Predicate<Path> filter) {
        this.max = new AtomicInteger(countFilesToProcess(sourceRoot, filter));
        this.current = new AtomicInteger(0);
    }

    private static int countFilesToProcess(Path source, Predicate<Path> filter) {
        var countVisitor = new CountFileVisitor(filter);
        try {
            Files.walkFileTree(source, countVisitor);
        } catch (IOException e) {
            LoggingHelper.error(e);
        }
        return countVisitor.count.get();
    }

    public int step() {
        int curr = current.incrementAndGet();
        int total = max.get();

        if(curr % 100 == 0) {
            float progress = (float) curr / (float) total;
            LoggingHelper.info("Processed files: %.2f", progress);
        }
        return curr;
    }

    private static class CountFileVisitor extends SimpleFileVisitor<Path> {

        private final Predicate<Path> filter;
        private final AtomicInteger count;

        public CountFileVisitor(Predicate<Path> filter) {
            this.filter = filter;
            this.count = new AtomicInteger(0);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if(filter.test(file)) count.incrementAndGet();
            return FileVisitResult.CONTINUE;
        }
    }
}
