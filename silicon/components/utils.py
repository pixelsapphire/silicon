import silicon.core as sic
from silicon.components.generic import TTCLabelPosition
from silicon.core import ComponentExpression as Expr
from silicon.units import Amount


def __draw__(start: sic.Node, end: sic.Node, expr: Expr):
    return f'\\draw {start.to_tikz()} {expr.build()} {end.to_tikz()};'


def __label__(*, name: str | None = None, value: Amount | None = None,
              label_pos: TTCLabelPosition = 'default') -> tuple[str, str]:
    label = []
    if name is not None:
        label.append(name)
    if value is not None:
        label.append(str(value))
    return ('l' if label_pos == 'default' else 'l_'), (f'\\scriptsize{{{", ".join(label)}}}' if label else False)
