# SNAKE Compiler

**THE OBJECTIVE:**

Developping a compiler that has the ability to analyze a source file **.snk** written using a -made in- language called **SNAKE**

**LEXICAL ANALYSIS**

All tokens must be recognized

**SYNTAX ANALYSIS**

Syntaxic errors must be signaled in case one or multiple instructions weren't following the right format

**SEMANTIC  ANALYSIS**

Semantic errors must be printed such as; incompatible types, using an undeclared variable and so on

# SNAKE SPECIFICATIONS

* [X] The program must start with **Snk_Begin** and ends with **Snk_End.**
* [X] Only **ONE** instruction per line
* [X] An instruction must ends with **#**.
* [X] A comment must start with **##.**
* [X] An identifier must start with a letter and can be followed by alphanumeric characters.
* [X] **Snk_Int** for declaring integers.
* [X] **Snk_Real** for declaring floats.
* [X] **Snk_Strg** for declaring strings.
* [X] A float is composed of 2 integers separated by a point.
* [X] A string must be inside double quotes (**" "**).

# NEED TO DO

* [X] Adding the rules in the about page.
* [ ] Semantic analysis: Type mismatch
* [X] Semantic Analysis: undeclared variables
