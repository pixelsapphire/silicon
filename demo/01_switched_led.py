from silicon.components.all import *
from silicon.units import ohm, volt

schem = begin(scale=2)

v1 = VoltageSource(
    voltage=5 * volt,
    negative=(0, 0),
    positive=(3, 0),
    label_pos='alternative'
)
r1 = Resistor(
    resistance=220 * ohm,
    start=v1.positive,
    end=v1.positive + (0, 3)
)
d1 = LED(
    positive=r1.end,
    negative=r1.end + (-3, 0)
)
s1 = Switch(
    start=v1.negative,
    end=d1.negative,
    mirror=True,
    invert=True
)

with open('schematic.tex', 'w') as file:
    schem.render(file)
