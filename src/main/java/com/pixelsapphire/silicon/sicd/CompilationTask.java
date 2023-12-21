package com.pixelsapphire.silicon.sicd;

import com.pixelsapphire.silicon.io.SiCDSourceProvider;
import com.pixelsapphire.silicon.io.SiliconSourceException;
import com.pixelsapphire.silicon.io.SourceOutput;
import com.pixelsapphire.silicon.latex.LaTeXCommand;
import com.pixelsapphire.silicon.latex.LaTeXEnvironment;
import com.pixelsapphire.silicon.latex.LaTeXSequence;
import com.pixelsapphire.silicon.latex.RawLaTeX;
import com.pixelsapphire.silicon.sicd.compiler.SiCDCompiler;
import com.pixelsapphire.silicon.sicd.lexer.SiCDLexer;
import com.pixelsapphire.silicon.sicd.parser.SiCDParser;

public class CompilationTask {

    private static final LaTeXEnvironment defaultTiKZPicture = new LaTeXEnvironment("tikzpicture")
            .withArgument("/tikz/circuitikz/bipoles/length", "0.5in")
            .withArgument("/tikz/circuitikz/multipoles/dipchip/width", "1.4")
            .withArgument("line width", "0.75pt");

    private final SiCDSourceProvider sourceProvider;
    private final SourceOutput output;

    public CompilationTask(SiCDSourceProvider sourceProvider, SourceOutput output) {
        this.sourceProvider = sourceProvider;
        this.output = output;
    }

    public void run() {
        try {
            final SiCDLexer lexer = new SiCDLexer(sourceProvider);
            final var tokens = lexer.tokenize();

            final SiCDParser parser = new SiCDParser(tokens);
            final var circuitGraph = parser.parse();

            final SiCDCompiler compiler = new SiCDCompiler(circuitGraph);
            final var compiled = new RawLaTeX(compiler.compile());

            final var picture = LaTeXSequence.of("\n", defaultTiKZPicture.append(compiled), "\n")
                                             .wrapWith(new LaTeXCommand("scalebox").withRequiredArgument("1"));
            final var figure = LaTeXSequence.of(new LaTeXCommand("centering"), picture)
                                            .wrapWith(new LaTeXEnvironment("figure").withOption("tbh!"));
            output.write(figure.translate());

        } catch (final SiliconSourceException error) {
            System.err.println(error.getMessage());
        }
    }
}
