package main

import (
	"fmt"
	"os"
	"strings"
)

func countNeighbours(x, y int, field []string) int {
	offsets := [][]int{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}}
	c := 0

	for _, d := range offsets {
		xOffset, yOffset := x+d[0], y+d[1]
		if xOffset >= 0 && xOffset < len(field) {
			if yOffset >= 0 && yOffset < len(field[xOffset]) {
				if field[xOffset][yOffset] == '@' {
					c++
				}
			}
		}
	}

	return c
}

func SolveDay4() {
	data, err := os.ReadFile("day4.in")
	check(err)
	field := string(data)

	fmt.Printf("field: \n%v\n", field)

	rows := strings.Split(field, "\n")
	c := 0
	startingC := -1
	for c != startingC {
		startingC = c
		for x := range rows {
			for y := range rows[x] {
				if rows[x][y] == '@' && countNeighbours(x, y, rows) < 4 {
					c++
					b := []byte(rows[x])
					b[y] = '.'
					rows[x] = string(b)
				}
			}
		}
	}
	fmt.Printf("c: %v\n", c)

}
