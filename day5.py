from inputs import day5_input
stacks = [[],[],[],[],[],[],[],[],[]]

for l in day5_input.splitlines():
    if '[' in l:
        for i in zip(range(1, 34, 4), range(0, 9)):
            a, b = i
            if l[a] != ' ':
                stacks[b].insert(0, l[a])

part = 2
[print(s) for s in stacks]
print("executing the moving")
for l in day5_input.splitlines():                
    if "move" in l:
        [_, count, _, from_stack, _, to_stack] = l.split(" ")
        target = int(to_stack) - 1
        source = int(from_stack) - 1
        count = int(count)        
        if part == 1:    
            for i in range(count):
                stacks[target].append(stacks[source].pop())
        elif part == 2:
            stacks[target].extend(stacks[source][-count:])
            stacks[source] = stacks[source][:len(stacks[source]) - count]
        
[print(s) for s in stacks]
        
print("".join([s[-1] for s in stacks]))