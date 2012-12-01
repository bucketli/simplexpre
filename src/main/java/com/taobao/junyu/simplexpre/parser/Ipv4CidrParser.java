package com.taobao.junyu.simplexpre.parser;

import com.taobao.junyu.simplexpre.bean.Ipv4CidrToken;
import com.taobao.junyu.simplexpre.expr.Ipv4CidrExpression;
import com.taobao.junyu.simplexpre.lexer.Ipv4CidrLexer;

/**
 * @description
 * @author <a href="junyu@taobao.com">junyu</a>
 * @date 2012-10-30ÏÂÎç12:38:54
 */
public class Ipv4CidrParser {
	private Ipv4CidrLexer lexer;
	private Ipv4CidrExpression expr;

	public Ipv4CidrParser(Ipv4CidrLexer lexer) {
		this.lexer = lexer;
	}

	public Ipv4CidrExpression cidrip() {
		expr = new Ipv4CidrExpression();
		this.lexer.nextToken();
		while (this.lexer.token() != Ipv4CidrToken.END) {
			parse0();
			this.lexer.nextToken();
		}

		return expr;
	}

	private void parse0() {
		if (lexer.token() == Ipv4CidrToken.PURE_NUMBER) {
			expr.addIpPiece((Integer) this.lexer.integerValue());
		} else if (lexer.token() == Ipv4CidrToken.PUNC_DOT) {
			return;
		} else if (lexer.token() == Ipv4CidrToken.PUNC_UNDER_SLASH) {
			if (this.expectToken(Ipv4CidrToken.PURE_NUMBER)) {
				expr.setFit((Integer) lexer.integerValue());
			} else {
				throw new RuntimeException("parse error");
			}
		} else if (lexer.token() == Ipv4CidrToken.ALL) {
			expr.setFit(0);
		}
	}

	/**
	 * LL(1)
	 * 
	 * @param token
	 * @return
	 */
	private boolean expectToken(Ipv4CidrToken token) {
		this.lexer.nextToken();
		if (this.lexer.token() == token) {
			return true;
		} else {
			return false;
		}
	}
}
