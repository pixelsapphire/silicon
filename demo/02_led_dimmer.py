from silicon.components.all import *
from silicon.markers import *
from silicon.units import ohm
from silicon.render import ImageRenderer

schem = begin(scale=2)

vin = Terminal(
    position=(0, 0),
    name='12V',
    label_pos='north'
)
l_plus = Terminal(
    position=vin + (0, 1.5),
    name='L+',
    label_pos='south'
)
l_minus = Terminal(
    position=l_plus + (0, 1),
    name='L-',
    label_pos='north'
)
transistor = NPNTransistor(
    position=l_minus + (0, 1),
    anchor='collector'
)
gnd = Terminal(
    position=transistor.emitter + (0, 1),
    name='GND',
    label_pos='south'
)
resistor = Resistor(
    start=transistor.base,
    end=transistor.base + (-2, 0),
    resistance=2200 * ohm,
    label_pos='alternative'
)
potentiometer = Potentiometer(
    position=resistor.end,
    rotation=-90,
    resistance=10000 * ohm,
    anchor='wiper',
)
switch_pot = Switch(
    start=vin + (0, 1),
    end=xy(y=vin, x=potentiometer.left) + (0, 1),
    closed=True,
    name='S1',
    mirror=True
)
switch_gnd = Switch(
    start=l_minus + (0, 0.5),
    end=l_minus + (1.5, 0.5),
    name='S2',
    label_pos='alternative'
)

Wire(vin, '-o', switch_pot.start, '-', l_plus)
Wire(l_minus, '-o', switch_gnd.start, '-', transistor.collector)
Wire(switch_pot.end, '-', potentiometer.left)
Wire(transistor.emitter, '-', gnd)
Wire(potentiometer.right, '|-o', gnd + (0, -0.5))
Wire(switch_gnd.end, '|-o', gnd + (0, -0.5))

Line(switch_pot.lever, '|-', vin + (0, 0.5), '-|', switch_gnd.lever, style='dashed')
Label(
    text='S1 and S2 closed\nsimultaneously is\na forbidden state',
    position=switch_gnd.center + (0, -0.25),
    anchor='north west',
    font_size=1,
    rotation=90
)

renderer = ImageRenderer()
renderer.render(schem, verbose=True)
renderer.show(use_pyplot=True)
