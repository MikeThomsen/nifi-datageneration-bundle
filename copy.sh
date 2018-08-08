#!/bin/bash
for i in `find ./ -name *.nar`; do
  cp -v $i $1
done
