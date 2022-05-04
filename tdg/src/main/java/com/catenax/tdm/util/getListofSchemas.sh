#!/bin/bash

# This helps to generate a uniq list of schema links, which can be used, to g
cat Untitled-7.json  | grep schema | sed 's/[[:blank:]]//g' | sed 's/[\:{\[]//g' | sort | uniq
