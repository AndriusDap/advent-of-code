from inputs import day8_input

grid = [[int(x) for x in line] for line in day8_input.splitlines()]
visible = [[False for x in line] for line in grid]
y_max = len(grid)
x_max = len(grid[0])

for y in range(y_max):
    tallest_left = -1
    tallest_right = -1
    for x in range(x_max):
        if grid[y][x] > tallest_left:
            tallest_left = grid[y][x]
            visible[y][x] = True    
    for x in range(x_max - 1, -1, -1):
        if grid[y][x] > tallest_right:
            tallest_right = grid[y][x]
            visible[y][x] = True

for x in range(x_max):
    tallest_top = -1
    tallest_bottom = -1
    for y in range(y_max):
        if grid[y][x] > tallest_bottom:
            tallest_bottom = grid[y][x]
            visible[y][x] = True    
    for y in range(y_max - 1, -1, -1):
        if grid[y][x] > tallest_top:
            tallest_top = grid[y][x]
            visible[y][x] = True


#print(grid)
[print("".join([{True: "V", False: "."}[v] for v in line])) for line in visible]
print(sum([sum(i) for i in visible]))

max_score = 0
for y in range(y_max):
    for x in range(x_max):
        left_distance = 0
        right_distance = 0
        top_distance = 0
        bottom_distance = 0
        for x_c in range(x - 1, -1, -1):
            left_distance += 1
            if grid[y][x_c] >= grid[y][x]:
                break
        for x_c in range(x + 1, x_max):
            right_distance += 1
            if grid[y][x_c] >= grid[y][x]:
                break
        for y_c in range(y -1, -1, -1):
            top_distance += 1
            if grid[y_c][x] >= grid[y][x]:
                break
        for y_c in range(y + 1, y_max):
            bottom_distance += 1
            if grid[y_c][x] >= grid[y][x]:
                break

        score = left_distance * right_distance * top_distance * bottom_distance
        if score > max_score:
            max_score = score

print(max_score)