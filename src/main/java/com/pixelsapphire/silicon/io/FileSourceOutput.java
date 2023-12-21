package com.pixelsapphire.silicon.io;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;

public class FileSourceOutput implements SourceOutput {

    private final File file;

    public FileSourceOutput(@NotNull File file) {
        this.file = file;
    }

    public FileSourceOutput(@NotNull String path) {
        this(new File(path));
    }

    @Override
    public void write(@NotNull String source) {
        try (final FileOutputStream stream = new FileOutputStream(file)) {
            stream.write(source.getBytes());
        } catch (Exception e) {
            throw new SiliconIOException(e);
        }
    }
}
