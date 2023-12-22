package com.pixelsapphire.silicon.test;

import com.pixelsapphire.silicon.util.TextTransform;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTransformTest {

    @Test
    void testHex() {
        final var hex = TextTransform.encodeAsHexBytes("Hello, World!");
        assertEquals("48656c6c6f2c20576f726c6421", hex);
    }

    @Test
    void testPsseudoHex() {
        final var hex = TextTransform.encodeAsPseudoHexBytes("Hello, World!");
        assertEquals("EIGFGcGcGfCcCAFHGfHCGcGECB", hex);
    }
}
