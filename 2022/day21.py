from inputs import day21_input
import time
import sympy


initial_monkeys = {}

for line in day21_input.splitlines():
    if len(line.split()) == 2:
        [name, number] = line.split(":")
        initial_monkeys[name] = int(number)
    else:
        name, first, operation, second = line.split(" ")
        initial_monkeys[name[:-1]] = (first, operation, second)


def resolve(monkeys, monkey, cache):
    if monkey in cache:
        return cache[monkey]
    elif type(monkeys[monkey]) == int:
        cache[monkey] = monkeys[monkey]
        return cache[monkey]
    else:
        left, operation, second = monkeys[monkey]
        lv = resolve(monkeys, left, cache)
        rv = resolve(monkeys, second, cache)
        value = None
        match operation:
            case "*":
                value = lv * rv
            case "+":
                value = lv + rv
            case "-":
                value = lv - rv
            case "/":
                value = lv / rv
        cache[monkey] = value
        return value


def part2():
    c = initial_monkeys.copy()
    cache_base = simplify(initial_monkeys)

    left = symbolic(c, c['root'][0], cache_base)
    right = symbolic(c, c['root'][2], cache_base)
    sympy_eq = sympy.sympify(f"Eq({left}, {right})")
    solution = int(sympy.solve(sympy_eq)[0])

    for i in range(-5, 5):
        c['humn'] = int(solution) - i
        print(i, int(solution) - i, float(right) - resolve(c, c['root'][0], {}))


def symbolic(monkeys, monkey, cache):
    if monkey in cache:
        return str(cache[monkey])
    elif monkey == 'humn':
        return 'humn'
    else:
        left, operation, second = monkeys[monkey]
        return f"({symbolic(monkeys, left, cache)}{operation}{symbolic(monkeys, second, cache)})"

def simplify(initial_monkeys):
    c = initial_monkeys.copy()
    cache = dict()
    c['humn'] = 'humn'
    for monkey in c.keys():
        try:
            resolve(c, monkey, cache)
        except:
            pass
    return cache



def part1():
    print(int(resolve(initial_monkeys, 'root', dict())))

part1()
part2()