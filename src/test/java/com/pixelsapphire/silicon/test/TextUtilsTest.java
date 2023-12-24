package com.pixelsapphire.silicon.test;

import com.pixelsapphire.silicon.util.TextUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextUtilsTest {

    @Test
    void testHex() {
        final var hex = TextUtils.encodeAsHexBytes("Hello, World!");
        assertEquals("48656c6c6f2c20576f726c6421", hex);
    }

    @Test
    void testPsseudoHex() {
        final var hex = TextUtils.encodeAsPseudoHexBytes("Hello, World!");
        assertEquals("EIGFGcGcGfCcCAFHGfHCGcGECB", hex);
    }

    @Test
    void testInt2Hex() {
        var hex = TextUtils.encodeAsHexBytes(7);
        assertEquals("07", hex);
        hex = TextUtils.encodeAsHexBytes(0xF54AC8);
        assertEquals("f54ac8", hex);
    }

    @Test
    void testInt2PseudoHex() {
        var hex = TextUtils.encodeAsPseudoHexBytes(7);
        assertEquals("AH", hex);
        hex = TextUtils.encodeAsPseudoHexBytes(0xF54AC8);
        assertEquals("fFEacI", hex);
    }
}
