import silicon.core as sc
from silicon.core import ComponentExpression as Expr
from silicon.components.generic import NPTwoTerminalComponent, TTCLabelPosition
from silicon.components.utils import __draw__, __label__
from silicon.units import Amount
from typing import Literal


class Switch(NPTwoTerminalComponent):
    def __init__(self, *, start: sc.NodeCoordinates, end: sc.NodeCoordinates, closed: bool = False,
                 name: str | None = None, label_pos: TTCLabelPosition = 'default',
                 mirror: bool = False, invert: bool = False):
        super().__init__(start=start, end=end, name=name, label_pos=label_pos, mirror=mirror, invert=invert)
        self.closed: bool = closed
        self.lever = self.north

    def __render__(self) -> str:
        expr = Expr('to', f'n{'c' if self.closed else 'o'}s', __label__(name=self.name, label_pos=self.label_pos),
                    mirror=self.mirror, invert=self.invert, n=self.tikz_id())
        return __draw__(self.start, self.end, expr)


class Resistor(NPTwoTerminalComponent):
    def __init__(self, *, start: sc.NodeCoordinates, end: sc.NodeCoordinates, resistance: Amount | None = None,
                 name: str | None = None, label_pos: TTCLabelPosition = 'default', mirror: bool = False):
        super().__init__(start=start, end=end, name=name, label_pos=label_pos, mirror=mirror)
        self.resistance: Amount | None = resistance

    def __render__(self) -> str:
        expr = Expr('to', 'R', __label__(name=self.name, value=self.resistance, label_pos=self.label_pos),
                    mirror=self.mirror)
        return __draw__(self.start, self.end, expr)


PotentiometerAnchor = Literal['left', 'right', 'wiper']


class Potentiometer(sc.Component):
    def __init__(self, position: sc.NodeCoordinates, resistance: Amount | None = None, name: str | None = None,
                 anchor: sc.Direction | PotentiometerAnchor = 'wiper', rotation: int = 0):
        super().__init__(name=name)
        self.position = sc.__to_node__(position)
        self.resistance = resistance
        self.anchor = anchor
        self.rotation = rotation
        self.left = sc.SymbolicNode(f'{self.tikz_id()}.left')
        self.right = sc.SymbolicNode(f'{self.tikz_id()}.right')
        self.wiper = sc.SymbolicNode(f'{self.tikz_id()}.W')

    def __render__(self) -> str:
        expr = Expr('node', 'potentiometershape', rotate=f'{self.rotation}')
        expr.add(anchor=self.anchor[0].upper() if self.anchor == 'wiper' else self.anchor)
        label_expr = Expr('to', 'R', __label__(name=self.name, value=self.resistance, label_pos='alternative'),
                          color='transparent')
        return (f'\\draw {expr.build()}({self.tikz_id()}) at {self.position.to_tikz()} {{}};'
                f'{__draw__(self.left, self.right, label_expr)}')
