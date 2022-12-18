from inputs import day13_input
from functools import cmp_to_key

data = day13_input.splitlines()

unknown = 0
failed = 1
validated = -1

def compare(left, right):
    if type(left) == list and type(right) == list:
        if len(left) == 0 and len(right) > 0:
            return validated
        if len(right) == 0 and len(left) > 0:
            return failed
        if len(left) == 0 and len(right) == 0:
            return unknown
        result = compare(left[0], right[0])
        if result != unknown:
            return result
        return compare(left[1:], right[1:])
    elif type(left) == int and type(right) == int:
        if left == right:
            return unknown
        elif left < right:
            return validated
        elif right < left:
            return failed
    elif type(left) == int and type(right) == list:
        return compare([left], right)
    elif type(left) == list and type(right) == int:
        return compare(left, [right])

index = 1
index_sum = 0
for left, right, _ in zip(data[0::3], data[1::3], data[2::3]):
    l = eval(left)
    r = eval(right)
    print()
    print()
    print(left)
    print(right)
    result = compare(l, r)
    print(result)
    if result == validated:
        index_sum += index
    index += 1

lists = [[[2]], [[6]]]
for l in data:
    if len(l) > 0:
        lists.append(eval(l))

print(index_sum)
lists = sorted(lists, key=cmp_to_key(compare))
result = (lists.index([[2]]) + 1) * (lists.index([[6]]) + 1)
print(f"result {result}")