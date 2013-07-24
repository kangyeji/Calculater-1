/**
 *
 * @author yangchen
 */

import java.util.*;

public class Problem2 {

    static String exp;

    public static String[] parseExpress(String string) {

        //check input use regular exp
        Pattern pattern = Pattern.compile("[\\d\\.\\+\\-\\*\\/]+");
        Matcher mat = pattern.matcher(string);
        if (!mat.matches()) {
            System.out.println("Illegal input!");
            System.exit(0);
        }
        
        //replace all escape characters
        string = string.replaceAll("\\+", " + ");
        string = string.replaceAll("-", " - ");
        string = string.replaceAll("\\*", " * ");
        string = string.replaceAll("/", " / ");
        
        //get all number and operators
        String[] array = string.split(" ");

        return array;
    }

    public static List<String> infixToPostfix(String[] infixArray) {
    
        List<String> list = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
        
        //use stack to store nums and oprs by oprLevel
        for (String str : infixArray) {
            if (0 == oprLevel(str)) {
                list.add(str);
            } else if (stack.isEmpty() || (oprLevel(str) > oprLevel(stack.peek()))) {
                stack.push(str);
            } else {
                while (!stack.isEmpty() && (oprLevel(str) <= oprLevel(stack.peek()))) {
                    list.add(stack.pop());
                }
                stack.push(str);
            }
        }

        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }
        return list;
    }

    public static int oprLevel(String str) {
    
        if (str.equals("*") || str.equals("/")) {
            return 2;
        } else if (str.equals("+") || str.equals("-")) {
            return 1;
        } else {
            return 0;
        }
    }

    public static Double calculatePostfix(String[] postfix) {
    
        Stack<Double> stack = new Stack<Double>();
        
        for (String token : postfix) {
            if (0 == oprLevel(token)) {
            
                //push two nums
                stack.push(Double.parseDouble(token));
            } else {
            
                //calculate two nums with token opr and then push back
                Double result = applyOpr(token, stack.pop(), stack.pop());
                stack.push(result);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Postfix error" + Arrays.toString(postfix));
        }

        return stack.pop();
    }

    public static Double applyOpr(String opr, double second, double first) {
    
        double result;
        char oprChar = opr.charAt(0);
        switch (oprChar) {
            case '+':
                result = first + second;
                break;
            case '-':
                result = first - second;
                break;
            case '*':
                result = first * second;
                break;
            case '/':
                result = first / second;
                break;
            default:
                throw new IllegalArgumentException("operator'" + opr + "'err");
        }

        return result;
    }

    public static void getInput() {
    
        System.out.println("Please enter arithmetic expression which only contains +,-,*,/");
        Scanner scan = new Scanner(System.in);
        exp = scan.nextLine();
    }

    public static void main(String[] args) {

        getInput();
        String[] array = Problem2.parseExpress(exp);
        List<String> list = Problem2.infixToPostfix(array);
        Double result = calculatePostfix(list.toArray(new String[list.size()]));
        System.out.println("= "+result);
    }
}
