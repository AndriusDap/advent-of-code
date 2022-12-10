from inputs import day10_input
import numpy as np


x = 1
cycle = 0
strengths = []
pixels = []

def bump_cycle():
    global cycle
    if cycle in [20, 60, 100, 140, 180, 220]:
        strengths.append(x * cycle)
    beam_position = cycle % 40
    if beam_position + 1 == x or beam_position == x or beam_position - 1 == x:
        pixels.append('#')
    else:
        pixels.append(' ')
    cycle += 1


for instruction in day10_input.splitlines():
    if instruction == 'noop':
        bump_cycle()
    else:
        val = int(instruction.split()[1])
        bump_cycle()
        bump_cycle()
        x += val

print(sum(strengths))

for row in np.array_split(pixels, 6):
    print("".join(row))