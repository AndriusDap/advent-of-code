from inputs import day11_input


monkeys = []
for l in day11_input.splitlines():
    if "Monkey" in l:
        monkeys.append({})
    if "Starting items" in l:
        monkeys[-1]['items'] = l.split(":")[1].replace(",", "").split()
    if "Operation" in l:
        monkeys[-1]['operation'] = l.split("=")[1]
    if "Test" in l:
        monkeys[-1]['test'] = int(l.split("by")[-1])
    if "If true" in l:
        monkeys[-1]['if_true'] = int(l.split(" ")[-1])
    if "If false" in l:
        monkeys[-1]['if_false'] = int(l.split(" ")[-1])


for monkey in monkeys:
    monkey['inspections'] = 0


divisor = 1 
for m in monkeys:
    divisor = divisor * m['test']


for i in range(10000):
    print(f"round {i}")
    for idx, monkey in enumerate(monkeys):
        monkey['inspections'] += len(monkey['items'])
        for item in monkey['items']:
            old = int(item)
            worry = eval(monkey['operation']) % divisor
            #print(f"operation is {monkey['operation']}, old = {old}, evaled = {eval(monkey['operation'])} new worry = {worry}")

            if worry % monkey['test'] == 0:
                monkeys[monkey['if_true']]['items'].append(worry)
            else:
                monkeys[monkey['if_false']]['items'].append(worry)
        monkey['items'] = []

    for idx, monkey in enumerate(monkeys):
        print(f"monkey {idx} items: {monkey['items']}, inspections: {monkey['inspections']}")

inpections = sorted([monkey['inspections'] for monkey in monkeys], reverse = True)

print(inpections[0] * inpections[1])
