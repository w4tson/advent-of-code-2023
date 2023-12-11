import re


def flatten_lists(a):
    if isinstance(a, list):
        for b in a:
            for x in flatten_lists(b):
                yield x
    else:
        yield a

def calc_maps(input):
    parts_map = {}
    coords_map = {}
    for i, line in enumerate(input):
        new_coords_map, new_parts = to_row(line, i, parts_map)
        coords_map = coords_map | new_coords_map
        parts_map = parts_map | new_parts

    return parts_map, coords_map


def day03(input):
    parts_map, coords_map = calc_maps(input)
    symbols = [k for k, v in parts_map.items() if not v.isdigit()]

    symbol_coords = [coord for coord, index in coords_map.items() if index in symbols]
    all_coords = list(flatten_lists([surround_coords(*coord) for coord in symbol_coords]))
    print_map(all_coords, coords_map, parts_map)
    matched_indexes = set([coords_map[coord] for coord in all_coords if coords_map.get(coord) is not None])
    print(sum([int(parts_map[index]) for index in matched_indexes]))


def day03_part2(input):
    parts_map, coords_map = calc_maps(input)

    stars = [k for k, v in parts_map.items() if v == '*']
    symbols = [k for k, v in parts_map.items() if not v.isdigit()]

    # all_coords = list(flatten_lists([surround_coords(*coord) for coord in stars]))
    symbol_coords = [coord for coord, index in coords_map.items() if index in symbols]

    stars_coords = [coord for coord, index in coords_map.items() if index in stars]
    print(f"stars ={stars_coords}")
    # print(symbol_coords)
    all_coords = list(flatten_lists([surround_coords(*coord) for coord in symbol_coords if coordsd]))
    print_map(all_coords, coords_map, parts_map)

    for

    # matched_indexes = set([coords_map[coord] for coord in all_coords if coords_map.get(coord) is not None])
    # print(sum([int(parts_map[index]) for index in matched_indexes]))


def print_map(all_coords, coords_map, parts_map):
    all_coords_map = dict(all_coords)
    print(coords_map.keys())
    maxx = max(map(lambda coord: coord[0], coords_map.keys()))
    maxy = max(map(lambda coord: coord[1], coords_map.keys()))
    skip = []
    print(f"all-coords = {all_coords}")
    print(all_coords_map)
    for y in range(maxy+1):
        for x in range(maxx+1):
            if (x,y) in all_coords:
                print("\x1b[1;32m", end='')

            if len(skip) > 0:
                print(skip[0], end='')
                skip = skip[1:]
                continue

            index = coords_map.get((x, y))
            if index is not None:
                item = parts_map[index]
                if item.isdigit():
                    skip = list(item)[1:]
                    print(item[0], end='')
                else:
                    print(f"\x1b[1;31m{item}\x1b[0m", end='')
            else:
                print('.', end='')

            print("\x1b[0m", end='')
        print()


def surround_coords(x, y):
    surrounding = [
        (x-1, y),
        (x+1, y),
        (x, y-1),
        (x, y+1),
        (x-1, y-1),
        (x+1, y-1),
        (x-1, y+1),
        (x+1, y+1)
    ]

    return surrounding


def to_row(r, y_index, parts_map):
    index = max(parts_map.keys()) + 1  if len(parts_map.keys()) > 0 else 0
    coords_map = {}

    for match in re.finditer('(\d+|[^.])', r.strip()):
        new_dict = dict([((x, y_index), index) for x in range(match.start(), match.end())])
        coords_map = coords_map | new_dict
        parts_map[index]=match.group()
        index += 1

    return coords_map, parts_map


if __name__ == '__main__':
    with open('inputs/day03','r') as f:
        day03_part2(f)



