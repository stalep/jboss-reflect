/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
  * by the @authors tag. See the copyright.txt in the distribution for a
  * full listing of individual contributors.
  *
  * This is free software; you can redistribute it and/or modify it
  * under the terms of the GNU Lesser General Public License as
  * published by the Free Software Foundation; either version 2.1 of
  * the License, or (at your option) any later version.
  *
  * This software is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * Lesser General Public License for more details.
  *
  * You should have received a copy of the GNU Lesser General Public
  * License along with this software; if not, write to the Free
  * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
  */

package org.jboss.annotation.factory.ast;

public interface Node {

  /** This method is called after the node has been made the current
    node.  It indicates that child nodes can now be added to it. */
  public void jjtOpen();

  /** This method is called after all the child nodes have been
    added. */
  public void jjtClose();

  /** This pair of methods are used to inform the node of its
   * parent.
   * 
   * @param n 
   */
  public void jjtSetParent(Node n);
  public Node jjtGetParent();

  /** This method tells the node to add its argument to the node's
   *  list of children.
   * @param n 
   * @param i 
   */
  public void jjtAddChild(Node n, int i);

  /** This method returns a child node.  The children are numbered
   *  from zero, left to right.
   *  
   * @param i 
   * @return the node
   */
  public Node jjtGetChild(int i);

  /** Return the number of children the node has.
   * @return the number of children
   */
  public int jjtGetNumChildren();

  /** Accept the visitor.
   * @param visitor the visitor
   * @param data the data
   * @return ? 
   */
  public Object jjtAccept(AnnotationParserVisitor visitor, Object data);
}
