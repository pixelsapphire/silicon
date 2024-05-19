import silicon.core as sc
from silicon.core import ComponentExpression as Expr
from typing import Literal, get_args

BJTAnchor = Literal['base', 'collector', 'emitter']
BJTTransistorType = Literal['npn', 'pnp']


class BJTTransistor(sc.Component):
    def __init__(self, t: BJTTransistorType, position: sc.NodeCoordinates, name: str | None = None,
                 anchor: sc.Direction | BJTAnchor = 'center'):
        super().__init__(name=name)
        self.t = t
        self.position = sc.__to_node__(position)
        self.anchor = anchor
        self.base = sc.SymbolicNode(f'{self.tikz_id()}.B')
        self.collector = sc.SymbolicNode(f'{self.tikz_id()}.C')
        self.emitter = sc.SymbolicNode(f'{self.tikz_id()}.E')

    def __render__(self) -> str:
        expr = Expr('node', self.t)
        expr.add(anchor=self.anchor[0].upper() if self.anchor in get_args(BJTAnchor) else self.anchor)
        return f'\\draw {expr.build()}({self.tikz_id()}) at {self.position.to_tikz()} {{}};'


class NPNTransistor(BJTTransistor):
    def __init__(self, position: sc.NodeCoordinates, name: str | None = None,
                 anchor: sc.Direction | BJTAnchor = 'center'):
        super().__init__(name=name, t='npn', position=position, anchor=anchor)


class PNPTransistor(BJTTransistor):
    def __init__(self, position: sc.NodeCoordinates, name: str | None = None,
                 anchor: sc.Direction | BJTAnchor = 'center'):
        super().__init__(name=name, t='pnp', position=position, anchor=anchor)
