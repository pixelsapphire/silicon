from numbers import Number

from typing import Self


class Dimension:
    def __init__(self, powers: dict[str, int]):
        self.powers = {key: value for key, value in powers.items() if value != 0}

    def __eq__(self, other):
        return self.powers == other.powers

    def __hash__(self):
        return hash(tuple(sorted(self.powers.items())))

    def __multiply__(self, other, inverse: bool) -> Self:
        result = self.powers.copy()
        for key, value in other.powers.items():
            if key in result:
                result[key] += value if not inverse else -value
            else:
                result[key] = value if not inverse else -value
        return Dimension(result)

    def __mul__(self, other) -> Self:
        return self.__multiply__(other, False)

    def __truediv__(self, other) -> Self:
        return self.__multiply__(other, True)

    def __str__(self):
        return '*'.join([f'{key}^{value}' if value != 1 else key for key, value in self.powers.items()])

    def __repr__(self):
        return self.__str__()


class Unit:
    __lookup__: dict[Dimension, Self] = {}

    def __init__(self, name: str, symbol: str, dimension: Dimension):
        self.name: str = name
        self.symbol: str = symbol
        self.dimension: Dimension = dimension

    def __multiply__(self, other, inverse: bool) -> Self:
        dimension = self.dimension * other.dimension if not inverse else self.dimension / other.dimension
        if dimension in Unit.__lookup__:
            return Unit.__lookup__[dimension]
        Unit.__lookup__[dimension] = Unit(
            f'{self.name} * {other.name}' if not inverse else f'{self.name} / {other.name}',
            f'{self.symbol}{other.symbol}' if not inverse else f'{self.symbol}/{other.symbol}',
            self.dimension * other.dimension if not inverse else self.dimension / other.dimension)
        return Unit.__lookup__[dimension]

    def __mul__(self, other: Self | Number) -> Self:
        if isinstance(other, Number):
            return Amount(other, self.dimension)
        return self.__multiply__(other, False)

    def __rmul__(self, other: Self | float) -> Self:
        return self.__mul__(other)

    def __truediv__(self, other: Self) -> Self:
        return self.__multiply__(other, True)


class Amount:
    def __init__(self, magnitude: Number, dimension: Dimension):
        self.magnitude: Number = magnitude
        self.dimension: Dimension = dimension

    @staticmethod
    def prefix(value: float) -> str:
        prefixes = ['y', 'z', 'a', 'f', 'p', 'n', 'μ', 'm', '', 'k', 'M', 'G', 'T', 'P', 'E', 'Z', 'Y']
        if value == 0:
            return '0'
        if value < 0:
            return '-' + Amount.prefix(-value)
        exp = int((len(str(abs(value))) - 1) / 3)
        amount = f'{value / 10 ** (3 * exp)}'
        return f'{amount[:-2] if amount.endswith('.0') else amount}{prefixes[exp + 8]}'

    def __eq__(self, other: Self) -> bool:
        return self.magnitude == other.magnitude and self.dimension == other.dimension

    def __check_dimension__(self, other: Self | Number) -> None:
        if (isinstance(other, Amount) and self.dimension != other.dimension or
                isinstance(other, Number) and self.dimension != Dimension({})):
            raise ValueError('Cannot perform operation on amounts with different dimensions.')

    def __add__(self, other: Self | Number) -> Self:
        self.__check_dimension__(other)
        return Amount(self.magnitude + other.magnitude, self.dimension)

    def __sub__(self, other: Self | Number) -> Self:
        self.__check_dimension__(other)
        return Amount(self.magnitude - other.magnitude, self.dimension)

    def __mul__(self, other: Self | Number | Unit) -> Self:
        if isinstance(other, Number):
            return Amount(self.magnitude * other, self.dimension)
        if isinstance(other, Unit):
            return Amount(self.magnitude, self.dimension * other.dimension)
        return Amount(self.magnitude * other.magnitude, self.dimension * other.dimension)

    def __truediv__(self, other: Self | Number | Unit) -> Self:
        if isinstance(other, Number):
            return Amount(self.magnitude / other, self.dimension)
        if isinstance(other, Unit):
            return Amount(self.magnitude, self.dimension / other.dimension)
        return Amount(self.magnitude / other.magnitude, self.dimension / other.dimension)

    def __str__(self):
        if self.dimension in Unit.__lookup__.keys():
            return f'{self.prefix(self.magnitude)}{Unit.__lookup__[self.dimension].symbol}'
        return f'{self.prefix(self.magnitude)}{self.dimension}'

    def __repr__(self):
        return self.__str__()


ampere = Unit('ampere', 'A', Dimension({'A': 1}))
volt = Unit('volt', 'V', Dimension({'m': 2, 'kg': 1, 's': -3, 'A': -1}))
ohm = Unit('ohm', 'Ω', Dimension({'m': 2, 'kg': 1, 's': -3, 'A': -2}))
watt = Unit('watt', 'W', Dimension({'m': 2, 'kg': 1, 's': -3}))
henry = Unit('henry', 'H', Dimension({'m': 2, 'kg': 1, 's': -2, 'A': -2}))
farad = Unit('farad', 'F', Dimension({'m': -2, 'kg': -1, 's': 4, 'A': 2}))
hertz = Unit('hert`', 'Hz', Dimension({'s': -1}))

Unit.__lookup__.update({unit.dimension: unit for unit in [ampere, volt, ohm, watt, henry, farad, hertz]})
