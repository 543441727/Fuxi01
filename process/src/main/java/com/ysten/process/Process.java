package com.ysten.process;

import com.google.auto.service.AutoService;
import com.ysten.annotation.Route;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * @author wangjitao on 2020/5/5
 * @desc:
 */
@AutoService(Processor.class)
public class Process extends AbstractProcessor {
    Filer filer ; //

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer =  processingEnv.getFiler() ;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>() ;
        types.add(Route.class.getCanonicalName()) ;
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //生成文件代码
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Route.class);
        Map<String ,String> map = new HashMap<>() ;
        for (Element element :elementsAnnotatedWith ){
            // TypeElement
            // VariableElement
            TypeElement typeElement = (TypeElement)element ;
            //com.ysten.fuxi01.MainActivity
            String className = typeElement.getQualifiedName().toString();
            String pathName = typeElement.getAnnotation(Route.class).path();
            map.put(pathName,className+".class");
        }
        if (map.size() == 0 ){
            return false;
        }

        //
        Writer writer = null ;
        //
        String className = "ActivityUtils"+System.currentTimeMillis();
        try {
            JavaFileObject classFile = filer.createSourceFile("com.ysten.test." + className);
            writer = classFile.openWriter() ;
            writer.write("package com.ysten.test;\n" +
                    "\n" +
                    "import com.ysten.arouter.Arouter;\n" +
                    "import com.ysten.arouter.IRouter;\n" +
                    "\n" +
                    "public class "+className+" implements IRouter {\n" +
                    "    @Override\n" +
                    "    public void putActivity() {\n");

            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()){
                String activityKey = iterator.next() ;
                String cls = map.get(activityKey);
                writer.write("        Arouter.getInstance().putActivity(");
                writer.write("\""+activityKey+"\","+cls+");");
            }
            writer.write("\n}\n" +
                    "}");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
}
