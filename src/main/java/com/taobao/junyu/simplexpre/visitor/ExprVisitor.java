package com.taobao.junyu.simplexpre.visitor;

import com.taobao.junyu.simplexpre.expr.Ipv4CidrExpression;

/**
 * @description
 * @author <a href="junyu@taobao.com">junyu</a> 
 * @date 2012-10-30ионГ10:45:29
 */
public interface ExprVisitor {
	public void visit(Ipv4CidrExpression cidr);
}
