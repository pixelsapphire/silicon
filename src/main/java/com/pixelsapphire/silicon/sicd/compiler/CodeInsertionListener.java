package com.pixelsapphire.silicon.sicd.compiler;

import org.jetbrains.annotations.NotNull;

public interface CodeInsertionListener {

    void insertCode(@NotNull String code);
}
