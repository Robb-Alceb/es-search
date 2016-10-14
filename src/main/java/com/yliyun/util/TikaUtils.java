package com.yliyun.util;/**
 * Created by Administrator on 2016/10/14.
 */

import org.apache.james.mime4j.parser.ContentHandler;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.*;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/14 - 20:57
 *
 */
public class TikaUtils {


    public String txtParser(String path) throws IOException, TikaException, SAXException {

        BodyContentHandler handler = new BodyContentHandler(1000* 1024*1024);
        Metadata metadata = new Metadata();

        FileInputStream inputstream = new FileInputStream(new File(path));

        ParseContext pcontext = new ParseContext();

        //Text document parser
        TXTParser TexTParser = new TXTParser();

        TexTParser.parse(inputstream, handler, metadata, pcontext);

        System.out.println("Contents of the document:" + handler.toString());
        System.out.println("Metadata of the document:");

        String[] metadataNames = metadata.names();

        for (String name : metadataNames) {
            System.out.println(name + " : " + metadata.get(name));
        }
        return handler.toString();
    }


    /**
     * 根据Parser得到文档的内容
     *
     * @param f
     * @return
     */
    public String fileToTxt(File f) {
        Parser parser = new AutoDetectParser();//自动检测文档类型，自动创建相应的解析器
        InputStream is = null;
        try {
            Metadata metadata = new Metadata();
            //   metadata.set(Metadata.AUTHOR, "空号");//重新设置文档的媒体内容
            //  metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());
            is = new FileInputStream(f);
            BodyContentHandler handler = new BodyContentHandler();
            ParseContext context = new ParseContext();
            context.set(Parser.class, parser);
            parser.parse(is, handler, metadata, context);
            for (String name : metadata.names()) {
                System.out.println(name + ":" + metadata.get(name));
            }
            return handler.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
