package main

import (
	"cmp"
	"fmt"
	"os"
	"reflect"
	"slices"
	"strconv"
	"strings"
)

type IdRange struct {
	start int
	end   int
}

func reduce(ranges []IdRange) []IdRange {
	byMin := func(a, b IdRange) int {
		return cmp.Compare(a.start, b.start)
	}
	slices.SortFunc(ranges, byMin)

	c := []IdRange{ranges[0]}

	for _, i := range ranges[0:] {
		last := c[len(c)-1]
		if last.end >= i.start {
			if last.end <= i.end {
				c[len(c)-1].end = i.end
			}
		} else {
			c = append(c, i)
		}
	}
	if reflect.DeepEqual(ranges, c) {
		return c
	} else {
		fmt.Println("Rolling again")
		return reduce(c)
	}
}

func SolveDay5() {
	data, err := os.ReadFile("day5.in")
	check(err)
	field := strings.Split(string(data), "\n")

	ranges := []IdRange{}
	ids := []int{}
	for _, r := range field {
		if strings.Contains(r, "-") {
			idRange := strings.Split(r, "-")
			start, err := strconv.Atoi(idRange[0])
			check(err)
			end, err := strconv.Atoi(idRange[1])
			check(err)
			ranges = append(ranges, IdRange{start: start, end: end})
		} else {
			value, err := strconv.Atoi(r)
			if err == nil {
				ids = append(ids, value)
			}
		}

	}
	totalFresh := 0
	for _, r := range ranges {
		totalFresh += r.end - r.start + 1
	}

	fresh := 0
	for _, i := range ids {
		spoiled := true
		for _, r := range ranges {
			if r.start <= i && r.end >= i {
				spoiled = false
			}
		}
		if !spoiled {
			fresh++
		}
	}

	fmt.Printf("fresh: %v\n", fresh)

	ranges = reduce(ranges)
	fmt.Printf("ranges: %v\n", ranges)

	totalFresh = 0
	for _, r := range ranges {
		//	fmt.Printf("r: %v\n", r)
		freshInRange := (r.end - r.start) + 1
		//	fmt.Printf("freshInRange: %v\n", freshInRange)
		totalFresh += freshInRange
	}
	fmt.Printf("totalFresh: %v\n", totalFresh)
}
