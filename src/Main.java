import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

class Main {
    public static void main(String[] args) {
        System.out.println("Калькулятор готов. Введите выражение (например, 2 + 2 или II + II):\nВведите 'exit' для выхода");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                if (!hasNoMoreThanThreeConsecutiveChars(input)) {
                    throw new Exception("Некорректный ввод: больше трёх одинаковых символов подряд.");
                }
                String result = calc(input);
                System.out.println("Результат: " + result);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                break;
            }
        }
    }

    public static String calc(String input) throws Exception {
        String[] tokens = input.split("\\s+");
        if (tokens.length != 3) {
            throw new Exception("Некорректный формат выражения. Ожидается формат: <число> <операция> <число>");
        }

        String num1Str = tokens[0];
        String operator = tokens[1];
        String num2Str = tokens[2];

        boolean isNum1Roman = isRoman(num1Str);
        boolean isNum2Roman = isRoman(num2Str);

        if (isNum1Roman != isNum2Roman) {
            throw new Exception("Используйте либо оба арабских числа, либо оба римских числа.");
        }

        int num1 = isNum1Roman ? romanToArabic(num1Str) : Integer.parseInt(num1Str);
        int num2 = isNum2Roman ? romanToArabic(num2Str) : Integer.parseInt(num2Str);

        if (num1 > 10 || num2 > 10 || num1 < 1 || num2 < 1) {
            throw new Exception("Числа должны быть в диапазоне от 1 до 10 включительно.");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                result = num1 / num2;
                break;
            default:
                throw new Exception("Неизвестная операция: " + operator);
        }

        return isNum1Roman ? arabicToRoman(result) : String.valueOf(result);
    }

    private static boolean isRoman(String s) {
        return s.matches("^[IVXLCDM]+$");
    }

    private static final Map<Character, Integer> romanToArabicMap = new HashMap<>();
    private static final int[] arabicValues = {100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] romanNumerals = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    static {
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);
        romanToArabicMap.put('L', 50);
        romanToArabicMap.put('C', 100);
    }

    private static int romanToArabic(String roman) {
        int result = 0;
        for (int i = 0; i < roman.length(); i++) {
            int current = romanToArabicMap.get(roman.charAt(i));
            int next = (i + 1 < roman.length()) ? romanToArabicMap.get(roman.charAt(i + 1)) : 0;
            if (current < next) {
                result -= current;
            } else {
                result += current;
            }
        }
        return result;
    }

    private static String arabicToRoman(int number) {
        if (number < 1) {
            return "Некорректный результат для римских чисел (меньше I).";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arabicValues.length; i++) {
            while (number >= arabicValues[i]) {
                number -= arabicValues[i];
                sb.append(romanNumerals[i]);
            }
        }
        return sb.toString();
    }

    public static boolean hasNoMoreThanThreeConsecutiveChars(String str) {
        // Regular expression to find more than 3 consecutive same characters
        int count = 1;
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1)) {
                count++;
                if (count > 3) {
                    return false;
                }
            } else {
                count = 1;
            }
        }
        return true;
    }
}