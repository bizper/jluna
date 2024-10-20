package compiler.components.define;

import compiler.components.models.Token;

import java.util.List;

/**
 * define all the syntax here.
 */
public enum SyntaxStatus {
    UNDETERMINED,
    ASSIGNMENT,
    EXPRESSION,
    FETAL,// minor mistake
    PANIC // big mistake
}
