from textwrap import dedent

class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __repr__(self):
       return str(self.__dict__)

    def line_to(self, target):
        if self.x == target.x:
            return [Point(self.x, y) for y in range(min(target.y, self.y), max(target.y, self.y) + 1)]
        else:
            return [Point(x, self.y) for x in range(min(target.x, self.x), max(target.x, self.x) + 1)]

    def surrounding(self, r):
        for i in range(self.x - r, self.x + r):
            for k in range(self.y - r, self.y + r):
                yield Point(i, k)

    def manhattan_distance(self, target):
        return abs(self.x - target.x) + abs(self.y - target.y)

def show_grid(grid):
    print(dedent("\n".join(["".join(g) for g in grid])))