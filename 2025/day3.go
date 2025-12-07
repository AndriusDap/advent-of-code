package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func joltage12(row []int) int {
	cursor := 0
	jolt := 0

	for i := 11; i >= 0; i-- {
		f := max(row[cursor:len(row)-i]) + cursor
		cursor = f + 1
		jolt = jolt*10 + row[f]
	}
	return jolt
}

func max(row []int) int {
	idx := 0
	for i := range row {
		if row[i] > row[idx] {
			idx = i
		}
	}
	return idx
}

func joltage(row []int) int {
	first := 0
	second := 0

	for i := range row {
		if i+1 == len(row) {
			if second < row[i] {
				second = row[i]
			}

		} else if first < row[i] {
			first = row[i]
			second = 0
		} else if second < row[i] {
			second = row[i]
		}
	}

	return first*10 + second
}

func toInts(s string) []int {
	acc := []int{}

	for _, i := range s {
		j, err := strconv.Atoi(string(i))
		check(err)

		acc = append(acc, j)
	}
	return acc
}

func SolveDay3() {
	data, err := os.ReadFile("day3.in")
	check(err)
	field := string(data)

	fmt.Printf("Day3 input: \n%v\n", field)

	lines := strings.Split(field, "\n")
	combined := 0
	for _, line := range lines {
		l := toInts(line)

		//j := joltage(l)

		j := joltage12(l)
		fmt.Printf("Got joltage %v\n", j)
		combined += j
	}
	fmt.Printf("combined: %v\n", combined)

}
