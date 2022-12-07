from inputs import day7_input

class Tree:
    def __init__(self, path, root):
        self.folders = []
        self.files = {}
        self.path = path
        self.root = root

    def leaf(self, path):
        for f in self.folders:
            if path == f.path:
                return f
        new_leaf = Tree(path, self)
        self.folders.append(new_leaf)
        return new_leaf

    def pprint(self, indentation = 0):
        print(f"{' ' * indentation}/{self.path} [{self.size()}]:")
        for name, size in self.files.items():
            print(f"{' ' * (indentation + 2)} {name} {size}")
        for f in self.folders:
            f.pprint(indentation + 2)

    def size(self):
        current_size = sum([x for x in self.files.values()])
        child_sizes = sum([f.size() for f in self.folders])
        return current_size + child_sizes

    def directory_sizes(self):
        x = [self.size()]
        for child in self.folders:
            x.extend(child.directory_sizes())
        return x
    


root = None


files = None
for line in day7_input.splitlines():
    if line.startswith('$'):
        action = line.split()
        match action[1]:
            case 'cd':
                folder = action[2]
                match folder:
                    case '/':
                        files = Tree("/", None) 
                        root = files
                    case '..':
                        files = files.root
                    case folder:
                        files = files.leaf(folder)
            case 'ls':
                pass
    elif line.startswith('dir'):
        pass
    else:
        [size, filename] = line.split()
        files.files[filename] = int(size)
        
    
root.pprint()

print(sum([d for d in root.directory_sizes() if d <= 100000]))

claimed_space = root.size()
currently_free_space = (70000000 - claimed_space)
need_to_free_up = 30000000 - currently_free_space
print(f"occupied space is {claimed_space}, free space is {currently_free_space}, need to free up {need_to_free_up}")
print(sorted([f for f in root.directory_sizes() if f >= need_to_free_up])[0])


