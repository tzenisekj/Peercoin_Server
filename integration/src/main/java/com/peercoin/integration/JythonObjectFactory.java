package com.peercoin.integration;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class JythonObjectFactory {
    private static JythonObjectFactory instance = null;
    private static PyObject pyObject = null;

    protected JythonObjectFactory() {

    }

    public static JythonObjectFactory getInstance(){
        if(instance == null){
            instance = new JythonObjectFactory();
        }

        return instance;

    }


    public Object createObject(Object interfaceType, String moduleName){
        Object javaInt = null;
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from " + moduleName.toLowerCase() + " import " + moduleName);

        pyObject = interpreter.get(moduleName);

        try {

            PyObject newObj = pyObject.__call__();

            javaInt = newObj.__tojava__(Class.forName(interfaceType.toString().substring(
                    interfaceType.toString().indexOf(" ")+1, interfaceType.toString().length())));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JythonObjectFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

        return javaInt;
    }

}