import silicon.components.common as common
import silicon.components.diodes as diodes
import silicon.components.passive as passive
import silicon.components.sources as sources
import silicon.components.transistors as transistors
import silicon.core as sc
from numbers import Real
from typing import Callable, TypeAlias

begin: Callable[[Real], sc.Schematic] = sc.__begin__
here: sc.SymbolicNode = sc.SymbolicNode()


def xy(*, x: sc.NodeCoordinates, y: sc.NodeCoordinates) -> sc.MidpointNode:
    return sc.MidpointNode(x, y)


Wire: TypeAlias = common.Wire
Terminal: TypeAlias = common.Terminal
LED: TypeAlias = diodes.LED
Switch: TypeAlias = passive.Switch
Resistor: TypeAlias = passive.Resistor
Potentiometer: TypeAlias = passive.Potentiometer
VoltageSource: TypeAlias = sources.VoltageSource
NPNTransistor: TypeAlias = transistors.NPNTransistor
PNPTransistor: TypeAlias = transistors.PNPTransistor
