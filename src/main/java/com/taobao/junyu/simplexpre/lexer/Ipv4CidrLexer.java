package com.taobao.junyu.simplexpre.lexer;

import java.math.BigInteger;

import com.taobao.junyu.simplexpre.bean.Ipv4CidrToken;
import com.taobao.junyu.simplexpre.utils.CharTypes;

/**
 * @description
 * @author <a href="junyu@taobao.com">junyu</a>
 * @date 2012-10-30ÏÂÎç12:40:54
 */
public class Ipv4CidrLexer {
	private char[] exprChs;
	private int eofIndex;
	public static final byte EOI = 0x1A;

	private char ch;
	protected int curIndex = -1;

	private Ipv4CidrToken token;

	protected int offsetCache;
	protected int sizeCache;

	public Ipv4CidrLexer(String expr) {
		this(toChars(expr));
	}

	public Ipv4CidrLexer(char[] expr) {
		if (CharTypes.isWhitespace(expr[expr.length - 1])) {
			this.exprChs = expr;
		} else {
			this.exprChs = new char[expr.length + 1];
			System.arraycopy(expr, 0, exprChs, 0, expr.length);
		}

		this.eofIndex = exprChs.length - 1;
		this.exprChs[this.eofIndex] = Ipv4CidrLexer.EOI;
		scanChar();
	}

	protected char scanChar() {
		return this.ch = exprChs[++curIndex];
	}

	public Ipv4CidrToken nextToken() {
		skipWhiteSpace();

		switch (ch) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			this.scanNumber();
			return this.token;
		case '/':
			scanChar();
			return this.token = Ipv4CidrToken.PUNC_UNDER_SLASH;
		case '.':
			scanChar();
			return this.token = Ipv4CidrToken.PUNC_DOT;
		case EOI:
			return this.token = Ipv4CidrToken.END;
		case '%':
			scanChar();
			return this.token = Ipv4CidrToken.ALL;
		default:
			throw new RuntimeException("current char is " + ch);
		}
	}

	private void skipWhiteSpace() {
		for (; CharTypes.isWhitespace(ch); scanChar()) {
		}
	}

	public Ipv4CidrToken token() {
		return token;
	}

	private void scanNumber() {
		this.offsetCache = curIndex;
		sizeCache = 1;
		for (; scanChar() != EOI; sizeCache++) {
			if (CharTypes.isDigit(ch)) {
			} else {
				break;
			}
		}

		token = Ipv4CidrToken.PURE_NUMBER;
		return;
	}

	/**
	 * 
	 * 
	 * @param expr
	 * @return
	 */
	private static char[] toChars(String expr) {
		if (CharTypes.isWhitespace(expr.charAt(expr.length() - 1))) {
			return expr.toCharArray();
		}

		char[] chars = new char[expr.length() + 1];
		expr.getChars(0, expr.length(), chars, 0);
		chars[expr.length()] = ' ';
		return chars;
	}

	public Number integerValue() {
		// 2147483647
		// 9223372036854775807
		if (this.sizeCache < 10
				|| (this.sizeCache == 10 && this.exprChs[offsetCache] <= '2' && this.exprChs[offsetCache] < '1')) {
			int rst = 0;
			int end = offsetCache + sizeCache;
			for (int i = offsetCache; i < end; i++) {
				rst = (rst << 3) + (rst << 1);
				rst += exprChs[i] - '0';
			}
			return rst;
		} else if (sizeCache < 19 || sizeCache == 19
				&& exprChs[offsetCache] < '9') {
			long rst = 0;
			int end = offsetCache + sizeCache;
			for (int i = offsetCache; i < end; ++i) {
				rst = (rst << 3) + (rst << 1);
				rst += exprChs[i] - '0';
			}
			return rst;
		} else {
			return new BigInteger(new String(exprChs, offsetCache, sizeCache));
		}
	}
}
