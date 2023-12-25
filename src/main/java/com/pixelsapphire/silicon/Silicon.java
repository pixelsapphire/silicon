package com.pixelsapphire.silicon;

import com.pixelsapphire.silicon.io.FileSourceOutput;
import com.pixelsapphire.silicon.io.SiCDFileAdapter;
import com.pixelsapphire.silicon.io.SiCDSourceProvider;
import com.pixelsapphire.silicon.io.SourceOutput;
import com.pixelsapphire.silicon.sicd.CompilationTask;

public class Silicon {

    public static void main(String[] args) {
        final SiCDSourceProvider input = new SiCDFileAdapter("samples/sensor.sicd");
        // final SourceOutput output = System.out::println;
        final SourceOutput output = new FileSourceOutput("samples/sensor.tex");
        new CompilationTask(input, output).run();
    }
}