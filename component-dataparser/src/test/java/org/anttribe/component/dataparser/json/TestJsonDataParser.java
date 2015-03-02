package org.anttribe.component.dataparser.json;

import static org.junit.Assert.fail;

import org.anttribe.component.dataparser.DataParser;
import org.anttribe.component.dataparser.Parser;
import org.junit.Test;

public class TestJsonDataParser
{
    
    @Test
    public void testParseToString()
    {
    }
    
    @Test
    public void testParseToObjectStringClassOfQ()
    {
        String jsonStr = "[{\"id\": 2," + " \"title\": \"json title\", " + "\"width\": 34," + "\"height\": 35}]";
        DataParser parser = DataParser.getDataParser(Parser.Json);
        JavaBean[] bean = (JavaBean[])parser.parseToObject(jsonStr, JavaBean.class);
        System.out.println(bean);
    }
    
    @Test
    public void testParseToObjectInputStreamClassOfQ()
    {
    }
    
}
