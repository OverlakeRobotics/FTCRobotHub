package com.overlake.ftc.ftcrobothub.routes;

public class SumDTO
{
    private String input;
    private int[] numbers;
    private int sum;

    public SumDTO() {

    }

    public void calculate(String input) {
        if (input != null || input == "") {
            numbers = getNumbers(input);
            sum = sumNumbers(numbers);
        }
    }

    private int[] getNumbers(String input) {
        String[] numbersAsStrings = input.split(",");
        int[] numbers = new int[numbersAsStrings.length];
        for (int i = 0; i < numbersAsStrings.length; i++) {
            numbers[i] = Integer.parseInt(numbersAsStrings[i]);
        }
        return numbers;
    }

    private int sumNumbers(int[] numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }
}
