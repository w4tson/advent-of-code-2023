import re

def day01(name):
     with open("inputs/day01","r") as f:
        total = sum([calibration_value(it) for it in f])
        print(total)


def calibration_value(txt):
    first_idx = re.search("\d", txt).start()
    last_idx = re.search("\d", txt[::-1]).start()
    c = int(f"{txt[first_idx]}{txt[::-1][last_idx]}")
    print(c)
    return c


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    day01('PyCharm')
    print(calibration_value("asdf ai7asdf a3i"))

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
