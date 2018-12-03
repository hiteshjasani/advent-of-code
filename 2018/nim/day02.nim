from options import Option, some, none, `$`
from sequtils import filter, map
from strutils import strip, splitLines
from tables import Table, initTable, contains, values, `[]=`, `[]`, `$`

const
  filepath = "../resources/day02/input.txt"

proc frequency(a: string): Table[char, int] =
  result = initTable[char, int]()
  for c in a:
    if contains(result, c): result[c] += 1
    else: result[c] = 1

proc getNs(n: int): (proc(tbl: Table[char,int]): bool) =
  return proc(tbl: Table[char,int]): bool =
             for x in tbl.values():
               if x == n:
                 return true
             return false

proc diffDistance(a, b: string): int =
  if a.len == b.len:
    for i, c in a:
      if b[i] != c:
        result += 1
  else:
    return -1

proc commonChars(a, b: string): string =
  if a.len == b.len:
    for i, c in a:
      if b[i] == c:
        result &= c
  else:
    return ""

when isMainModule:
  let
    lines = readFile(filepath).strip().splitLines()
    freqs = map(lines, frequency)
    twos = filter(freqs, getNs(2))
    threes = filter(freqs, getNs(3))

  # Part 1
  echo "twos x threes = " & $(twos.len * threes.len) # 8398

  # Part 2
  for a in lines:
    for b in lines:
      if diffDistance(a, b) == 1:
        echo "common: " & commonChars(a, b)
