from inputs import day9_input
import os
import time

head_x = 0
head_y = 0

commands = []

for i in day9_input.splitlines():
    [direction, count] = i.split()
    commands.extend([direction] * int(count))

tail_locations = []
tails = [(0, 0),(0, 0),(0, 0),(0, 0),(0, 0),(0, 0),(0, 0),(0, 0),(0, 0)]

min_x = -20
max_x = 20
min_y = -20
max_y = 20
def show(tail_locations, head_x, head_y):
    print('\33c')
    x_locations = [x for (x, y) in  tail_locations]
    y_locations = [y for (x, y) in  tail_locations]
    #min_x = min(min(x_locations), head_x)
    #max_x = max(max(x_locations), head_x)

    #min_y = min(min(y_locations), head_y)
    #max_y = max(max(y_locations), head_y)

    grid = []
    for i in range(abs(min_y) + abs(max_y) + 3):
        grid.append([' '] * (abs(min_x) + abs(max_x) + 3))

    for i in range(len(tail_locations)):
        (x, y) = tail_locations[i]
        grid[y + abs(min_y)][x + abs(min_x)] = str(i)
    grid[head_y + abs(min_y)][head_x + abs(min_x)] = "H"
    [print("".join(row)) for row in grid]

def is_touching(head_x, head_y, tail_x, tail_y):
    valid_poins = [-1, 0, 1]
    for x in valid_poins:
        for y in valid_poins:
            if head_x + x == tail_x and head_y + y == tail_y:
                return True
    return False

def shift(head_x, head_y, tail_x, tail_y):
    if is_touching(head_x, head_y, tail_x, tail_y):
        return (tail_x, tail_y)
    else:
        xdiff = head_x - tail_x
        ydiff = head_y - tail_y
        if (abs(xdiff) + abs(ydiff)) >= 3:
            tail_x += 1 if head_x > tail_x else -1
            tail_y += 1 if head_y > tail_y else -1
            return (tail_x, tail_y)

        elif abs(xdiff) == 2 and ydiff == 0:
            tail_x += 1 if head_x > tail_x else -1
            return (tail_x, tail_y)

        elif abs(ydiff) == 2 and xdiff == 0:
            tail_y += 1 if head_y > tail_y else -1
            return (tail_x, tail_y)

for c in commands:
    match c:
        case "R":
            head_x += 1
        case "L":
            head_x -= 1
        case "U":
            head_y += 1
        case "D":
            head_y -= 1
    tails[0] = shift(head_x, head_y, tails[0][0], tails[0][1])
    for i in range(1, len(tails)):
        (x, y) = tails[i - 1]# if i != 0 else (head_x, head_y)
        (current_x, current_y) = tails[i]
        tails[i] = shift(x, y, current_x, current_y)
    tail_locations.append(tails[-1])
    #show(tails, head_x, head_y)
    #time.sleep(0.05)







print(len(list(set(tail_locations))))