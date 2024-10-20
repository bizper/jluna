package compiler.components.define;

public enum NodeType {

    //root node, for multiple line code
    start,
    calculate,
    define, // start to define something
    call, // use the definition

    //leaf node
    constant,
    symbol

}
