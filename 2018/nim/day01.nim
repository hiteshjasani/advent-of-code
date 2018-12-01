from strutils import parseInt, strip, splitLines
from tables import newTable, contains, `[]=`

proc strsToInts(lines: openarray[string]): seq[int] =
  result = newSeq[int](lines.len)
  for i, value in lines:
    try:
      result[i] = parseInt(value)
    except ValueError:
      echo "Could not parse " & value

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
  let changes = readFile(filepath).strip().splitLines().strsToInts()

  # Part 1
  block:
    var freq = 0
    for x in changes:
      freq += x
    echo "Part1: freq = " & $freq # 538

  # Part 2
  echo "Part2: dupe freq = " & $part2(0, changes) # 77271
