import java.awt.Graphics;

public class Function {
	
	private static final double EulerMascheroni = 0.5772156649;
	
	public static double Gamma(double x){
			double erg = x*(Math.exp(EulerMascheroni*(x)));
			for (int n = 1; n<10000;n++) {
				erg = erg*(n+x)/n * Math.exp(-x/n);
			}
			return 1/erg;
	}
	
	public static double Digamma(double x){
		double n = 10000;
		double erg = Math.log(n);
		for(int k = 0; k <= n; k++){
			erg -= 1/(x+k);
		}
		return erg;
	}
	
	public static double Zeta(double x){
		double erg = 0;
		int n = 100;
		for(int k = 1; k <= n; k++){
			erg += 1/Math.pow(k, x);
		}
		return erg;
	}
	
	public static double heavySide(double x){
		if(x < 0) return 0;
		else if(x > 0) return 1;
		else return 1/2;
	}
	
	public static double sigmoid(double x){
		return 1/(1+Math.exp(-x));
	}
	
	private static final char[] operators = {'+', '-', '*', '/', '^', '!'};
	private static final String[] functions = {"sin","cos","tan", "cot",
											   "asin", "acos", "atan", "acot",
											   "sinh", "cosh", "tanh", "coth", 
											   "asinh", "acosh", "atanh", "acoth", 
											   "exp", "ln", "log", "ld",
											   "gamma", "digamma", "fact",
											   "floor", "ceil", "ipart", "fpart",
											   "sgn", "hs",
											   "sigmoid", 
											   "sqrt", "cbrt",
											   "zeta"};
	
	private String term;
	
	public Function(String t){
		term = t.replaceAll(" ", "");
	}
	
	public String getTerm(){
		return term;
	}
	
	public void setTerm(String t){
		term = t.replaceAll(" ", "");
	}
	
	public static double parse(String t, int operator, double x){
		
		t = removeBrackets(t);
		//falls leerer Term, direkte Ausgabe von 0
		if(t.equals("")) return 0;
		
		if (operator>operators.length-1) {
			return parseFunctions(t, x);
			
		}
		
				
		//Kontrollausgabe
		//System.out.println("parse:\t" + operators[operator] + '\t' + t);
		
		int brackets = 0;
		int i = 0;
		char c;
				
		for(i = 0; i < t.length(); i++){
			
			c = t.charAt(i);
			if(c == '('){ brackets++; continue; }
			else if(c == ')'){ brackets--; continue; }
			//System.out.println(brackets);
						
			if(brackets == 0 && c == operators[operator]) {
				if(operator == 5){
					return Gamma(parse(t.substring(0, i), 0, x)+1);
				}
				return operate(operator, parse(t.substring(0, i), 0, x), parse(t.substring(i + 1, t.length()), 0, x));
			}
			
			/*else if(brackets == 0 && c == '-') {
				return parse(t.substring(0, i), operator+1, x) - parse(t.substring(i + 1, t.length()), operator, x);
			}*/			
			
		}

		return parse(t, operator+1, x);		
		
	}
	
	public static double parseConstants(String t, double x) {
		//System.out.println("parseConstants: " + t);
		if(t.equals("x")) return x;
		else if(t.equals("e")) return Math.E;
		else if(t.equals("pi")) return Math.PI;
		else if(t.equals("phi")) return (Math.sqrt(5)+1)/2;
		else if(t.equals("psi")) return (Math.sqrt(5)-1)/2;
		else if(t.equals("em")) return EulerMascheroni;
		return parseNumbers(t);
	}

	public static double parseNumbers(String t) {
		return Double.parseDouble(t);
	}	
	
	public static double operate(int operator, double a, double b){
		switch(operator){
		case 0: return a + b;
		case 1: return a - b;
		case 2: return a * b;
		case 3: return a / b;
		case 4: return Math.pow(a, b);
		default: System.out.println("invalid operator id"); System.exit(0); return 0;
		}
	}
	
