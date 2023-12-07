from inputs import day2_input
scores = {"A X": 4,"A Y": 8, "A Z": 3, "B X": 1, "B Y": 5, "B Z": 9, "C X": 7, "C Y": 2, "C Z": 6}
alt_scores = {"A X": 3, "A Y": 4, "A Z": 8, "B X": 1, "B Y": 5, "B Z": 9, "C X": 2, "C Y": 6, "C Z": 7}
acc = 0
alt_acc = 0

for line in day2_input.splitlines():
    acc += scores[line.strip()]
    alt_acc += alt_scores[line.strip()]
print(acc)
print(alt_acc)
