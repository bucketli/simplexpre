package com.taobao.junyu.simplexpre.ast;

import com.taobao.junyu.simplexpre.visitor.ExprVisitor;

/**
 * @description
 * @author <a href="junyu@taobao.com">junyu</a> 
 * @date 2012-10-30ионГ10:50:40
 */
public interface ASTNode {
    public void accept(ExprVisitor visitor);
}
