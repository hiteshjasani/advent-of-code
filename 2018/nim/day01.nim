from sequtils import foldl, map
from strutils import parseInt, strip, splitLines
from tables import newTable, contains, `[]=`

proc part2(startFreq: int, changes: openarray[int]): int =
  result = startFreq
  var seenFreqs = newTable[int, bool](1024)
  seenFreqs[result] = true

  for iter in 0..1_000_000:
    for x in changes:
      result += x
      if contains(seenFreqs, result):
        return result
      else:
        seenFreqs[result] = true
  result = -1

when isMainModule:
  const filepath = "../resources/day01/input.txt"
  let changes = readFile(filepath).strip().splitLines().map(parseInt)

  # Part 1
  echo "Part1: freq = " & $(foldl(changes, a + b, 0)) # 538

  # Part 2
  echo "Part2: dupe freq = " & $part2(0, changes) # 77271
