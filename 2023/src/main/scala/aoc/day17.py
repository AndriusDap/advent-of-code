from inputs import *
import math
from heapq import *

def show_grid(grid):
    for row in grid:
        print("".join([str(r) for r in row]))

input = [[int(i) for i in line] for line in day17_normal.splitlines()]

show_grid(input)



def neighbours(y, x, prev_direction, straight_steps):
    directions = {
        "down": (1, 0),
        "up": (-1, 0),
        "left": (0, -1),
        "right": (0, 1)
    }
    oposites = {
        "down": "up",
        "up": "down",
        "left": "right",
        "right": "left"
    }
    for direction in directions:
        yi, xi = directions[direction]
        if direction == prev_direction and straight_steps == 3:
            pass
        elif prev_direction in oposites and direction == oposites[prev_direction]:
            pass
        if (x + xi) < 0 or (y + yi) < 0 or (x + xi) >= len(input[y]) or (y + yi) >= len(input):
            pass
        else:
            yield (x + xi, y + yi)
    
'''
 1  function Dijkstra(Graph, source):
 2
 3      for each vertex v in Graph.Vertices:
 4          dist[v] ← INFINITY
 5          prev[v] ← UNDEFINED
 6          add v to Q
 7      dist[source] ← 0
 8
 9      while Q is not empty:
10          u ← vertex in Q with min dist[u]
11          remove u from Q
12
13          for each neighbor v of u still in Q:
14              alt ← dist[u] + Graph.Edges(u, v)
15              if alt < dist[v]:
16                  dist[v] ← alt
17                  prev[v] ← u
18
19      return dist[], prev[]
'''

def dijsktra(source):
    distances = {}
    previous = {}
    Q = []
    for y in range(len(input)):
        for x in range(len(input[y])):
            distances[(x, y)] = math.inf
            previous[(x, y)] = None
            Q.append((x, y))

    distances[source] = 0
    while len(Q) > 0:
        u = min(Q, key=lambda k: distances[k])
        Q.remove(u)
        x, y = u
        for neighbour in neighbours(x, y, "", 0):
            if neighbour in Q:
                alt = distances[u] + input[y][x]
                if alt < distances[neighbour]:
                    distances[neighbour] = alt
                    previous[neighbour] = u
    return distances, previous


distances, prevs = dijsktra((0, 0))

print(distances[(len(input) - 1, len(input) - 1)])