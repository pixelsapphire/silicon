import silicon.core as sc
from silicon.components.common import Wire, PathSpecifier
from typing import Literal, TypeAlias

LineStyle: TypeAlias = Literal['solid', 'dashed']
FontSize: TypeAlias = Literal[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

__latex_font_size__: dict[FontSize, str] = {
    1: '\\tiny',
    2: '\\scriptsize',
    3: '\\footnotesize',
    4: '\\small',
    5: '\\normalsize',
    6: '\\large',
    7: '\\Large',
    8: '\\LARGE',
    9: '\\huge',
    10: '\\Huge',
}


class Line(Wire):
    def __init__(self, *path: sc.NodeCoordinates | PathSpecifier,
                 style: LineStyle = 'solid'):
        super().__init__(*path)
        self.style = style

    def __render__(self) -> str:
        expr = sc.ComponentExpression('draw')
        if self.style == 'dashed':
            expr.add('dashed')
        return f'\\{expr.build()} {self.__render_path__()};'


class Label(sc.Component):
    def __init__(self, text: str, position: sc.NodeCoordinates, anchor: sc.Direction = 'center',
                 align: sc.TextAlignment = 'left', rotation: int = 0, font_size: FontSize = 2):
        super().__init__()
        self.text: str = text
        self.position: sc.Node = sc.__to_node__(position)
        self.align: sc.TextAlignment = align
        self.anchor: sc.Direction = anchor
        self.rotation: int = rotation
        self.font_size: FontSize = font_size

    def __render__(self) -> str:
        text = self.text.replace('\\', '\\textbackslash').replace('\n', '\\\\')
        expr = sc.ComponentExpression('node', anchor=self.anchor, align=self.align,
                                      rotate=f'{self.rotation}', font=__latex_font_size__[self.font_size])
        return f'\\{expr.build()} at {self.position.to_tikz()} {{{text}}};'
