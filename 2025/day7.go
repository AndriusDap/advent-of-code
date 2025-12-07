package main

import (
	"fmt"
	"os"
	"strings"
)

func SolveDay7() {
	data, err := os.ReadFile("day7_sample.in")
	check(err)
	field := strings.Split(string(data), "\n")

	fmt.Printf("field: %v\n", field)

	beams := []bool{}
	routes := []int{}

	for i, r := range field[0] {
		beams = append(beams, false)
		routes = append(routes, 0)
		if r == 'S' {
			beams[i] = true
			routes[i] = 1
		}
	}
	splits := 0

	for _, line := range field[1:] {
		fmt.Printf("routes: %v\n", routes)
		old := make([]bool, len(beams))
		copy(old, beams)
		for i, r := range line {
			if old[i] {
				if r == '^' {
					splits++
					beams[i] = false
					beams[i-1] = true
					beams[i+1] = true

					routes[i-1] += routes[i]
					routes[i+1] += routes[i]
					routes[i] = 0
				}
			}
		}
	}
	sum := 0
	for _, r := range routes {
		sum += r
	}
	fmt.Printf("splits: %v\n", splits)
	fmt.Printf("sum: %v\n", sum)
}
