from options import Option, some, none, `$`
from sequtils import cycle, foldl, map, filter
from strutils import parseInt, strip, splitLines
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

proc get2s(tbl: Table[char, int]): bool =
  for x in tbl.values():
    if x == 2:
      return true
  return false

proc get3s(tbl: Table[char, int]): bool =
  for x in tbl.values():
    if x == 3:
      return true
  return false

when isMainModule:
  let
    lines = readFile(filepath).strip().splitLines()
    freqs = map(lines, frequency)
    twos = filter(freqs, get2s)
    threes = filter(freqs, get3s)

  when false:
    echo "Read " & $lines.len & " lines from file"
    for i in 0..3:
      echo lines[i] & "  " & $freqs[i]
    echo "table is " & $frequency("Hello world")
    echo "twos = " & $twos[0]

  # Part 1
  echo "twos x threes = " & $(twos.len * threes.len) # 8398
