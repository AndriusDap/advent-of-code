from inputs import day6_input

for i in range(len(day6_input)):
    subset = day6_input[i - 4 : i]

    if len(set(subset)) == 4:
        print(i)
        break


for i in range(len(day6_input)):
    subset = day6_input[i - 14 : i]

    if len(set(subset)) == 14:
        print(i)
        break
