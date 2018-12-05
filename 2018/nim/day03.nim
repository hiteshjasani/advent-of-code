import re
from sequtils import delete, filter, foldl, map, mapIt
from strutils import parseUInt, strip, splitLines

const
  filepath = "../resources/day03/input.txt"

type
  Patch* = object
    id*: uint
    x*: uint
    y*: uint
    w*: uint
    h*: uint

  Pos* = object
    x*: uint
    y*: uint

proc toPatch(s: string): Patch =
  var matches: array[5, string]
  if match(s, re"(\w+)\s+@\s+(\d+),(\d+):\s+(\d+)x(\d+)", matches, 1):
    let
      t = map(matches, parseUInt)
      id = t[0]
      x = t[1]
      y = t[2]
      w = t[3]
      h = t[4]
    Patch(id: id, x: x, y: y, w: w, h: h)
  else:
    Patch(id: 0, x: 0, y: 0, w: 0, h: 0)

when isMainModule:
  const FabricSide = 1000

  type FState = enum fOpen, fUsedOnce, fUsedMultiple
  let
    lines = readFile(filepath).strip().splitLines()
    patches = map(lines, toPatch)

  var fabric: array[FabricSide, array[FabricSide, FState]]

  echo "Read " & $lines.len & " lines"

  ## Part 1
  for p in patches:
    for i in p.x..<(p.x + p.w):
      for j in p.y..<(p.y + p.h):
        case fabric[j][i]:
          of fOpen: fabric[j][i] = fUsedOnce
          of fUsedOnce: fabric[j][i] = fUsedMultiple
          of fUsedMultiple: discard

  var area = 0
  for i in 0..<FabricSide:
    for j in 0..<FabricSide:
      if fabric[j][i] == fUsedMultiple:
        area += 1

  echo "Overused area = " & $area  # 103482

  ## Part 2
  for p in patches:
    var usedOnce = true
    for i in p.x..<(p.x + p.w):
      for j in p.y..<(p.y + p.h):
        case fabric[j][i]:
          of fOpen: usedOnce = false
          of fUsedOnce: discard
          of fUsedMultiple: usedOnce = false
    if usedOnce:
      echo "Used once: " & $p
