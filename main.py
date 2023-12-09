import re

num_dict = {
    "one" : 1,
    "two" : 2,
    "three": 3,
    "four": 4,
    "five": 5,
    "six": 6,
    "seven": 7,
    "eight": 8,
    "nine" : 9,
    "ten": 10
}


def day01():
     with open("inputs/day01","r") as f:
        total = sum([calibration_value_part2(it) for it in f])
        print(total)


def calibration_value_part2(txt):
    res = re.findall("\d|one|two|three|four|five|six|seven|eight|nine", txt)
    first = num_dict.get(res[0], res[0])
    last = num_dict.get(res[-1], res[-1])
    c = f"{first}{last}"
    return int(c)


if __name__ == '__main__':
    day01()

