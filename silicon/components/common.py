import silicon.core as sc
from silicon.components.generic import OneTerminalComponent
from numbers import Real
from typing import Literal, TypeAlias, get_args

PathSpecifier: TypeAlias = Literal['-', 'o-', '-o', 'o-o', '|-', 'o|-', '|-o', 'o|-o', '-|', 'o-|', '-|o', 'o-|o']


class Wire(sc.Component):

    def __init__(self, *path: sc.NodeCoordinates | PathSpecifier):
        """
        Accepts an odd number of arguments, where the odd arguments should be `NodeCoordinates`
        objects and the even arguments are `PathSpecifier` values (counting from 1).
        """
        super().__init__()
        self.nodes: list[sc.Node] = []
        self.path_spec: list[PathSpecifier] = []
        for i, p in enumerate(path):
            if i % 2 == 0:
                self.nodes.append(sc.__to_node__(p))
            else:
                if p not in get_args(PathSpecifier):
                    raise ValueError(f'Invalid path specifier: {p}')
                self.path_spec.append(p)

    @staticmethod
    def __make_wire__(specifier: str) -> tuple[str, str | None]:
        start = specifier[0]
        end = specifier[-1]
        if start == '' and end == '':
            return specifier if specifier != '-' else '--', None
        if '|' not in specifier:
            return f'to[short,{specifier.replace('o', '*')}]', None
        middle = specifier.strip('o')
        return (f'{'to[short,*-]++(0,0) ' if start == 'o' else ''}{middle}',
                'to[short,-*]++(0,0)' if end == 'o' else None)

    def __render_path__(self) -> str:
        path = ''
        prev_path = None
        for i in range(len(self.nodes)):
            before, after = self.__make_wire__(self.path_spec[i]) if i < len(self.path_spec) else ('', None)
            path += f'{self.nodes[i].to_tikz()}{prev_path if prev_path else ''}{before}'
            prev_path = after
        return path

    def __render__(self) -> str:
        return f'\\draw {self.__render_path__()};'


class Terminal(OneTerminalComponent):

    def __init__(self, *, position: sc.NodeCoordinates, name: str | None = None,
                 label_pos: sc.Direction = 'north', label_rotation: Real = 0):
        super().__init__(name=name if name else '', position=position)
        self.label_anchor = sc.__opposite__(label_pos)
        self.label_rotation = label_rotation

    def __render__(self) -> str:
        return (f'\\draw {self.position.to_tikz()} to[short,-o]++(0,0) node[anchor={self.label_anchor}]'
                f'{{\\rotatebox[origin=c]{{{self.label_rotation}}}{{\\scriptsize{{\\texttt{{{self.name}}}}}}}}};')
