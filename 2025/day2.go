package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func check(e error) {
	if e != nil {
		panic(e)
	}
}

func isFancyFake(n int) bool {
	asString := strconv.Itoa(n)

	for i := 1; i <= len(asString)/2; i++ {
		part := asString[:i]

		if strings.Count(asString, part)*len(part) == len(asString) {
			return true
		}
	}
	return false
}

func isFake(n int) bool {
	asString := strconv.Itoa(n)

	length := len(asString)
	if length%2 == 1 {
		return false
	}

	for i := 0; i < length/2; i++ {
		if asString[i] != asString[i+length/2] {
			return false
		}
	}
	return true
}

func SolveDay2() {
	data, err := os.ReadFile("day2.in")
	check(err)

	ranges := strings.Split(string(data), ",")

	fakeSum := 0
	fancyFakeSum := 0
	for _, r := range ranges {
		parsed := strings.Split(r, "-")
		start, err := strconv.Atoi(parsed[0])
		check(err)
		end, err := strconv.Atoi(parsed[1])
		check(err)

		fmt.Printf("Got the string %v %v \n", start, end)

		for i := start; i <= end; i++ {
			if isFake(i) {
				fakeSum += i
			}

			if isFancyFake(i) {
				fmt.Printf("Found fancy fake %v\n", i)
				fancyFakeSum += i
			}
		}
	}
	fmt.Println("Yo, day 2 is here")
	fmt.Printf("Fake sum is %v \n", fakeSum)
	fmt.Printf("Fancy fake sum is %v \n", fancyFakeSum)
}
