import re
from itertools import count

valid = {
    'red':  12,
    'green': 13,
    'blue': 14
}


def to_pair(s):
    x = s.split(" ")
    return x[1].strip(), int(x[0])


def to_dict(set):
    return dict([to_pair(s) for s in set.split(", ")])


def to_game(a):
    res = re.search("\d+", a)
    game_num = int(a[res.start():res.end()])
    sets_str = a.split(": ")[1].split("; ")

    sets = [to_dict(set) for set in sets_str]
    # print(game_num, sets)
    return game_num, sets

def is_valid(game):
    for set in game[1]:
        if (set.get("red", 0) > valid["red"] or
            set.get("green", 0) > valid["green"] or
            set.get("blue", 0) > valid["blue"]):
                return False

    return True


def day02():
    with open("inputs/day02","r") as f:
        games = [to_game(line) for line in f]
        valid_games = [g[0] for g in games if is_valid(g)]

        print(sum(valid_games))



if __name__ == '__main__':
    # print(is_valid(to_game("Game 1: 1 blue, 4 red; 1 red, 2 green, 6 blue; 2 green")))
    day02()