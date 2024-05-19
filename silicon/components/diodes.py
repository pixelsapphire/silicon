import silicon.core as sc
from silicon.core import ComponentExpression as Expr
from silicon.components.generic import PTwoTerminalComponent, TTCLabelPosition
from silicon.components.utils import __draw__, __label__


class LED(PTwoTerminalComponent):
    def __init__(self, *, positive: sc.NodeCoordinates, negative: sc.NodeCoordinates,
                 name: str | None = None, label_pos: TTCLabelPosition = 'default', mirror: bool = False):
        super().__init__(positive=positive, negative=negative, name=name, label_pos=label_pos, mirror=mirror)

    def __render__(self) -> str:
        params = Expr('to', 'led', __label__(name=self.name, label_pos=self.label_pos), mirror=self.mirror)
        return __draw__(self.positive, self.negative, params)
