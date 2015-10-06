package parse;


//FIGURE OUT THE THROW NEW UNSUPPORTED ERROR THINGS
import java.io.Reader;

import ast.Action;
import ast.Action.Hamlet;
import ast.BinaryCondition;
import ast.BinaryCondition.Operator;
import ast.Booly;
import ast.Booly.equalities;
import ast.Condition;
import ast.Expr;
import ast.Mem;
import ast.Node;
import ast.Num;
import ast.Plmin;
import ast.Program;
import ast.ProgramImpl;
import ast.Rule;
import ast.Rulesll;
import ast.Senses;
import ast.Sensespace;
import ast.Specaction;
import ast.Timdivmod;
import ast.Update;
import ast.Updateact;
import ast.Updatell;
import exceptions.SyntaxError;
import parse.Tokenizer.TokenizerIOException;

class ParserImpl implements Parser {
	private static int [] mem = new int [20];

    @Override
    public Program parse(Reader r) {
    	Tokenizer j = new Tokenizer (r);
    	ProgramImpl p = null;
    	try {
			p = parseProgram(j); 
		} catch (SyntaxError e) {
			// TODO Auto-generated catch block
			System.err.println("error");
			e.printStackTrace();
		}
    	return p;
        //throw new UnsupportedOperationException(); 
    }

    
    /** Parses a program from the stream of tokens provided by the Tokenizer,
     *  consuming tokens representing the program. All following methods with
     *  a name "parseX" have the same spec except that they parse syntactic form
     *  X.
     *  @return the created AST
     *  @throws SyntaxError if there the input tokens have invalid syntax
     */
    public static ProgramImpl parseProgram(Tokenizer t) throws SyntaxError {
    	Rulesll rll = new Rulesll();
    	while (t.hasNext()){
    		rll.add(parseRule(t));
    	}
    	return new ProgramImpl(rll);
        //throw new UnsupportedOperationException();
    }
//parse the rule by using parseCondition which should consume until the --> then consume --> and parsecommand
    /**
     * Parses the rule
     * @param t
     * 			the tokenizer
     * @return
     * 			A Rule Node
     * @throws SyntaxError
     * 			if the thing which follows a condition is not an arrow.
     */
    public static Rule parseRule(Tokenizer t) throws SyntaxError {
    	Condition a = parseCondition(t);
    	consume(t,TokenType.ARR);
    	Node b = parseCommand(t);
    	return new Rule(a,b);
        //throw new UnsupportedOperationException();
    }

    public static Condition parseCondition(Tokenizer t) throws SyntaxError {
    	Condition c = parseConjunction(t);
    	while(t.peek().getType().equals(TokenType.OR)){
    		consume(t,TokenType.OR);
    		c = new BinaryCondition(c,Operator.OR,parseConjunction(t));
    	}
    	return c;
        //throw new UnsupportedOperationException();
    }

    public static Expr parseExpression(Tokenizer t) throws SyntaxError {
    	//TODO
    	Expr e = parseTerm(t);
    	while (t.peek().isAddOp()){ //hopefully this is valid syntax?
    		if (t.peek().getType().equals(TokenType.PLUS)){
    			consume(t,TokenType.PLUS);
    			e = new Plmin(e,parseTerm(t),true);
    		}
    		else{
    			consume(t,TokenType.MINUS);
    			e = new Plmin(e,parseTerm(t),false);
    		}
    	}
    	return e;
        //throw new UnsupportedOperationException();
    }

    public static Expr parseTerm(Tokenizer t) throws SyntaxError {
    	Expr f = parseFactor(t);
    	Boolean mult;
    	while (t.peek().isMulOp()){
    		if (t.peek().getType().equals(TokenType.MUL)){
    			mult = true;
    			consume(t,TokenType.MUL);
    		}
    		else if (t.peek().getType().equals(TokenType.DIV)){
    			mult = false;
    			consume(t,TokenType.DIV);
    		}
    		else{
    			mult = null;
    			consume(t,TokenType.MOD);
    		}
    		f = new Timdivmod(f,parseFactor(t),mult);
    	}
    	return f;
        //throw new UnsupportedOperationException();
    }

    public static Expr parseFactor(Tokenizer t) throws SyntaxError {
        // TODO
    	Expr e;
    	if (t.peek().getType().equals(TokenType.MEM)){
    		consume(t,TokenType.MEM);
    		consume(t,TokenType.LBRACKET);
    		e = parseExpression(t); //TODO make mem object
    		consume(t,TokenType.RBRACKET);
    		e = new Mem(e);
    	}
    	else if (t.peek().getType().equals(TokenType.LPAREN)){
    		consume(t,TokenType.LPAREN);
    		e = parseExpression(t);
    		consume(t,TokenType.RPAREN);
    	}
    	else if (t.peek().getType().equals(TokenType.MINUS)){
    		consume(t,TokenType.MINUS);
    		e = parseFactor(t); //TODO negate parsefactor here. Maybe by casting to num
    	}
    	else if (t.peek().isSensor()){
    		e = parseSensor(t);
    	}
    	else{
    		e = new Num(Integer.parseInt(t.peek().toString()));
    		consume(t,TokenType.NUM);
    	}
    	return e;
        //throw new UnsupportedOperationException();
    }
    
    
    
    
    
    
    
    private static Condition parseConjunction(Tokenizer t) throws SyntaxError{
        Condition c = parseRelation(t);
       	while(t.peek().getType().equals(TokenType.AND)){
       		consume(t,TokenType.AND);
       		c = new BinaryCondition(c,Operator.AND,parseRelation(t));
       	}
       	return c;
    }
    
