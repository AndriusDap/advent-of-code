from distutils import dist
from inputs import day12_input
import math
from multiprocessing import Pool

input = day12_input.splitlines()

source = None
destination = None
for y in range(len(input)):
    for x in range(len(input[y])):
        if input[y][x] == 'S':
            source = (x, y)

for y in range(len(input)):
    for x in range(len(input[y])):
        if input[y][x] == 'E':
            destination = (x, y)
            
def height(char):
    if char == 'S':
        return ord('a')
    if char == 'E':
        return ord('z')
    return ord(char)

def neighbours(u):
    x, y = u
    value = input[y][x]

    for xi, yi in [(-1, 0), (1, 0), (0, 1), (0, -1)]:
        if (x + xi) < 0 or (y + yi) < 0 or (x + xi) >= len(input[y]) or (y + yi) >= len(input):
            pass
        else:
            if height(value) - height(input[y + yi][x + xi]) >= -1:
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
        for neighbour in neighbours(u):
            if neighbour in Q:
                alt = distances[u] + 1
                if alt < distances[neighbour]:
                    distances[neighbour] = alt
                    previous[neighbour] = u
    return distances, previous

def part2(a):
    (distances_a, previous_a) = dijsktra(a)
    print(a, distances_a[destination])
    return distances_a[destination]

if __name__ == '__main__':

    (distances, previous) = dijsktra(source)

    d = destination
    c = [[' '] * len(i) for i in input]
    while d != source:
        p = previous[d]
        (x, y) = p
        c[y][x] = input[y][x]
        d = p

    x, y = destination
    c[y][x] = input[y][x]

    items = []
    for y in range(len(input)):
        for x in range(len(input[y])):
            if input[y][x] == 'a':
                items.append((x, y))

    with Pool(10) as p:
        ad = p.map(part2, items)
        print(min(ad))
