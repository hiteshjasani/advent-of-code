from options import Option, some, none, `$`
from sequtils import filter, map
from strutils import strip, splitLines
from tables import Table, initTable, contains, values, `[]=`, `[]`, `$`

const
  filepath = "../resources/day02/input.txt"

proc frequency(a: string): Table[char, int] =
  result = initTable[char, int]()
  for c in a:
    if contains(result, c):
      result[c] += 1
    else:
      result[c] = 1

proc getNs(n: int): (proc(tbl: Table[char,int]): bool) =
  return proc(tbl: Table[char,int]): bool =
             for x in tbl.values():
               if x == n:
                 return true
             return false

when isMainModule:
  let
    lines = readFile(filepath).strip().splitLines()
    freqs = map(lines, frequency)
    twos = filter(freqs, getNs(2))
    threes = filter(freqs, getNs(3))

  # Part 1
  echo "twos x threes = " & $(twos.len * threes.len) # 8398
