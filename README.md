# deferredCalculate

```
-- result1 --
INITIALIZE ROOT
RESULT CACHED: [bbb, aaa, ccc]
CALCULATED: [aaa, bbb, ccc]
RESULT CACHED: [AAA, BBB, CCC]
CALCULATED: [AAA]
CALCULATED: [NOPE AAA]
[NOPE AAA]
-- result2 --
REUSED: [AAA, BBB, CCC]
RESULT CACHED: [A, B, C]
[A, B, C]
-- result3 --
REUSED: [bbb, aaa, ccc]
CALCULATED: ccc
ccc
-- result4 --
REUSED: [A, B, C]
CALCULATED: [a, b, c]
[a, b, c]
```