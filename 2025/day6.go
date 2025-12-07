package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func SolveDay6() {
	data, err := os.ReadFile("day6.in")
	check(err)
	field := strings.Split(string(data), "\n")
	givens := [][]string{}
	for _, row := range field {
		r := strings.Split(row, " ")
		line := []string{}
		for _, symbol := range r {
			clean := strings.TrimSpace(symbol)
			if len(clean) > 0 {
				line = append(line, clean)
			}
		}
		fmt.Printf("line: %v\n", line)
		givens = append(givens, line)
	}

	//fmt.Printf("givens: %v\n", givens)

	// sum := 0
	// for idx := range givens[0] {
	// 	first, err := strconv.Atoi(givens[0][idx])
	// 	check(err)
	// 	second, err := strconv.Atoi(givens[1][idx])
	// 	check(err)
	// 	third, err := strconv.Atoi(givens[2][idx])
	// 	check(err)
	// 	fourth, err := strconv.Atoi(givens[3][idx])
	// 	check(err)
	// 	if givens[4][idx] == "*" {
	// 		sum += first * second * third * fourth
	// 	} else {
	// 		sum += first + second + third + fourth
	// 	}
	// }
	// fmt.Printf("sum: %v\n", sum)

	sum := 0
	for i := len(field[0]) - 1; i >= 0; {
		parseSingleNumber := func(offset int) int {
			number := 0
			for r := range field[:len(field)-1] {
				digit, _ := strconv.Atoi(string(field[r][i-offset]))
				if number != 0 && digit != 0 {
					number *= 10
				}
				number += digit
			}
			return number
		}
		numbers := []int{}
		j := 0
		for ; i-j >= 0; j++ {
			parsed := parseSingleNumber(j)
			if parsed == 0 {
				//j++
				break
			}
			numbers = append(numbers, parsed)
		}
		j--
		operand := string(field[len(field)-1][i-j])
		fmt.Printf("numbers: %v\n", numbers)
		fmt.Printf("operand: %v\n", operand)

		agg := numbers[0]
		for _, number := range numbers[1:] {
			if operand == "+" {
				agg += number
			} else {
				agg *= number
			}
		}
		fmt.Printf("agg: %v\n", agg)
		sum += agg
		i -= len(numbers) + 1

	}
	fmt.Printf("sum: %v\n", sum)

}
