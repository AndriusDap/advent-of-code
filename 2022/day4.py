from inputs import day4_input

def between(x, left, right):
    return x >= left and x <= right

fully_contained = 0
partially_contained = 0
for line in day4_input.splitlines():
    [a_start, a_end, b_start, b_end] = [int(l) for x in line.split(",") for l in x.split("-")]
    
    if a_start >= b_start and a_end <= b_end or b_start >= a_start and b_end <= a_end:
        fully_contained += 1
    
    if between(a_start, b_start, b_end) or between(a_end, b_start, b_end) or between(b_start, a_start, a_end) or between(b_end, a_start, a_end):
        partially_contained += 1

print(fully_contained)
print(partially_contained)
