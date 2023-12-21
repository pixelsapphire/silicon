package com.pixelsapphire.silicon.sicd.parser.node.literal;

import com.pixelsapphire.silicon.sicd.parser.node.Node;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListNode extends Node {

    private final List<Node> elements = new ArrayList<>();

    public void addElement(@NotNull Node element) {
        elements.add(element);
    }

    @Contract(pure = true)
    public @NotNull @UnmodifiableView List<Node> getElements() {
        return Collections.unmodifiableList(elements);
    }

    @Contract(pure = true)
    public @NotNull Node get(@Range(from = 0, to = Integer.MAX_VALUE) int index) {
        return elements.get(index);
    }

    @Override
    public @NotNull Type getType() {
        return Type.LIST;
    }

}
