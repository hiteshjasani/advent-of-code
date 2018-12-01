from strutils as su import nil

proc loadFile(filename: string): seq[string] =
  let raw = readFile(filename)
  result = su.splitLines(raw)

proc interpreter(startFreq: int, lines: openarray[string]): int =
  result = startFreq
  for x in lines:
    try:
      echo $x
      let i = su.parseInt(x)
      result += i
    except ValueError:
      echo "Could not parse " & x

when isMainModule:
  # Part 1
  let lines = loadFile "../resources/day01/input.txt"
  echo "Read " & $lines.len() & " lines from file"
  let freq = interpreter(0, lines)
  echo "End frequency is " & $freq # answer is 538
