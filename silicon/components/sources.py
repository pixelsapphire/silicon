from silicon.core import ComponentExpression as Expr, NodeCoordinates
from silicon.components.generic import PTwoTerminalComponent, TTCLabelPosition
from silicon.components.utils import __draw__, __label__
from silicon.units import Amount


class VoltageSource(PTwoTerminalComponent):
    def __init__(self, *, positive: NodeCoordinates, negative: NodeCoordinates, voltage: Amount | None = None,
                 name: str | None = None, label_pos: TTCLabelPosition = 'default'):
        super().__init__(positive=positive, negative=negative, name=name, label_pos=label_pos)
        self.voltage: Amount | None = voltage

    def __render__(self) -> str:
        params = Expr('to', 'battery1', __label__(name=self.name, value=self.voltage, label_pos=self.label_pos))
        return __draw__(self.positive, self.negative, params)
