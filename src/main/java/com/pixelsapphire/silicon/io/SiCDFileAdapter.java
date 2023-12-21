package com.pixelsapphire.silicon.io;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SiCDFileAdapter implements SiCDSourceProvider {

    private final File file;

    public SiCDFileAdapter(@NotNull String path) {
        this(new File(path));
    }

    public SiCDFileAdapter(@NotNull File path) {
        this.file = path;
    }

    public @NotNull File getPath() {
        return file.getAbsoluteFile();
    }

    @Override
    public @NotNull String getSource() {
        try {
            final Scanner fileScanner = new Scanner(file);
            final StringBuilder builder = new StringBuilder();
            while (fileScanner.hasNextLine()) builder.append(fileScanner.nextLine()).append('\n');
            return builder.toString();
        } catch (FileNotFoundException e) {
            throw new SiliconIOException(e);
        }
    }

    @Override
    public @NotNull FileCoordinates getCoordinates(int characterIndex) {
        final var source = getSource();
        int line = 1, column = 1;
        for (int i = 0; i < characterIndex; i++) {
            if (source.charAt(i) == '\n') {
                line++;
                column = 1;
            } else column++;
        }
        return new FileCoordinates(getPath().toString(), line, column);
    }
}
