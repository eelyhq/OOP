package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // 1. Парсим выражение из строки
        String input = "(3+(2*x))";
        Expression e = Parser.parse(input);

        // 2. Печатаем
        System.out.print("Expression: ");
        e.print(); // Ожидаем: (3+(2*x))

        // 3. Дифференцируем по x
        Expression de = e.derivative("x");
        System.out.print("Derivative: ");
        de.print(); // Ожидаем: (0+((0*x)+(2*1)))

        // 4. Вычисляем со значениями
        int result = e.eval("x = 10; y = 13");
        System.out.println("Result: " + result); // Ожидаем: 23
    }
}
