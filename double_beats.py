#!/usr/bin/env python3

# ;; insert-beats-between-beats [0 1 2 3 4 5])   => [0 0.5 1 1.5 2 2.5 3 3.5 4 4.5 5]
# (defn insert-beats-between-beats [coll]
#  (let [average #(/ (+ %1 %2) 2)]
#        (loop [[x & xs] coll
#               result []]
#         (if (nil? x)
#          result
#          (recur xs (apply conj result
#                     (if xs
#                      [x (average x (first xs))]
#                      [x])))))))

import sys
import os.path

if not (  len(sys.argv) == 2 and os.path.isfile(sys.argv[1])  ):
    print('usage: double_beats.py filename')
    exit(1)


with open(sys.argv[1]) as f:
    lines = f.read().splitlines()      # exported audacity labels look like "0.083000 0.083000 label_name"

timestamps = [ float( l.split()[0] ) for l in lines ]
result = []

l = len(timestamps)
for i in range(l):
    if i < l-1:
        result.append(timestamps[i])
        result.append(  (timestamps[i] + timestamps[i+1]) / 2  )
    else:
        result.append(timestamps[i])

for r in result:
    print(r)           
