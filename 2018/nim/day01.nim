from options import Option, some, none, `$`
from sequtils import cycle, foldl, map
from strutils import parseInt, strip, splitLines
from tables import initTable, contains, `[]=`

const
  filepath = "../resources/day01/input.txt"
  numIterations = 144

## Each iteration does not consume any additional memory than the
## original change sequence
proc part2Iter(startFreq: int, changes: openarray[int]): Option[int] =
  var freq = startFreq
  var seenFreqs = initTable[int, bool](1024)
  seenFreqs[freq] = true

  for iter in 0..<numIterations:
    for x in changes:
      freq += x
      if contains(seenFreqs, freq):
        return some freq
      else:
        seenFreqs[freq] = true
  return none(int)

## The iterations consume additional memory of (numIterations * changes.len)
proc part2Seq(startFreq: int, changes: openarray[int]): Option[int] =
  var freq = startFreq
  var seenFreqs = initTable[int, bool](1024)
  seenFreqs[freq] = true

  for x in changes.cycle(numIterations):
    freq += x
    if contains(seenFreqs, freq):
      return some freq
    else:
      seenFreqs[freq] = true
  return none(int)

when isMainModule:
  let changes = readFile(filepath).strip().splitLines().map(parseInt)

  # Part 1
  echo "Part1: freq = " & $(foldl(changes, a + b, 0)) # 538

  # Part 2
  echo "Part2: iterative dupe freq = " & $part2Iter(0, changes) # 77271
  echo "Part2: sequential dupe freq = " & $part2Seq(0, changes) # 77271
