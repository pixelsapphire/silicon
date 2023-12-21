package com.pixelsapphire.silicon.test;

import com.pixelsapphire.silicon.latex.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LaTeXTest {

    @Test
    void testCommandNoArgs() {
        final LaTeXCommand command = new LaTeXCommand("textbackslash");
        assertEquals("\\textbackslash", command.translate());
    }

    @Test
    void testCommandOneReqArg() {
        final LaTeXCommand command = new LaTeXCommand("documentclass").withRequiredArgument("article");
        assertEquals("\\documentclass{article}", command.translate());
    }

    @Test
    void testCommandManyArgs() {
        final LaTeXCommand command = new LaTeXCommand("rotatebox")
                .withRequiredArgument("90").withRequiredArgument("Hello world!")
                .withOptionalArgument("x", "0.5").withOptionalArgument("y", "1");
        assertEquals("\\rotatebox[x=0.5,y=1]{90}{Hello world!}", command.translate());
    }

    @Test
    void testCommandNested() {
        final LaTeXCommand command = new LaTeXCommand("rotatebox")
                .withOptionalArgument("origin", "c")
                .withRequiredArgument("90")
                .withRequiredArgument(new LaTeXCommand("textbf").withRequiredArgument("Hello world!"));
        assertEquals("\\rotatebox[origin=c]{90}{\\textbf{Hello world!}}", command.translate());
    }

    @Test
    void testSequence() {
        final LaTeXSequence paragraph = new LaTeXSequence();
        paragraph.append(new LaTeXCommand("textbf").withRequiredArgument("Hello world!")).append(" This is a nice ")
                 .append(new LaTeXCommand("textit").withRequiredArgument("paragraph")).append(".");
        assertEquals("\\textbf{Hello world!} This is a nice \\textit{paragraph}.", paragraph.translate());
    }

    @Test
    void testEnvironment() {
        final LaTeXSequence paragraph = new LaTeXSequence();
        paragraph.append(new LaTeXCommand("textbf").withRequiredArgument("Hello world!")).append(" This is a nice ")
                 .append(new LaTeXCommand("textit").withRequiredArgument("paragraph")).append(".");
        final LaTeXEnvironment document = new LaTeXEnvironment("document").append(paragraph);
        assertEquals("""
                     \\begin{document}
                     \\textbf{Hello world!} This is a nice \\textit{paragraph}.
                     \\end{document}""", document.translate());
    }

    @Test
    void testMathModeInline() {
        final LaTeXMathMode math = LaTeXMathMode.inline().withContent(new LaTeXCommand("alpha"));
        assertEquals("$\\alpha$", math.translate());
    }

    @Test
    void testMathModeDisplay() {
        final LaTeXMathMode math = new LaTeXCommand("operatorname")
                .withRequiredArgument("sin")
                .withRequiredArgument(new LaTeXCommand("alpha"))
                .wrapWith(LaTeXMathMode.display());
        assertEquals("$$\\operatorname{sin}{\\alpha}$$", math.translate());
    }
}