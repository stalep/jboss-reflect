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

public class AnnotationParserTokenManager implements AnnotationParserConstants
{
  public  java.io.PrintStream debugStream = System.out;
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private final int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private final int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
private final int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 10:
         return jjStopAtPos(0, 8);
      case 13:
         return jjMoveStringLiteralDfa1_0(0x80L);
      case 40:
         return jjStopAtPos(0, 10);
      case 41:
         return jjStopAtPos(0, 11);
      case 44:
         return jjStopAtPos(0, 12);
      case 61:
         return jjStopAtPos(0, 13);
      case 64:
         return jjStopAtPos(0, 9);
      case 123:
         return jjStopAtPos(0, 14);
      case 125:
         return jjStopAtPos(0, 15);
      default :
         return jjMoveNfa_0(1, 0);
   }
}
private final int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x80L) != 0L)
            return jjStopAtPos(1, 7);
         break;
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private final void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private final void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private final void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}
private final void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}
private final void jjCheckNAddStates(int start)
{
   jjCheckNAdd(jjnextStates[start]);
   jjCheckNAdd(jjnextStates[start + 1]);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private final int jjMoveNfa_0(int startState, int curPos)
{
   int[] nextStates;
   int startsAt = 0;
   jjnewStateCnt = 27;
   int i = 1;
   jjstateSet[0] = startState;
   int j, kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  if ((0x3ff401000000000L & l) != 0L)
                  {
                     if (kind > 3)
                        kind = 3;
                     jjCheckNAdd(0);
                  }
                  else if (curChar == 34)
                     jjCheckNAddStates(0, 2);
                  else if (curChar == 39)
                     jjAddStates(3, 4);
                  break;
               case 0:
                  if ((0x3ff401000000000L & l) == 0L)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjCheckNAdd(0);
                  break;
               case 2:
                  if ((0xffffff7fffffdbffL & l) != 0L)
                     jjCheckNAdd(3);
                  break;
               case 3:
                  if (curChar == 39 && kind > 5)
                     kind = 5;
                  break;
               case 5:
                  if ((0x8000008400000000L & l) != 0L)
                     jjCheckNAdd(3);
                  break;
               case 6:
                  if (curChar == 48)
                     jjCheckNAddTwoStates(7, 3);
                  break;
               case 7:
                  if ((0xff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(7, 3);
                  break;
               case 8:
                  if ((0x3fe000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(9, 3);
                  break;
               case 9:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(9, 3);
                  break;
               case 10:
                  if (curChar == 48)
                     jjAddStates(5, 6);
                  break;
               case 12:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(12, 3);
                  break;
               case 14:
                  if (curChar == 34)
                     jjCheckNAddStates(0, 2);
                  break;
               case 15:
                  if ((0xfffffffbffffdbffL & l) != 0L)
                     jjCheckNAddStates(0, 2);
                  break;
               case 17:
                  if ((0x8000008400000000L & l) != 0L)
                     jjCheckNAddStates(0, 2);
                  break;
               case 18:
                  if (curChar == 34 && kind > 6)
                     kind = 6;
                  break;
               case 19:
                  if (curChar == 48)
                     jjCheckNAddStates(7, 10);
                  break;
               case 20:
                  if ((0xff000000000000L & l) != 0L)
                     jjCheckNAddStates(7, 10);
                  break;
               case 21:
                  if ((0x3fe000000000000L & l) != 0L)
                     jjCheckNAddStates(11, 14);
                  break;
               case 22:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddStates(11, 14);
                  break;
               case 23:
                  if (curChar == 48)
                     jjAddStates(15, 16);
                  break;
               case 25:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddStates(17, 20);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 1:
               case 0:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjCheckNAdd(0);
                  break;
               case 2:
                  if ((0xffffffffefffffffL & l) != 0L)
                     jjCheckNAdd(3);
                  break;
               case 4:
                  if (curChar == 92)
                     jjAddStates(21, 24);
                  break;
               case 5:
                  if ((0x54404610000000L & l) != 0L)
                     jjCheckNAdd(3);
                  break;
               case 11:
                  if (curChar == 120)
                     jjCheckNAdd(12);
                  break;
               case 12:
                  if ((0x7e0000007eL & l) != 0L)
                     jjCheckNAddTwoStates(12, 3);
                  break;
               case 13:
                  if (curChar == 88)
                     jjCheckNAdd(12);
                  break;
               case 15:
                  if ((0xffffffffefffffffL & l) != 0L)
                     jjCheckNAddStates(0, 2);
                  break;
               case 16:
                  if (curChar == 92)
                     jjAddStates(25, 28);
                  break;
               case 17:
                  if ((0x54404610000000L & l) != 0L)
                     jjCheckNAddStates(0, 2);
                  break;
               case 24:
                  if (curChar == 120)
                     jjCheckNAdd(25);
                  break;
               case 25:
                  if ((0x7e0000007eL & l) != 0L)
                     jjCheckNAddStates(17, 20);
                  break;
               case 26:
                  if (curChar == 88)
                     jjCheckNAdd(25);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 2:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjstateSet[jjnewStateCnt++] = 3;
                  break;
               case 15:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(0, 2);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 27 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   15, 16, 18, 2, 4, 11, 13, 15, 16, 20, 18, 15, 16, 22, 18, 24, 
   26, 15, 16, 25, 18, 5, 6, 8, 10, 17, 19, 21, 23, 
};
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, "\15\12", "\12", "\100", "\50", "\51", 
"\54", "\75", "\173", "\175", };
public static final String[] lexStateNames = {
   "DEFAULT", 
};
static final long[] jjtoToken = {
   0xffe9L, 
};
static final long[] jjtoSkip = {
   0x6L, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[27];
private final int[] jjstateSet = new int[54];
protected char curChar;
public AnnotationParserTokenManager(SimpleCharStream stream)
{
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}
public AnnotationParserTokenManager(SimpleCharStream stream, int lexState)
{
   this(stream);
   SwitchTo(lexState);
}
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private final void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 27; i-- > 0;)
      jjrounds[i] = 0x80000000;
}
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}
public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   Token t = Token.newToken(jjmatchedKind);
   t.kind = jjmatchedKind;
   String im = jjstrLiteralImages[jjmatchedKind];
   t.image = (im == null) ? input_stream.GetImage() : im;
   t.beginLine = input_stream.getBeginLine();
   t.beginColumn = input_stream.getBeginColumn();
   t.endLine = input_stream.getEndLine();
   t.endColumn = input_stream.getEndColumn();
   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

public Token getNextToken() 
{
  int kind;
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {   
   try   
   {     
      curChar = input_stream.BeginToken();
   }     
   catch(java.io.IOException e)
   {        
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100000200L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

}