	public static double calcFunction(int fId, double x) {
		String function = functions[fId];
		switch(function) {
		//trig
		case "sin": return Math.sin(x);
		case "cos": return Math.cos(x);
		case "tan": return Math.tan(x);
		case "cot": return 1/Math.tan(x);
		case "sec": return 1/Math.cos(x);
		case "csc": return 1/Math.sin(x);
		//atrig
		case "asin": return Math.asin(x);
		case "acos": return Math.acos(x);
		case "atan": return Math.atan(x);
		case "acot": return Math.atan(1/x);
		case "asec": return Math.acos(1/x);
		case "acsc": return Math.asin(1/x);
		//trigh
		case "sinh": return Math.sinh(x);
		case "cosh": return Math.cosh(x);
		case "tanh": return Math.tanh(x);
		case "coth": return 1/Math.tanh(x);
		case "sech": return 1/Math.cosh(x);
		case "csch": return 1/Math.sinh(x);
		//atrigh
		case "asinh": return Math.log(1+Math.sqrt(1+x*x));
		case "acosh": return Math.log(1+Math.sqrt(x*x-1));
		case "atanh": return -Math.log(2/(x+1)-1)/2;
		case "acoth": return -Math.log(2/(1/x+1)-1)/2;
		case "asech": return Math.log(1+Math.sqrt(1/(x*x)-1));
		case "ascsh": return Math.log(1+Math.sqrt(1+1/(x*x)));
		//exp
		case "exp": return Math.exp(x);
		//log
		case "ln": return Math.log(x);
		case "log": return Math.log10(x);
		case "ld": return Math.log1p(x)/Math.log(2);
		//gamma, fac
		case "gamma": return Gamma(x);
		case "digamma": return Digamma(x);
		case "fact": return Gamma(x+1);
		//num
		case "floor": return Math.floor(x);
		case "ceil": return Math.ceil(x);
		case "ipart": return Math.floor(Math.abs(x));
		case "fpart": return x-Math.floor(x)-heavySide(-x);
		case "sgn": return Math.signum(x);
		case "hs": return heavySide(x);
		//fermi
		case "sigmoid": return sigmoid(x);
		//radix
		case "sqrt": return Math.sqrt(x);
		case "cbrt": return Math.cbrt(x);
		//Riemann
		case "zeta": return Zeta(x);
		
		default: System.out.println("Invalid function id"); System.exit(0);return 0; 
		}
	}
	
	public static double parseFunctions(String s, double x) {
		int i = 0;
		String fname = "";
		while(s.charAt(i) != '(') {
			fname += s.charAt(i);
			i+=1;
			if (i==s.length()) {
				break;
			}
		}
		if (i<s.length()) { 
			int k;
			for (k = 0; k<functions.length;k++) {
				if (fname.equals(functions[k])) {
					String argument = s.substring(i+1,closeBracket(s,i,true));
					return calcFunction(k, parse(argument,0,x));
				}
			}
		}
		return parseConstants(s, x);
	
	}
	
	public static int closeBracket(String s, int i, boolean searchForClosed) {
		int j;
		int c = 1;
		int start = i + 1, end = s.length(), inc=1;
			
		if(!searchForClosed) {
			end = i+1;
			start = s.length();
			inc = -1;
		}
		for(j = start; j != end; j+=inc) {
			
			if(s.charAt(j)=='(') {
				c+=inc;
			}
			else if(s.charAt(j)==')') {
				c-=inc;
			}
			
			if(c == 0) break;
					
		}
		
		return j;
	}
	
	public static String removeBrackets(String t){
		if(t.equals("")) return "";
		String s = t;
		boolean b1 = t.charAt(0) == '(';
		boolean b2 = closeBracket(t, 0,true) == (t.length() - 1);
		if(b1 && b2) s = t.substring(1, t.length() - 1);
		return s;
	}
	
	public double eval(double x){
		return parse(term, 0, x);
	}
	
	/*public String replaceFactorial(String s) {
		String ret;
		int i = s.indexOf('!');
		if (s.charAt(i-1) ==')') {
			int start = closeBracket(s, i, false);
			ret = s.substring(0, start) + "fac" + s.substring(start,i) + s.substring(i+1,s.length());
		} else {
				
		
			
		}
		return ret;
	}*/
	
	public void draw(Graphics g, int x0, int y0) {
		double x1, y1, x2, y2;
		x1 = -5;
		y1 = parse(term, 0, x1);
		x2 = x1;
		y2 = y1;
		for(x1 = -5; x1 <= 5; x1+=0.01) {
			y1 = eval(x1);
			g.drawLine((int)(x0+20*x1), (int)(y0-20*y1), (int)(x0+20*x2), (int)(y0-20*y2));
			//g.fillRect((int)(x0+20*x1), (int)(y0-20*parse(term, 0, x1)), 1, 1);
			x2 = x1; y2 = y1;
		}
	}

}
