from strutils as su import nil
from tables as tbl import newTable, `[]`, `[]=`

proc loadFile(filename: string): seq[string] =
  # Unless we strip the read file, we end up with an extra 0
  let raw = su.strip(readFile(filename))
  result = su.splitLines(raw)

proc strsToInts(lines: seq[string]): seq[int] =
  result = newSeq[int](lines.len)
  for i, value in lines:
    try:
      result[i] = su.parseInt(value)
    except ValueError:
      echo "Could not parse " & value
    except IndexError:
      echo "indexerror with i of " & $i

proc interpreter(startFreq: int, lines: openarray[int]): int =
  result = startFreq
  for x in lines:
    result += x

proc interpreter2(startFreq: int, lines: openarray[int]): int =
  var seenFreqs = newTable[int, bool](1024)

  result = startFreq
  seenFreqs[result] = true

  var ctr = 0
  for t in 0..1_000_000:
    for x in lines:
      ctr += 1
      result += x
      if tbl.contains(seenFreqs, result):
        echo "Saw freq " & $result & " before on iter " & $ctr
        return result
      else:
        seenFreqs[result] = true
  echo "Counter = " & $ctr

when isMainModule:
  let lines = strsToInts(loadFile("../resources/day01/input.txt"))
  echo "Read " & $lines.len() & " lines from file"
  when false:
    echo "lines.last =  " & $lines[lines.len()-1]
    for i in 1..10:
      let t = lines.len() - i
      echo "lines[" & $t & "] = " & $lines[t]

  # Part 1
  let freq = interpreter(0, lines)
  echo "Part 1:  End frequency is " & $freq # answer is 538

  # Part 2
  let dupFreq = interpreter2(0, lines)
  echo "Part 2:  First dupe frequency is " & $dupFreq # answer is 77271 on change 145417
