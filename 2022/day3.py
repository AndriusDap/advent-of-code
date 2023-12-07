from inputs import day3_input
import numpy as np

lines = day3_input.splitlines()
priorities = {l: i+1 for i, l in enumerate("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")}

priority_sum = 0
for line in lines:
    [repeating] = set(line[:len(line)//2]).intersection(set(line[len(line)//2:]))
    priority_sum += priorities[repeating]
print(priority_sum)

badge_priorities = 0
for group in  np.array_split(lines, len(lines) // 3):
    [common] = set(group[0]).intersection(set(group[1])).intersection(set(group[2]))
    badge_priorities += priorities[common]
print(badge_priorities)
