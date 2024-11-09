number=NUMBER
letter=LETTER
factor=number | letter
term=(factor "*" factor) | (factor "/" factor)
expr=factor | (term "+" term) | (term "-" term)
