package ENUMERATORS;

/* KEYWORDPRSTR: keyword that identifies the start of the program
 * KEYWORDPREND: keyword that identifies the end of the program
 * KEYWORDINT: keyword that identifies integers
 * KEYWORDFLT: keyword that identifies floats
 * KEYWORDSTR: keyword that identifies strings
 * KEYWORDCND: keyword for condition (if, else)
 * ENDING: the end of the instruction
 * RELOPERATOR: relational operator (=, <=, >=, <>)
 */
public enum TokenType {
    KEYWORDPRSTR, KEYWORDPREND, KEYWORDINT, KEYWORDFLT, KEYWORDSTR, KEYWORDPRINT,
    IDENTIFIER, NUMBER, RELOPERATOR, COMMENT, ENDING, PUNCTUATION,
    KEYWORDCND, CONDITIONSTR, CONDITIONEND, BEGIN, END,
    SET, GET, MESSAGE, UNKNOWN, FROM
}
