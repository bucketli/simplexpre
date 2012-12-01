package com.taobao.junyu.simplexpre.parser;

import org.junit.Test;

import com.taobao.junyu.simplexpre.expr.Ipv4CidrExpression;
import com.taobao.junyu.simplexpre.lexer.Ipv4CidrLexer;
import com.taobao.junyu.simplexpre.visitor.ExprVisitor;

/**
 * @description
 * @author <a href="junyu@taobao.com">junyu</a> 
 * @date 2012-11-1ÏÂÎç04:05:26
 */
public class Ipv4CidrParserTest {
    @Test
	public void testCidrParse(){
    	String cip="   %";
    	Ipv4CidrLexer lexer=new Ipv4CidrLexer(cip);
    	Ipv4CidrParser parser=new Ipv4CidrParser(lexer);
    	Ipv4CidrExpression expr=parser.cidrip();
    	expr.accept(new Ipv4CidrVisior());
    	
    	String ip="  10.232.0.12";
    	Ipv4CidrLexer lexer2=new Ipv4CidrLexer(ip);
    	Ipv4CidrParser parser2=new Ipv4CidrParser(lexer2);
    	Ipv4CidrExpression expr2=parser2.cidrip();
    	System.out.println("expr1:"+expr.dump());
    	System.out.println("expr2:"+expr2.dump());
        System.out.println(expr.isContain(expr2));
    }
    
    public class Ipv4CidrVisior implements ExprVisitor{
		public void visit(Ipv4CidrExpression cidr) {
			System.out.println(cidr.getFit());
		}
    }
}
