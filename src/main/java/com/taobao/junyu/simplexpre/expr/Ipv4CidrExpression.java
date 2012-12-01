package com.taobao.junyu.simplexpre.expr;

import java.util.ArrayList;
import java.util.List;

import com.taobao.junyu.simplexpre.ast.ASTNode;
import com.taobao.junyu.simplexpre.visitor.ExprVisitor;

/**
 * @description
 * @author <a href="junyu@taobao.com">junyu</a>
 * @date 2012-10-30ÉÏÎç11:06:17
 */
public class Ipv4CidrExpression implements ASTNode {
	private List<Integer> rmaskedIp = new ArrayList<Integer>();
	private int fit=32;

	/**
	 * true for 1,false for 0
	 */
	private int[] bits = new int[32];
	private int bitIndex = -1;

	public void addIpPiece(int piece) {
		if (piece > 255) {
			throw new RuntimeException("ip v4 piece not great than 255");
		}

		rmaskedIp.add(piece);
		bitIndex+=8;
		for (int i = 0; i < 8; i++) {
			bits[bitIndex - i] = piece & 0x1;
			piece >>>= 1;
		}
	}

	public boolean isContain(Ipv4CidrExpression target) {
		return this.isContain(target.getBits());
	}
	
	public boolean isContain(int[] ipBits) {
		if(ipBits==null||ipBits.length!=32){
			throw new RuntimeException("ip bits array is null or ip bits array length is not 32");
		}
		
        for(int i=0;i<fit;i++){
        	if(ipBits[i]!=this.bits[i]){
        		return false;
        	}
        }
        
        return true;
	}

	public void setFit(int fit) {
		this.fit = fit;
	}

	public int getFit() {
		return fit;
	}

	public int[] getBits() {
		return bits;
	}
	
	public String dump(){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<bits.length;i++){
			sb.append(bits[i]);
			sb.append(" ");
		}
		return sb.toString();
	}

	public void accept(ExprVisitor visitor) {
		visitor.visit(this);
	}
}
