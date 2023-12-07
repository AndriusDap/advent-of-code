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


regex = "([a-z])"
for i in range(0, 13):
    limiter = [f"\\{f}" for f in range(1, 15) if f is not (i + 2)]
    regex += f"(?!{'|'.join(limiter)})([a-z])"
print(regex)