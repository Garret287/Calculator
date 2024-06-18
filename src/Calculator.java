import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Calculator {

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

    public static void main(String[] args) {
        System.out.println("Calculator ready enter expression:\n enter 'exit' for leave");
        Scanner sc = new Scanner(System.in);
        int a = 0;
        int b = 0;
        int k = 0;
        String op = "";
            do {
                String input = sc.next();
                if (input.equals("exit")) {
                    break;
                }
                String[] strings = input.split("\\W");
                String[] operator = input.split("\\w");
                if (isInt(strings[0]) && isInt(strings[1])) {
                    k = 1;
                } else if (isInt(strings[0]) || isInt(strings[1])) {
                    System.out.println("Error input try again");
                    break;
                } else {
                    k = 0;
                    strings[0] = String.valueOf(romanToArabic(strings[0]));
                    strings[1] = String.valueOf(romanToArabic(strings[1]));
                }
                    try {
                        a = Integer.parseInt(strings[0]);
                        b = Integer.parseInt(strings[1]);
                        if ((a > 10 || b > 10) || (a < 0 || b < 0)) {
                            System.out.println("Error input try again, variable more then 10 or less then 1");
                                break;
                        }
                        op = operator[operator.length - 1];
                        if (k == 1) {
                                if (operation(a,b,op) == 101) {
                                    System.out.println("Error sugn input try again");
                                    break;
                                }
                            System.out.println("Result = " + operation(a, b, op));
                        } else {
                                if (operation(a,b,op) == 101) {
                                    System.out.println("Error sign input try again");
                                    break;
                                }
                            if (operation(a,b,op) < 1) {
                                System.out.println("Error roman less then 1");
                            } else {
                                System.out.println("Result = " + arabicToRoman(operation(a, b, op)));
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error input try again");
                        break;
                    }
        } while (true);
    }

    private static int operation(int a, int b, String op) {
        switch (op) {
            case "*":
                return a * b;
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "/":
                return a / b;
            default:
                return 101;
        }
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int romanToArabic(String roman) {
        int result = 0;
        for (int i = 0; i < roman.length(); i++) {
            int current = romanToArabicMap.get(roman.charAt(i));
            int next =  (i + 1 < roman.length()) ? romanToArabicMap.get(roman.charAt(i + 1)) : 0;
            if (current < next) {
                result -= current;
            } else {
                result += current;
            }
        }
        return result;
    }

    public static String arabicToRoman(int number) {
        if (number < 0) {
            return ("");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < arabicValues.length; i++) {
                while (number >= arabicValues[i]) {
                    number -= arabicValues[i];
                    sb.append(romanNumerals[i]);
                }
            }
            return sb.toString();
        }
    }
}