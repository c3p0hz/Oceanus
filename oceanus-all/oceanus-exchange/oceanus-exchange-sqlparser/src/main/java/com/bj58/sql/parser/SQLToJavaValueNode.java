/**
 * Copyright 2011-2013 FoundationDB, LLC
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* The original from which this derives bore the following: */

/*

   Derby - Class org.apache.derby.impl.sql.compile.SQLToJavaValueNode

   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to you under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package com.bj58.sql.parser;

import com.bj58.sql.StandardException;
import com.bj58.sql.types.DataTypeDescriptor;

/**
 * This node type converts a value in the SQL domain to a value in the Java
 * domain.
 */

public class SQLToJavaValueNode extends JavaValueNode
{
    ValueNode value;

    /**
     * Constructor for a SQLToJavaValueNode
     *
     * @param value A ValueNode representing a SQL value to convert to
     *                          the Java domain.
     */

    public void init(Object value) {
        this.value = (ValueNode)value;
    }

    /**
     * Fill this node with a deep copy of the given node.
     */
    public void copyFrom(QueryTreeNode node) throws StandardException {
        super.copyFrom(node);

        SQLToJavaValueNode other = (SQLToJavaValueNode)node;
        this.value = (ValueNode)getNodeFactory().copyNode(other.value,
                                                          getParserContext());
    }

    /**
     * Prints the sub-nodes of this object.  See QueryTreeNode.java for
     * how tree printing is supposed to work.
     *
     * @param depth The depth of this node in the tree
     */

    public void printSubNodes(int depth) {
        super.printSubNodes(depth);
        if (value != null) {
            printLabel(depth, "value: ");
            value.treePrint(depth + 1);
        }
    }

    /**
     * Override behavior in superclass.
     */
    public DataTypeDescriptor getType() throws StandardException {
        return value.getType();
    }

    /**
     * Get the SQL ValueNode that is being converted to a JavaValueNode
     *
     * @return The underlying SQL ValueNode
     */
    public ValueNode getSQLValueNode() {
        return value;
    }

    public void setSQLValueNode(ValueNode value) {
        this.value = value;
    }

    /** @see ValueNode#getConstantValueAsObject 
     *
     * @exception StandardException Thrown on error
     */
    Object getConstantValueAsObject() throws StandardException {
        return value.getConstantValueAsObject();
    }

    /**
     * Accept the visitor for all visitable children of this node.
     * 
     * @param v the visitor
     *
     * @exception StandardException on error
     */
    void acceptChildren(Visitor v) throws StandardException {
        super.acceptChildren(v);

        if (value != null) {
            value = (ValueNode)value.accept(v);
        }
    }
}
