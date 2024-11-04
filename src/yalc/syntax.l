number              =           NUMBER
factor              =           number+ | "(" factor ")"
term                =           factor "*" factor | factor "/" factor
expr                =           factor | term "+" term | term "-" term
