from abc import ABC
import silicon.core as sc
from typing import Literal, Type

TTCLabelPosition: Type[str] = Literal['default', 'alternative']


class OneTerminalComponent(sc.Component, ABC):
    def __init__(self, *, name: str | None = None, position: sc.NodeCoordinates):
        super().__init__(name=name)
        self.position: sc.Node = sc.__to_node__(position)

    def __add__(self, other: sc.NodeCoordinates):
        return self.position + other

    def __radd__(self, other):
        return self.__add__(other)


class TwoTerminalComponent(sc.Component, ABC):
    def __init__(self, *, name: str | None = None, label_pos: TTCLabelPosition = 'default',
                 mirror: bool = False, invert: bool = False):
        super().__init__(name=name)
        self.label_pos: TTCLabelPosition = label_pos
        self.mirror: bool = mirror
        self.invert: bool = invert
        self.north = sc.SymbolicNode(f'{self.tikz_id()}.north')
        self.northeast = sc.SymbolicNode(f'{self.tikz_id()}.north east')
        self.east = sc.SymbolicNode(f'{self.tikz_id()}.east')
        self.southeast = sc.SymbolicNode(f'{self.tikz_id()}.south east')
        self.south = sc.SymbolicNode(f'{self.tikz_id()}.south')
        self.southwest = sc.SymbolicNode(f'{self.tikz_id()}.south west')
        self.west = sc.SymbolicNode(f'{self.tikz_id()}.west')
        self.northwest = sc.SymbolicNode(f'{self.tikz_id()}.north west')
        self.center = sc.SymbolicNode(f'{self.tikz_id()}.center')


class NPTwoTerminalComponent(TwoTerminalComponent, ABC):
    def __init__(self, *, start: sc.NodeCoordinates, end: sc.NodeCoordinates, name: str | None = None,
                 label_pos: TTCLabelPosition = 'default', mirror: bool = False, invert: bool = False):
        super().__init__(name=name, label_pos=label_pos, mirror=mirror, invert=invert)
        self.start: sc.Node = sc.__to_node__(start)
        self.end: sc.Node = sc.__to_node__(end)


class PTwoTerminalComponent(TwoTerminalComponent, ABC):
    def __init__(self, *, positive: sc.NodeCoordinates, negative: sc.NodeCoordinates, name: str | None = None,
                 label_pos: TTCLabelPosition = 'default', mirror: bool = False, invert: bool = False):
        super().__init__(name=name, label_pos=label_pos, mirror=mirror, invert=invert)
        self.positive: sc.Node = sc.__to_node__(positive)
        self.negative: sc.Node = sc.__to_node__(negative)
