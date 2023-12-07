from inputs import day15_input
from lib import *
import re

sensors = []
beacons = []
sensor_set = []
for line in day15_input.splitlines():
    print(line)
    s_x, s_y, b_x, b_y = re.search('Sensor at x=([\-0-9]+), y=([\-0-9]+): closest beacon is at x=([\-0-9]+), y=([\-0-9]+)', line).group(1, 2, 3, 4)
    sensor = Point(int(s_x), int(s_y))
    sensor.closest = Point(int(b_x), int(b_y))
    sensor.range = sensor.manhattan_distance(sensor.closest)
    beacons.append((int(b_x), int(b_y)))
    sensor_set.append((int(s_x), int(s_y)))
    sensors.append(sensor)

beacons = set(beacons)
sensor_set = set(sensor_set)
c = 0
x_start = min(s.x - s.range for s in sensors) - 1
x_end = max(s.x + s.range for s in sensors) + 1



def can_contain_beacon(p):
    for s in sensors:
        if s.range >= s.manhattan_distance(p) and (p.x, p.y) not in beacons:
            return False
    return True

def part1():
    y = 2000000
    grid = []
    c = 0
    for x in range(x_start, x_end):

        if can_contain_beacon(Point(x, y)):
            #grid.append(" " if (x, y) not in beacons else "B")
            pass
        else:
            c += 1
            #grid.append("#" if (x, y) not in sensor_set else "S")

    print((str(y) + " " * 10)[:4], (str(c) + " " * 10)[:4], "".join(grid))

    print(sensors)
    print(beacons)
    print(c)

def part2():
    limit = 4000000
    for x in range(limit + 1):
        y = 0
        while y <= limit:
            valid = True
            for s in sensors:
                if s.manhattan_distance(Point(x, y)) <= s.range:
                    valid = False
                    # we don't need to save the ranges, we can just push 'y' on the fly to the next edge of this rhombus
                    y = s.y + s.range - abs(s.x - x)
                    break
            if valid:
                return Point(x, y)
            y += 1
        #if x % (limit / 100) == 0:
            #print(float(x) / limit)
part1()
p2 = part2()
print(p2.x * 4000000 + p2.y)