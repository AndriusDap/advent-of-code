from inputs import day14_input
from lib import *

points = []
for line in day14_input.splitlines():
    coords = []
    for p in line.split(" -> "):
        [x, y] =  p.split(",")
        coords.append(Point(int(x), int(y)))

    #print(coords)
    for source, target in zip(coords, coords[1:]):
        points.extend(source.line_to(target))

points = list(set(points))

max_x = max([p.x for p in points])
max_y = max([p.y for p in points])

grid = [[' '] * (max_x + 1) for _ in range(max_y + 1)]
for p in points:
    grid[p.y][p.x] = "#"

def drop(grid, sand):
    if grid[sand.y + 1][sand.x] == ' ':
        return drop(grid, Point(sand.x, sand.y + 1))
    elif grid[sand.y + 1][sand.x - 1] == ' ':
        return drop(grid, Point(sand.x - 1, sand.y + 1))
    elif grid[sand.y + 1][sand.x + 1] == ' ':
        return drop(grid, Point(sand.x + 1, sand.y + 1))
    return sand

try:
    while True:
        t = drop(grid, Point(500, 0))
        grid[t.y][t.x] = "o"
except:
    pass

#show_grid(grid)
print("".join(["".join(g) for g in grid]).count("o"))

offset = 2000
grid2 = [[' '] * (max_x + 1 + offset * 2) for _ in range(max_y + 3)]
for p in points:
    grid2[p.y][p.x + offset] = "#"

grid2[-1] = ['#'] * len(grid2[-1])

while True:
    t = drop(grid2, Point(500 + offset, 0))
    grid2[t.y][t.x] = "o"
    if t.x == 500 + offset and t.y == 0:
        break

show_grid(grid2)
print("".join(["".join(g) for g in grid2]).count("o"))