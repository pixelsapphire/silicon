from abc import abstractmethod, ABC
import copy
from numbers import Real
from types import UnionType
from typing import Literal, Self, Type

Direction: Type[str] = Literal[  # TODO get rid of spaces in these names
    'center', 'north', 'north east', 'east', 'south east', 'south', 'south west', 'west', 'north west']
NodeMarker: Type[str] = Literal['none', 'dot', 'circle']
TextAlignment: Type[str] = Literal['left', 'right', 'center', 'justify']


class Node(ABC):
    @abstractmethod
    def __add__(self, node) -> Self:
        pass

    @abstractmethod
    def __radd__(self, node) -> Self:
        pass

    @abstractmethod
    def to_tikz(self) -> str:
        pass


class SymbolicNode(Node):
    def __init__(self, symbol: str | None = None):
        self.symbol: str = symbol

    def __add__(self, node) -> Self:
        other = __to_node__(node)
        if isinstance(other, CoordinateNode):
            n = CoordinateNode(other.x, other.y)
            n.relative_to = self
            return n
        if isinstance(other, SymbolicNode):
            raise TypeError('unsupported operand type(s) for +: \'SymbolicNode\' and \'SymbolicNode\'')

    def __radd__(self, node) -> Self:
        return self.__add__(node)

    def to_tikz(self) -> str:
        return f'({self.symbol})' if self.symbol else ''


class CoordinateNode(Node):
    __here__: Self

    def __init__(self, x: Real, y: Real):
        self.x: Real = x
        self.y: Real = y
        self.relative_to: SymbolicNode | None = None

    def __add__(self, node) -> Self:
        other = __to_node__(node)
        if isinstance(other, CoordinateNode):
            if self.relative_to is not None and other.relative_to is not None:
                raise ValueError('Cannot add two relative nodes.')
            n = CoordinateNode(self.x + other.x, self.y + other.y)
            n.relative_to = self.relative_to if self.relative_to else other.relative_to
            return n
        if isinstance(other, SymbolicNode):
            n = CoordinateNode(self.x, self.y)
            n.relative_to = other
            return n

    def __radd__(self, node) -> Self:
        return self.__add__(node)

    def __getitem__(self, item):
        return self.x if item == 0 else self.y

    def to_tikz(self) -> str:
        if not self.relative_to:
            return f'({self.x},{-self.y})'
        if isinstance(self.relative_to, SymbolicNode) and not self.relative_to.symbol:
            return f'++({self.x},{-self.y})'
        return f'(${self.relative_to.to_tikz()}+({self.x},{-self.y})$)'


NodeCoordinates: UnionType = tuple[Real, Real] | tuple[Real, Real, str] | Node


class MidpointNode(Node):
    def __init__(self, x_from: NodeCoordinates, y_from: NodeCoordinates):
        self.x_from: Node = __to_node__(x_from)
        self.y_from: Node = __to_node__(y_from)

    def to_tikz(self) -> str:
        return f'{self.x_from.to_tikz().rstrip(')')}|-{self.y_from.to_tikz().lstrip('(')}'

    def __add__(self, node):
        other = __to_node__(node)
        if isinstance(other, CoordinateNode):
            n = CoordinateNode(other.x, other.y)
            n.relative_to = self
            return n
        else:
            raise TypeError(f'unsupported operand type(s) for +: \'MidpointNode\' and \'{other.__class__.__name__}\'')

    def __radd__(self, node) -> Self:
        return self.__add__(node)


class ComponentExpression:
    def __init__(self, expr: Literal['to', 'node', 'draw'], *params: str | tuple[str, str | bool | None], **kwargs):
        self.expr: Literal['to', 'node', 'draw'] = expr
        self.params: list[str | tuple[str, str]] = [*params]
        self.params.extend(list((k, v) for k, v in kwargs.items()))

    def add(self, *params: str | tuple[str, str | bool | None], **kwargs) -> None:
        self.params.extend(params)
        self.params.extend(list((k, v) for k, v in kwargs.items()))

    def build(self) -> str:
        params = list(filter(lambda p: isinstance(p, str), self.params))
        for param in filter(lambda p: isinstance(p, tuple), self.params):
            if isinstance(param[1], str):
                params.append(f'{param[0]}={param[1]}')
            elif param[1]:
                params.append(param[0])
        par = ','.join([f'{param if isinstance(param, str) else f'{param[0]}={param[1]}'}' for param in params])
        return f'{self.expr}[{par}]'


class Component:
    def __init__(self, name: str | None = None):
        self.name: str | None = name
        self.__uid__: int | None = None
        __append_component__(self)

    @abstractmethod
    def __render__(self) -> str:
        pass

    def tikz_id(self) -> str:
        return f'SI{self.__uid__}'

    def clone(self, **kwargs) -> Self:
        component = copy.copy(self)
        for key, value in kwargs.items():
            setattr(component, key, value)
        __append_component__(component)
        return component


class Schematic:
    def __init__(self, scale: Real = 1):
        self.__components__: list[Component] = []
        self.__scale__: Real = scale
        self.__next_uid__: int = 0

    def add_component(self, component: Component) -> None:
        self.__components__.append(component)
        component.__uid__ = __next_uid__()

    def render(self, target: any = None) -> None:
        output = (f'\\begin{{figure}}[tbh!]\\centering\\scalebox{{{self.__scale__}}}{{'
                  '\\begin{tikzpicture}[/tikz/circuitikz/multipoles/dipchip/width=1.4,'
                  '/tikz/circuitikz/bipoles/length=0.5in,line width=0.75pt]\n' +
                  '\n'.join([c.__render__() for c in sorted(self.__components__, key=Schematic.order)]) +
                  '\n\\end{tikzpicture}}\\end{figure}')
        print(output, file=target)

    @staticmethod
    def order(component: Component) -> int:
        from silicon.components.all import Terminal
        return 2 if isinstance(component, Terminal) else 1


__schematic_id__: int = -1
__schematics__: dict[int, Schematic] = {}


def __begin__(scale: Real = 1) -> Schematic:
    global __schematic_id__
    __schematic_id__ += 1
    __schematics__[__schematic_id__] = Schematic(scale)
    return __schematics__[__schematic_id__]


def __append_component__(component: Component) -> None:
    if __schematic_id__ < 0:
        raise RuntimeError('Component created before beginning a schematic.')
    __schematics__[__schematic_id__].add_component(component)


def __next_uid__() -> int:
    global __schematic_id__
    uid = __schematics__[__schematic_id__].__next_uid__
    __schematics__[__schematic_id__].__next_uid__ += 1
    return uid


def __to_node__(node: NodeCoordinates) -> Node:
    from silicon.components.generic import OneTerminalComponent
    if isinstance(node, OneTerminalComponent):
        return node.position
    return node if isinstance(node, Node) else CoordinateNode(*node)


def __opposite__(direction: Direction) -> Direction:
    return {'center': 'center', 'north': 'south', 'north east': 'south west', 'east': 'west',
            'south east': 'north west', 'south': 'north', 'south west': 'north east', 'west': 'east',
            'north west': 'south east'}[direction]
