package parser;

/**
 * @author samin on 10/28/21
 * @project jparser
 */
public interface AccessFlagCodes {

    int public_nonstatic = 1;
    int private_nonstatic = 2;
    int protected_nonstatic = 4;

    int public_static = 9;
    int private_static = 10;

    int unassigned = 0; //innerclass constructors, unassigned access flag modifiers

    int public_syncronized = 33;
    int clinit = 8;
    int interface_or_abstract = 1025;
    int access$ = 4104;


}