    private static Condition parseRelation(Tokenizer t) throws SyntaxError{
    	if (t.peek().getType().equals(TokenType.LBRACE)){
    		consume(t,TokenType.LBRACE);
    		Condition c = parseCondition(t);
    		consume(t,TokenType.RBRACE);
    		return c;
    	}
    	else{
    		Expr uno = parseExpression(t);
    		TokenType eq = t.peek().getType();
    		equalities g = tokenequal(eq);
    		consume(t,eq);
    		Expr dos = parseExpression(t);
    		return new Booly(uno,dos,g);
    	}
    }
    
    private static Updateact parseCommand(Tokenizer t) throws TokenizerIOException, SyntaxError{
    	Updatell e = new Updatell(); //TODO add the or-action part and the infinitely many part
    	
    	while (t.peek().getType().equals(TokenType.MEM)){
    		Update u = parseUpdate(t);
    		e.add(u);
    	}
    	Action a = null;
    	if (t.peek().isAction()){
    		a = parseAction(t);
    	}
		return new Updateact(e.toarray(),a);//TODO make this return something valuable
    }
    
    /** This should be the full parseUpdate*/
    private static Update parseUpdate(Tokenizer t) throws TokenizerIOException, SyntaxError{ //TODO fix this
    	consume(t,TokenType.MEM);
    	consume(t,TokenType.LBRACKET);
    	Expr e = parseExpression(t);
    	consume(t,TokenType.RBRACKET);
    	consume(t,TokenType.ASSIGN);
    	Expr p = parseExpression(t);
    	return new Update(new Mem(e), p);
    }
    
    private static Action parseAction(Tokenizer t) throws SyntaxError{
    	TokenType l = t.peek().getType();
    	Hamlet tobe = actiontypes(l);
    	Action a;
    	if (tobe.equals(Hamlet.tag) || tobe.equals(Hamlet.serve)){
    		consume(t,TokenType.LBRACKET);
    		Expr e = parseExpression(t);
    		consume(t,TokenType.RBRACKET);
    		a = new Specaction(tobe,e);
    	}
    	else{
    		a = new Action(tobe);
    	}
    	return a;
    }
    
    private static Senses parseSensor(Tokenizer t) throws SyntaxError{
    	int i;
    	if (t.peek().getType().equals(TokenType.AHEAD)){
    		consume(t,TokenType.AHEAD);
    		i = 1;
    	}
    	else if(t.peek().getType().equals(TokenType.NEARBY)){
    		consume(t,TokenType.NEARBY);
    		i = 2;
    	}
    	else if (t.peek().getType().equals(TokenType.RANDOM)){
    		consume(t,TokenType.RANDOM);
    		i = 3;
    	}
    	else{
    		i = 4;
    		consume(t,TokenType.SMELL);
    	}
    	consume(t,TokenType.LPAREN);
    	Expr e = parseExpression(t);
    	consume(t,TokenType.RPAREN);
    	return ((i < 4) ? new Sensespace(i,e) : new Senses(i));
    }
    
    /** goal is to return the number of Tokens*/
    public static int rulenum(Tokenizer t){ //TODO Delete (I don't think this is necessary anymore)
    	int temp = 0;
    	while(t.hasNext()){
    		if (t.peek().getType().equals(TokenType.ARR)){
    			temp++;
    		}
    		try {
				consume(t,t.peek().getType());
			} catch (TokenizerIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SyntaxError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return temp;
    }
    
    
    
    // TODO
    // add more as necessary...

    
    
    
    
    
    
    
    /**
     * Consumes a token of the expected type.
     * @throws SyntaxError if the wrong kind of token is encountered.
     */
    public static void consume(Tokenizer t, TokenType tt) throws SyntaxError {
        // TODO figure out what the unsupportedoperationException thing is about.
    	Token a = t.peek();
    	if (a.getType() != (tt)){
    		throw new SyntaxError();
    	}
    	t.next();
		//throw new UnsupportedOperationException();
    }
    
    public static equalities tokenequal(TokenType t){
    	final TokenType [] to = {TokenType.LT, TokenType.LE, TokenType.EQ, 
    			TokenType.GT, TokenType.GE, TokenType.NE
    	};
    	final equalities [] e = {equalities.LT, equalities.LE, equalities.EQ,
    			equalities.GT, equalities.GE, equalities.NE};
    	for (int place = 0; place < to.length; place++){
    		if (to[place].equals(t)){
    			return e[place];
    		}
    	}
    	System.out.println("the token you gave wasn't in the list of acceptable rel tokens");
    	return null; //It should never get here TODO make an exception
    }
    
    private static Hamlet actiontypes(TokenType r){
    	final TokenType [] to = {TokenType.WAIT,TokenType.FORWARD,
    			TokenType.BACKWARD, TokenType.LEFT, TokenType.RIGHT,
    			TokenType.EAT, TokenType.ATTACK, TokenType.GROW, TokenType.BUD,
    			TokenType.MATE, TokenType.TAG, TokenType.SERVE
    	};
    	final Hamlet [] h = {Hamlet.wait, Hamlet.forward, Hamlet.backward,
    			Hamlet.left, Hamlet.right, Hamlet.eat, Hamlet.attack, Hamlet.grow,
    			Hamlet.bud, Hamlet.mate, Hamlet.tag, Hamlet.serve
    	};
    	for (int place = 0; place < to.length; place++){
    		if (to[place] == r){
    			return h[place];
    		}
    	}
    	return null;
    }
}
